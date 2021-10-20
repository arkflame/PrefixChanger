package dev._2lstudios.prefixchanger.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final InventoryClickListener inventoryClickListener;

    public PlayerQuitListener(final InventoryClickListener inventoryClickListener) {
        this.inventoryClickListener = inventoryClickListener;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        inventoryClickListener.getLastClicks().remove(event.getPlayer());
    }
}
