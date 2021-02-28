package me.kovalski.gui.event;

import me.kovalski.gui.gui.GUIButton;
import me.kovalski.gui.gui.InventoryGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ButtonClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final InventoryGUI inventoryGUI;
    private final Player player;
    private final GUIButton guiButton;

    public ButtonClickEvent(Player player, InventoryGUI inventoryGUI, GUIButton guiButton) {
        this.inventoryGUI = inventoryGUI;
        this.player = player;
        this.guiButton = guiButton;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public InventoryGUI getInventoryGUI() {
        return inventoryGUI;
    }

    public Player getPlayer() {
        return player;
    }

    public GUIButton getGuiButton() {
        return guiButton;
    }
}
