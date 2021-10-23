package dev._2lstudios.prefixchanger.prefix;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.prefix.entities.Prefix;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;

public class PrefixHandler {
    private final PrefixService prefixService;
    private final PrefixPlayerService prefixPlayerService;

    public PrefixHandler(final PrefixService prefixService, final PrefixPlayerService prefixPlayerService) {
        this.prefixService = prefixService;
        this.prefixPlayerService = prefixPlayerService;
    }

    public PrefixHandlerResult changePrefix(final Player player, final String prefixName) {
        try {
            if (player.hasPermission("prefixchanger.prefix." + prefixName)) {
                final Prefix prefix = prefixService.getPrefix(prefixName);

                if (prefix != null) {
                    final String playerName = player.getName();
                    final UUID playerUUID = player.getUniqueId();
                    PrefixPlayer prefixPlayer = prefixPlayerService.getByUUID(playerUUID);

                    if (prefixPlayer == null) {
                        prefixPlayer = prefixPlayerService.getByName(playerName);
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

    public PrefixHandlerResult delete(final Player player, final String prefixName) {
        try {
            if (player.hasPermission("prefixchanger.delete")) {
                final long count = prefixService.delete(prefixName);

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
            final String materialName, final int data) {
        try {
            if (player.hasPermission("prefixchanger.create")) {
                if (prefixService.getPrefix(prefixName) == null) {
                    prefixService.create(prefixName, ChatColor.translateAlternateColorCodes('&', displayName),
                            materialName, data);

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
            final String materialName, final int data) {
        try {
            if (player.hasPermission("prefixchanger.edit")) {
                final Prefix foundPrefix = prefixService.getPrefix(prefixName);

                if (foundPrefix != null) {
                    foundPrefix.setName(prefixName);
                    foundPrefix.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
                    foundPrefix.setMaterialName(materialName);
                    foundPrefix.setData(data);
                    foundPrefix.save();
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

    public PrefixHandlerResult editPriority(final Player player, final String prefixName, final int priority) {
        try {
            if (player.hasPermission("prefixchanger.edit")) {
                final Prefix foundPrefix = prefixService.getPrefix(prefixName);

                if (foundPrefix != null) {
                    foundPrefix.setPriority(priority);
                    foundPrefix.save();
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

    public PrefixHandlerResult editPriority(final Player player, final int priority) {
        try {
            if (player.hasPermission("prefixchanger.edit")) {
                final Prefix[] prefixes = prefixService.getPrefixes();

                if (prefixes.length > 0) {
                    for (final Prefix prefix : prefixes) {

                        if (prefix != null) {
                            prefix.setPriority(priority);
                            prefix.save();
                        }
                    }

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
}
