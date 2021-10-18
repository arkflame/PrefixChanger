package dev._2lstudios.prefixchanger.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuItemClickable extends MenuItem {
    public MenuItemClickable(final ItemStack itemStack, final int slot) {
        super(itemStack, slot);
    }

    public void click(final Player player) {
        // Overriden by super class
    }
}
