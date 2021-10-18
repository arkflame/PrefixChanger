package dev._2lstudios.prefixchanger.prefix.menu;

import org.bukkit.Bukkit;

import dev._2lstudios.prefixchanger.menu.MenuInventory;
import net.md_5.bungee.api.ChatColor;

public class PrefixMenu extends MenuInventory {
    public static String getTitle(final int page, final int maxPages) {
        return ChatColor.translateAlternateColorCodes('&', "PrefixChanger - (Page " + page + "/" + maxPages + ")");
    }

    private final int page;

    public PrefixMenu(final int page, final int maxPages) {
        super(getTitle(page, maxPages), Bukkit.createInventory(null, 54, getTitle(page, maxPages)));
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
