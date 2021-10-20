package dev._2lstudios.prefixchanger.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItemClose extends MenuItemClickable {
    public MenuItemClose(final ItemStack itemStack, final int slot) {
        super(itemStack, slot);
    }

    public MenuItemClose(final String displayName, final int slot) {
        this(new ItemStack(Material.BARRIER), slot);
        
        final ItemStack itemStack = getItemStack();
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public void click(final Player player) {
        player.closeInventory();
    }
}
