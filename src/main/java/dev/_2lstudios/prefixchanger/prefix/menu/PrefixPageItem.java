package dev._2lstudios.prefixchanger.prefix.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.prefixchanger.menu.MenuItemClickable;
import net.md_5.bungee.api.ChatColor;

public class PrefixPageItem extends MenuItemClickable {
    private final PrefixMenuHandler prefixMenuHandler;
    private final int page;

    public PrefixPageItem(final PrefixMenuHandler prefixMenuHandler, final int page, final int slot) {
        super(new ItemStack(Material.ARROW), slot);
        this.prefixMenuHandler = prefixMenuHandler;
        this.page = page;

        final ItemStack itemStack = getItemStack();
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aPagina " + page));
        itemStack.setItemMeta(itemMeta);
    }

    public void click(final Player player) {
        prefixMenuHandler.openMenu(player, page);
    }
}
