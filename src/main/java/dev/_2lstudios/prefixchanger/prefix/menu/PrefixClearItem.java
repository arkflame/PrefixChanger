package dev._2lstudios.prefixchanger.prefix.menu;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.menu.MenuInventory;
import dev._2lstudios.prefixchanger.menu.MenuItem;
import dev._2lstudios.prefixchanger.placeholders.Placeholder;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixPlayerService;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;

public class PrefixClearItem extends MenuItem {
    private final static String ITEM_SUFFIX = ChatColor.translateAlternateColorCodes('&', "&7 (%prefix%)");

    public static String getItemSuffix(final String prefixName) {
        return ITEM_SUFFIX.replace("%prefix%", prefixName);
    }

    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final LangManager langManager;
    private final PrefixPlayerService prefixPlayerService;

    private void setupItemStack(final Player player) {
        final ItemStack itemStack = getItemStack();
        final Material prefixMaterial = Material.getMaterial("SKULL_ITEM");
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (prefixMaterial != null) {
            itemStack.setType(prefixMaterial);
        }

        final String playerName = player.getName();
        final PrefixPlayer prefixPlayer = prefixPlayerService.getByName(playerName);
        final String prefixName;

        if (prefixPlayer == null) {
            prefixName = "N/A";
        } else {
            prefixName = prefixPlayer.getPrefix();
        }

        final Placeholder[] placeholders = { new Placeholder("%player_name%", playerName),
                new Placeholder("%prefix_name%", prefixName) };
        final String displayName = langManager.getMessage(player, "item_clear.name", placeholders);
        final String lore = langManager.getMessage(player, "item_clear.lore", placeholders);

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore.split("\n")));
        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability((short) 3);
    }

    public PrefixClearItem(final Plugin plugin, final MenuInventory menuInventory, final LangManager langManager,
            final PrefixPlayerService prefixPlayerService, final Player player, final int slot,
            final PrefixHandler prefixHandler) {
        super(menuInventory, new ItemStack(Material.FEATHER), slot);
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        this.langManager = langManager;
        this.prefixPlayerService = prefixPlayerService;

        setupItemStack(player);
    }

    @Override
    public void click(final Player player) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            final PrefixPlayer prefixPlayer = prefixPlayerService.getByUUID(player.getUniqueId());

            if (prefixPlayer != null) {
                prefixPlayer.delete();
            }

            player.sendMessage(langManager.getMessage(player, "reset"));
            player.playSound(player.getLocation(), Sound.valueOf("ENDERMAN_TELEPORT"), 1f, 0.5f);
        });
    }

    @Override
    public void inventoryUpdate(final Player player) {
        scheduler.runTaskLaterAsynchronously(plugin, () -> {
            setupItemStack(player);
            getMenuInventory().setItem(this);
        }, 5L);
    }
}
