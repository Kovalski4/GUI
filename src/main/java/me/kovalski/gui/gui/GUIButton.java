package me.kovalski.gui.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class GUIButton {

    public enum GUIButtonType {
        BUTTON,
        BLOCK,
        NONE,
        INPUT
    }

    GUIButtonType guiButtonType;
    ItemStack itemStack;
    String id;
    int slot;

    public GUIButton(ItemStack itemStack, int slot, GUIButtonType guiButtonType, String id){
        this.guiButtonType = guiButtonType;
        this.itemStack = itemStack;
        this.slot = slot;
        this.id = id;
    }

    public void setItemStack (ItemStack itemStack){
        this.itemStack = itemStack;
        Bukkit.broadcastMessage("GUIButton; "+itemStack.getType()+" : "+itemStack.getAmount());
    }

    public GUIButtonType getGuiButtonType(){
        return guiButtonType;
    }

    public ItemStack getItemStack(){
        return itemStack;
    }

    public String getId(){
        return id;
    }

    public int getSlot(){
        return slot;
    }

}
