package me.kovalski.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.kovalski.gui.gui.GUIButton;
import me.kovalski.gui.gui.InventoryGUI;
import me.kovalski.gui.listener.UniverseGuiListener;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class GUI {

    private static final List<InventoryGUI> inventoryGUIList = new ArrayList<>();

    public GUI (Plugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new UniverseGuiListener(), plugin);
    }

    public void createGUI(ConfigurationSection configurationSection){

        String displayName = format(configurationSection.getString("displayName"));
        int size = configurationSection.getInt("size");
        String id = configurationSection.getName();
        List<GUIButton> GUIButtons = inventoryGuiButtonBuilder(configurationSection);

        InventoryGUI inventoryGUI = new InventoryGUI(displayName, id, size, GUIButtons);
        blockEmptyButtons(inventoryGUI);
        inventoryGUIList.add(inventoryGUI);

    }

    public List<GUIButton> inventoryGuiButtonBuilder(ConfigurationSection cs){

        List<GUIButton> GUIButtons = new ArrayList<>();
        GUIButton.GUIButtonType guiButtonType;
        GUIButton guiButton;
        String displayName;
        List<String> lore;
        Material material;
        ItemStack button;
        ItemMeta itemMeta;
        String id;
        int slot;

        for (String string : cs.getConfigurationSection("contents").getKeys(false)){

            id = string;
            guiButtonType =  GUIButton.GUIButtonType.valueOf(cs.getString("contents."+string+".type"));
            displayName = format(cs.getString("contents."+string+".displayName"));
            material = Material.valueOf(cs.getString("contents."+string+".material"));
            lore = formatList(cs.getStringList("contents."+string+".lore"));
            slot = cs.getInt("contents."+string+".slot");

            switch (material){
                case PLAYER_HEAD:
                    button = getCustomTextureHead(cs.getString("contents."+string+".value"));
                    break;
                case AIR:
                    button = null;
                    break;
                default:
                    button = new ItemStack(material);
            }

            if (button != null){
                itemMeta = button.getItemMeta();
                itemMeta.setDisplayName(displayName);
                itemMeta.setLore(lore);
                button.setItemMeta(itemMeta);
            }

            guiButton = new GUIButton(button, slot, guiButtonType, id);
            GUIButtons.add(guiButton);
        }

        return GUIButtons;
    }

    public void blockEmptyButtons(InventoryGUI inventoryGUI){
        for (int i = 0; i < inventoryGUI.getSize(); i++){
            if (!inventoryGUI.hasButton(i)){
                GUIButton guiButton = new GUIButton(null, i, GUIButton.GUIButtonType.BLOCK, "");
                inventoryGUI.addButton(guiButton);
            }
        }
    }

    public ItemStack getCustomTextureHead(String value) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }

    private String format(String string){
        return string.replaceAll("&", "§");
    }

    private List<String> formatList(List<String> stringList){
        List<String> formatted = new ArrayList<>();
        for (String string : stringList){
            formatted.add(format(string));
        }
        return formatted;
    }

    public static List<InventoryGUI> inventoryGUIList(){
        return inventoryGUIList;
    }

    public InventoryGUI getInventoryGUI(String string){
        for (InventoryGUI inventoryGUI : inventoryGUIList){
            if (string.equals(inventoryGUI.getId())){
                return inventoryGUI;
            }
        }
        return null;
    }
}
