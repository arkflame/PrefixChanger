package dev._2lstudios.prefixchanger.prefix.menu;

import java.util.HashMap;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;

import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.menu.MenuItemClose;
import dev._2lstudios.prefixchanger.menu.MenuManager;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;

public class PrefixMenuHandler {
    private final static int SLOTS = 28;
    private final MenuManager menuManager;
    private final PrefixHandler prefixHandler;
    private final Repository<Prefix> prefixRepository;

    public PrefixMenuHandler(final MenuManager menuManager, final PrefixHandler prefixHandler) {
        this.menuManager = menuManager;
        this.prefixHandler = prefixHandler;
        this.prefixRepository = MilkshakeORM.getRepository(Prefix.class);
    }

    private final int getMaxPages(int prefixCount) {
        return 1 + (prefixCount - 1) / SLOTS;
    }

    public void openMenu(final Player player, final int page) {
        final Prefix[] prefixes = prefixRepository.findMany(new HashMap<>());
        final int prefixCount = prefixes.length;
        final int maxPages = getMaxPages(prefixCount);
        final PrefixMenu prefixMenu = new PrefixMenu(page, maxPages);
        int index = Math.max((page - 1) * SLOTS, 0);

        for (int slot = 10; slot <= 43; slot++) {
            if (index >= prefixCount) {
                break;
            } else {
                prefixMenu.setItem(slot, new PrefixItem(slot, prefixes[index], prefixHandler));

                if ((index + 1) % 7 == 0) {
                    slot += 2;
                }
            }

            index++;
        }

        if (page - 1 > 0) {
            prefixMenu.setItem(46, new PrefixPageItem(this, page - 1, 46));
        }
        
        prefixMenu.setItem(48, new DisablePrefixItem(48));
        prefixMenu.setItem(49, new MenuItemClose(49));
        prefixMenu.setItem(50, new GhostPrefixItem(50));

        if (page + 1 <= maxPages) {
            prefixMenu.setItem(52, new PrefixPageItem(this, page + 1, 52));
        }
        
        menuManager.add(prefixMenu);
        player.openInventory(prefixMenu.getInventory());
    }
}
