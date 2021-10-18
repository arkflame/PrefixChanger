package dev._2lstudios.prefixchanger.menu;

import org.bukkit.inventory.ItemStack;

public class MenuItem {
    private final ItemStack itemStack;
    private final int slot;

    public MenuItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }
}
