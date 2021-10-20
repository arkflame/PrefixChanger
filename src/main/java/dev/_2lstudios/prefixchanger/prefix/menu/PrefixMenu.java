package dev._2lstudios.prefixchanger.prefix.menu;

import org.bukkit.Bukkit;

import dev._2lstudios.prefixchanger.menu.MenuInventory;

public class PrefixMenu extends MenuInventory {
    private final int page;

    public PrefixMenu(final String title, final int page, final int maxPages) {
        super(title, Bukkit.createInventory(null, 54, title));
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
