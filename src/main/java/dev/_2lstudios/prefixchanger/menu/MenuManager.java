package dev._2lstudios.prefixchanger.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class MenuManager {
    private final Map<Inventory, MenuInventory> inventoriesByInventory = new HashMap<>();
    private final Map<String, MenuInventory> inventoriesByTitle = new HashMap<>();

    public MenuInventory get(final Inventory inventory) {
        return inventoriesByInventory.getOrDefault(inventory, null);
    }

    public MenuInventory get(final String title) {
        return inventoriesByTitle.getOrDefault(title, null);
    }

    public MenuInventory create(final int size, final String title) {
        final Inventory inventory = Bukkit.getServer().createInventory(null, size, title);
        final MenuInventory menuInventory = new MenuInventory(title, inventory);

        inventoriesByInventory.put(inventory, menuInventory);
        inventoriesByTitle.put(title, menuInventory);

        return menuInventory;
    }

    public void delete(final String title) {
        final MenuInventory menuInventory = inventoriesByTitle.remove(title);
        inventoriesByInventory.remove(menuInventory.getInventory());
    }

    public void delete(final Inventory inventory) {
        final MenuInventory menuInventory = inventoriesByInventory.remove(inventory);
        inventoriesByTitle.remove(menuInventory.getTitle());
    }

    public void add(final MenuInventory menuInventory) {
        inventoriesByInventory.put(menuInventory.getInventory(), menuInventory);
        inventoriesByTitle.put(menuInventory.getTitle(), menuInventory);
    }
}
