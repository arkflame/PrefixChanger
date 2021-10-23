package dev._2lstudios.prefixchanger.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItemClose extends MenuItem {
    public MenuItemClose(final MenuInventory menuInventory, final ItemStack itemStack, final int slot) {
        super(menuInventory, itemStack, slot);
    }

    public MenuItemClose(final MenuInventory menuInventory, final String displayName, final int slot) {
        this(menuInventory, new ItemStack(Material.BARRIER), slot);
        
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
