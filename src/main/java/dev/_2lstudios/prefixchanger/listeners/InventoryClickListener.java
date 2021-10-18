package dev._2lstudios.prefixchanger.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.prefixchanger.menu.MenuInventory;
import dev._2lstudios.prefixchanger.menu.MenuItem;
import dev._2lstudios.prefixchanger.menu.MenuItemClickable;
import dev._2lstudios.prefixchanger.menu.MenuManager;

public class InventoryClickListener implements Listener {
    private final MenuManager menuManager;

    public InventoryClickListener(final MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        final HumanEntity whoClicked = event.getWhoClicked();

        if (whoClicked instanceof Player) {
            final Player player = (Player) whoClicked;
            final Inventory inventory = event.getClickedInventory();
            final MenuInventory menuInventory = menuManager.get(inventory);

            if (menuInventory != null) {
                event.setCancelled(true);

                final MenuItem menuItem = menuInventory.getItem(event.getSlot());

                if (menuItem instanceof MenuItemClickable) {
                    ((MenuItemClickable) menuItem).click(player);
                }
            }
        }
    }
}
