package dev._2lstudios.prefixchanger.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.Inventory;

public class MenuInventory {
    private final Map<Integer, MenuItem> items = new HashMap<>();
    private final String title;
    private final Inventory inventory;

    public MenuInventory(final String title, final Inventory inventory) {
        this.title = title;
        this.inventory = inventory;
    }

    public MenuInventory removeItem(final int slot) {
        if (slot < inventory.getSize()) {
            items.remove(slot);
            inventory.setItem(slot, null);
        }

        return this;
    }

    public MenuInventory removeItem(final MenuItem menuItem) {
        return removeItem(menuItem.getSlot());
    }

    public MenuInventory setItem(final int slot, final MenuItem menuItem) {
        if (slot < inventory.getSize()) {
            items.put(slot, menuItem);
            inventory.setItem(slot, menuItem.getItemStack());
        }

        return this;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getTitle() {
        return title;
    }

    public MenuItem getItem(final int slot) {
        return items.get(slot);
    }
}
