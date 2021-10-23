package dev._2lstudios.prefixchanger.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuItem {
    private final MenuInventory menuInventory;
    private final ItemStack itemStack;
    private final int slot;

    public MenuItem(final MenuInventory menuInventory, final ItemStack itemStack, final int slot) {
        this.menuInventory = menuInventory;
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public MenuInventory getMenuInventory() {
        return menuInventory;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public void inventoryUpdate(final Player player) {
        // Overriden by super class
    }

    public void click(final Player player) {
        // Overriden by super class
    }
}
