package me.kovalski.gui.listener;

import me.kovalski.gui.GUI;
import me.kovalski.gui.event.ButtonClickEvent;
import me.kovalski.gui.gui.GUIButton;
import me.kovalski.gui.gui.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class UniverseGuiListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        for (InventoryGUI inventoryGUI : GUI.inventoryGUIList()){
            if (inventoryGUI.getTitle().equals(event.getView().getTitle())){
                if (event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR) || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)){
                    event.setCancelled(true);
                    return;
                }
                if (inventoryGUI.hasButton(event.getRawSlot())){
                    Inventory inventory = event.getInventory();
                    GUIButton guiButton = inventoryGUI.getButton(event.getSlot());
                    GUIButton.GUIButtonType guiButtonType = guiButton.getGuiButtonType();
                    Bukkit.broadcastMessage(guiButton.getId());
                    switch (guiButtonType){
                        case INPUT:
                            if (event.getCursor() == null){
                                inventory.clear(event.getSlot());
                            }
                            else {
                                guiButton.setItemStack(event.getCursor());
                                event.getInventory().setItem(event.getSlot(), event.getCursor());
                            }
                            break;
                        case BUTTON:
                            Bukkit
                                    .getPluginManager()
                                    .callEvent(new ButtonClickEvent(
                                            (Player) event.getWhoClicked()
                                            , inventoryGUI
                                            , guiButton));
                            break;
                    }
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        for (InventoryGUI inventoryGUI : GUI.inventoryGUIList()) {
            if (inventoryGUI.getTitle().equals(event.getView().getTitle())) {
                event.setCancelled(true);
            }
        }
    }
}
