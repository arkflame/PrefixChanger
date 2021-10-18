package dev._2lstudios.prefixchanger.prefix;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.prefix.entities.Prefix;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;

public class PrefixHandler {
    private final Repository<Prefix> prefixRepository;
    private final Repository<PrefixPlayer> prefixPlayerRepository;

    public PrefixHandler() {
        this.prefixRepository = MilkshakeORM.getRepository(Prefix.class);
        this.prefixPlayerRepository = MilkshakeORM.getRepository(PrefixPlayer.class);
    }

    public PrefixHandlerResult changePrefix(final Player player, final String prefixName) {
        try {
            if (player.hasPermission("prefixchanger.prefix." + prefixName)) {
                final Prefix prefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                if (prefix != null) {
                    final String playerName = player.getName();
                    final UUID playerUUID = player.getUniqueId();
                    PrefixPlayer prefixPlayer = prefixPlayerRepository
                            .findOne(MapFactory.create("uuid", playerUUID.toString()));

                    if (prefixPlayer == null) {
                        prefixPlayer = prefixPlayerRepository.findOne(MapFactory.create("name", playerName));
                    }

                    if (prefixPlayer == null) {
                        prefixPlayer = new PrefixPlayer();
                    }

                    prefixPlayer.setName(playerName);
                    prefixPlayer.setUUID(playerUUID);
                    prefixPlayer.setPrefix(prefixName);
                    prefixPlayer.save();
                    return PrefixHandlerResult.SUCCESS;
                } else {
                    return PrefixHandlerResult.EXISTS;
                }
            } else {
                return PrefixHandlerResult.PERMISSION;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return PrefixHandlerResult.ERROR;
    }

    public PrefixHandlerResult listPrefixes(final Player player) {
        try {
            if (player.hasPermission("prefixchanger.list")) {
                final Prefix[] prefixes = prefixRepository.findMany(new HashMap<>());

                if (prefixes != null && prefixes.length > 0) {
                    final StringBuilder stringBuilder = new StringBuilder("&aPrefix list:\n");

                    for (final Prefix prefix : prefixes) {
                        if (!stringBuilder.isEmpty()) {
                            stringBuilder.append(" ");
                        } else {
                            stringBuilder.append("&aPrefix list:\n");
                        }

                        stringBuilder.append(prefix.getDisplayName() + "&7 (&b" + prefix.getName() + "&7) ");
                    }

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', stringBuilder.toString()));
                    return PrefixHandlerResult.SUCCESS;
                } else {
                    player.sendMessage("There are not prefixes configured!");
                    return PrefixHandlerResult.EXISTS;
                }
            } else {
                player.sendMessage("No permission to list prefixes!");
                return PrefixHandlerResult.PERMISSION;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return PrefixHandlerResult.ERROR;
    }

    public PrefixHandlerResult delete(final Player player, final String prefixName) {
        try {
            if (player.hasPermission("prefixchanger.delete")) {
                final long count = prefixRepository.deleteMany(MapFactory.create("name", prefixName));

                if (count > 0) {
                    return PrefixHandlerResult.SUCCESS;
                } else {
                    return PrefixHandlerResult.EXISTS;
                }
            } else {
                return PrefixHandlerResult.PERMISSION;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return PrefixHandlerResult.ERROR;
    }

    public PrefixHandlerResult clear(final Player player) {
        try {
            if (player.hasPermission("prefixchanger.clear")) {
                final long count = prefixRepository.deleteMany(new HashMap<>());

                if (count > 0) {
                    return PrefixHandlerResult.SUCCESS;
                } else {
                    return PrefixHandlerResult.EXISTS;
                }
            } else {
                return PrefixHandlerResult.PERMISSION;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return PrefixHandlerResult.ERROR;
    }

    public PrefixHandlerResult create(final Player player, final String prefixName, final String displayName,
            final List<String> lore) {
        try {
            if (player.hasPermission("prefixchanger.create")) {
                final Prefix foundPrefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                if (foundPrefix == null) {
                    final Prefix prefix = new Prefix();

                    prefix.setName(prefixName);
                    prefix.setDisplayName(displayName);
                    prefix.setLore(lore);
                    prefix.save();
                    return PrefixHandlerResult.SUCCESS;
                } else {
                    return PrefixHandlerResult.EXISTS;
                }
            } else {
                return PrefixHandlerResult.PERMISSION;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return PrefixHandlerResult.ERROR;
    }

    public PrefixHandlerResult edit(final Player player, final String prefixName, final String displayName,
            final List<String> lore) {
        try {
            if (player.hasPermission("prefixchanger.edit")) {
                final Prefix foundPrefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                if (foundPrefix != null) {
                    foundPrefix.setName(prefixName);
                    foundPrefix.setDisplayName(displayName);
                    foundPrefix.setLore(lore);
                    foundPrefix.save();
                } else {
                    player.sendMessage("The prefix '" + prefixName + "' doesn't exist!");
                }
            } else {
                player.sendMessage("No permission to edit prefixes!");
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return PrefixHandlerResult.ERROR;
    }
}
