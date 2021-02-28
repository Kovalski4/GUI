package me.kovalski.gui.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class InventoryGUI {

    Inventory inventory;
    List<GUIButton> buttons;
    String title;
    String id;
    int size;

    public InventoryGUI(String title, String id, int size, List<GUIButton> buttons){
        this.buttons = buttons;
        this.title = title;
        this.size = size;
        this.id = id;
        inventory = Bukkit.createInventory(null, size, title);
        for (GUIButton button : buttons){
            inventory.setItem(button.getSlot(),button.getItemStack());
        }
    }

    public void openGUI(Player player){
        player.openInventory(inventory);
    }

    public GUIButton getButton(int index){
        for (GUIButton guiButton : buttons){
            if (guiButton.getSlot() == index) {
                return guiButton;
            }
        }
        return null;
    }

    public GUIButton getButtonByID(String id){
        for (GUIButton guiButton : buttons){
            if (guiButton.getId().equals(id)) {
                return guiButton;
            }
        }
        return null;
    }       

    public void addButton(GUIButton guiButton){
        buttons.add(guiButton);
    }

    public boolean hasButton(int index){
        for (GUIButton guiButton : buttons){
            if (guiButton.getSlot() == index) {
                return true;
            }
        }
        return false;
    }

    public String getTitle(){
        return title;
    }

    public String getId(){
        return id;
    }

    public int getSize(){
        return size;
    }

    public Inventory getInventory(){
        return inventory;
    }

}
