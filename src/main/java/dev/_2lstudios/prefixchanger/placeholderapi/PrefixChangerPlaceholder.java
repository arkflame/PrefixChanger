package dev._2lstudios.prefixchanger.placeholderapi;

import java.util.UUID;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prefixchanger.prefix.entities.Prefix;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;

public class PrefixChangerPlaceholder extends PlaceholderExpansion {
    private final Plugin plugin;
    private final Repository<Prefix> prefixRepository;
    private final Repository<PrefixPlayer> prefixPlayerRepository;

    public PrefixChangerPlaceholder(final Plugin plugin) {
        this.plugin = plugin;
        this.prefixRepository = MilkshakeORM.getRepository(Prefix.class);
        this.prefixPlayerRepository = MilkshakeORM.getRepository(PrefixPlayer.class);
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
            PrefixPlayer prefixPlayer = prefixPlayerRepository
                    .findOne(MapFactory.create("uuid", playerUUID.toString()));

            if (prefixPlayer == null) {
                prefixPlayer = prefixPlayerRepository.findOne(MapFactory.create("name", playerName));
            }

            if (prefixPlayer != null) {
                final String prefixName = prefixPlayer.getPrefix();

                if (prefixName != null) {
                    if (prefixName.isEmpty()) {
                        return ChatColor.GRAY.toString();
                    } else {
                        final Prefix prefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                        if (prefix != null) {
                            return prefix.getDisplayName() + " ";
                        }
                    }
                }
            }

            return PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
        }

        return null;
    }
}
