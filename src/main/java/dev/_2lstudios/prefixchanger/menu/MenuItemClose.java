package dev._2lstudios.prefixchanger.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class MenuItemClose extends MenuItemClickable {
    public MenuItemClose(final ItemStack itemStack, final int slot) {
        super(itemStack, slot);
    }

    public MenuItemClose(final int slot) {
        this(new ItemStack(Material.BARRIER), slot);
        
        final ItemStack itemStack = getItemStack();
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cClose"));
    }

    @Override
    public void click(final Player player) {
        player.closeInventory();
    }
}
