package dev._2lstudios.prefixchanger.placeholderapi;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prefixchanger.prefix.PrefixPlayerService;
import dev._2lstudios.prefixchanger.prefix.PrefixService;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;

public class PrefixChangerPlaceholder extends PlaceholderExpansion {
    private final Plugin plugin;
    private final PrefixService prefixService;
    private final PrefixPlayerService prefixPlayerService;

    public PrefixChangerPlaceholder(final Plugin plugin, final PrefixService prefixService,
            final PrefixPlayerService prefixPlayerService) {
        this.plugin = plugin;
        this.prefixService = prefixService;
        this.prefixPlayerService = prefixPlayerService;
    }

    @Override
    public String getAuthor() {
        return "2LStudios";
    }

    @Override
    public String getIdentifier() {
        return "prefixchanger";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(final OfflinePlayer player, final String params) {
        if (params.equalsIgnoreCase("prefix")) {
            final String playerName = player.getName();
            final UUID playerUUID = player.getUniqueId();
            PrefixPlayer prefixPlayer = prefixPlayerService.getByUUID(playerUUID);

            if (prefixPlayer == null) {
                prefixPlayer = prefixPlayerService.getByName(playerName);
            }

            if (prefixPlayer != null) {
                final String prefixName = prefixPlayer.getPrefix();

                if (prefixName != null) {
                    if (prefixName.isEmpty()) {
                        return ChatColor.GRAY.toString();
                    } else {
                        final Prefix prefix = prefixService.getPrefix(prefixName);

                        if (prefix != null) {
                            final String displayName = prefix.getDisplayName();

                            if (ChatColor.stripColor(displayName).isEmpty()) {
                                return displayName;
                            } else {
                                return displayName + " ";
                            }
                        }
                    }
                }
            }

            return PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
        }

        return null;
    }
}
