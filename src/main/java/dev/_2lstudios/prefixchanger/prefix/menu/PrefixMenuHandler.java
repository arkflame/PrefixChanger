package dev._2lstudios.prefixchanger.prefix.menu;

import java.util.HashMap;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.prefixchanger.menu.MenuManager;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;

public class PrefixMenuHandler {
    private final static int SLOTS = 34;
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
        int index = Math.min((page - 1) * SLOTS, 0) - 1;

        for (int slot = 9; slot < SLOTS + 8; slot++) {
            if (++index >= prefixCount) {
                break;
            } else {
                final Prefix prefix = prefixes[index];
                final ItemStack itemStack = new ItemStack(Material.EMERALD, 1);
                final ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(prefix.getDisplayName());
                itemStack.setItemMeta(itemMeta);
                prefixMenu.setItem(slot, new PrefixItem(itemStack, slot, prefix, prefixHandler));
            }
        }

        menuManager.add(prefixMenu);
        player.openInventory(prefixMenu.getInventory());
    }
}
