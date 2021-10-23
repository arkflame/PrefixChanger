package dev._2lstudios.prefixchanger.prefix.menu;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.menu.MenuManager;
import dev._2lstudios.prefixchanger.placeholders.Placeholder;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixPlayerService;
import dev._2lstudios.prefixchanger.prefix.PrefixService;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;

public class PrefixMenuHandler {
    private final static int SLOTS = 28;

    private final Plugin plugin;
    private final LangManager langManager;
    private final MenuManager menuManager;
    private final PrefixHandler prefixHandler;
    private final PrefixService prefixService;
    private final PrefixPlayerService prefixPlayerService;

    public PrefixMenuHandler(final Plugin plugin, final LangManager langManager, final MenuManager menuManager,
            final PrefixHandler prefixHandler, final PrefixService prefixService,
            final PrefixPlayerService prefixPlayerService) {
        this.plugin = plugin;
        this.langManager = langManager;
        this.menuManager = menuManager;
        this.prefixHandler = prefixHandler;
        this.prefixService = prefixService;
        this.prefixPlayerService = prefixPlayerService;
    }

    private final int getMaxPages(int prefixCount) {
        return 1 + (prefixCount - 1) / SLOTS;
    }

    public static String getTitle(final LangManager langManager, final Player player, final int page,
            final int maxPages) {
        final String title = langManager.getMessage(player, "menu_title", new Placeholder("%page%", page),
                new Placeholder("%max_pages%", maxPages));

        return title;
    }

    public void openMenu(final Player player, final int page) {
        final Prefix[] prefixes = prefixService.getPrefixes();
        final int prefixCount = prefixes.length;
        final int maxPages = getMaxPages(prefixCount);
        final String title = getTitle(langManager, player, page, maxPages);
        final PrefixMenu prefixMenu = new PrefixMenu(title, page, maxPages);
        int index = Math.max((page - 1) * SLOTS, 0);

        for (int slot = 10; slot <= 43; slot++) {
            if (index >= prefixCount) {
                break;
            } else {
                prefixMenu.setItem(
                        new PrefixItem(plugin, prefixMenu, langManager, player, slot, prefixes[index], prefixHandler));

                if ((index + 1) % 7 == 0) {
                    slot += 2;
                }
            }

            index++;
        }

        if (page - 1 > 0) {
            prefixMenu.setItem(new PrefixPageItem(prefixMenu, langManager.getMessage(player, "item_page",
                    new Placeholder("%page%", page), new Placeholder("%max_pages%", maxPages)), this, page - 1, 45));
        }

        prefixMenu.setItem(
                new PrefixClearItem(plugin, prefixMenu, langManager, prefixPlayerService, player, 49, prefixHandler));

        if (page + 1 <= maxPages) {
            prefixMenu.setItem(new PrefixPageItem(prefixMenu, langManager.getMessage(player, "item_page",
                    new Placeholder("%page%", page), new Placeholder("%max_pages%", maxPages)), this, page + 1, 53));
        }

        menuManager.add(prefixMenu);
        player.openInventory(prefixMenu.getInventory());
    }
}
