package dev._2lstudios.prefixchanger.placeholderapi;

import java.util.UUID;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prefixchanger.entities.Prefix;
import dev._2lstudios.prefixchanger.entities.PrefixPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

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
        return "prefix";
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
                final Prefix prefix = prefixRepository.findOne(MapFactory.create("name", prefixPlayer.getPrefix()));

                if (prefix != null) {
                    return prefix.getDisplayName();
                }
            }
        }

        return null;
    }
}
