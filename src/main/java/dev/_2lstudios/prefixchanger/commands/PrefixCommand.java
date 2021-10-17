package dev._2lstudios.prefixchanger.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.entities.Prefix;
import dev._2lstudios.prefixchanger.entities.PrefixPlayer;

public class PrefixCommand implements CommandExecutor {
    private final Repository<Prefix> prefixRepository;
    private final Repository<PrefixPlayer> prefixPlayerRepository;

    public PrefixCommand() {
        this.prefixRepository = MilkshakeORM.getRepository(Prefix.class);
        this.prefixPlayerRepository = MilkshakeORM.getRepository(PrefixPlayer.class);
    }

    private List<String> split(String[] args, int first, int last) {
        final List<String> split = new ArrayList<>();

        for (int i = first; i < last; i++) {
            if (i < args.length) {
                split.add(args[i]);
            }
        }

        return split;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("change")) {
                    if (args.length > 1) {
                        final String prefixName = args[1].toLowerCase();

                        if (player.hasPermission("prefixchanger." + prefixName)) {
                            final Prefix prefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                            if (prefix != null) {
                                final String playerName = player.getName();
                                final UUID playerUUID = player.getUniqueId();
                                PrefixPlayer prefixPlayer = prefixPlayerRepository
                                        .findOne(MapFactory.create("uuid", playerUUID.toString()));

                                if (prefixPlayer == null) {
                                    prefixPlayer = prefixPlayerRepository
                                            .findOne(MapFactory.create("name", playerName));
                                }

                                if (prefixPlayer == null) {
                                    prefixPlayer = new PrefixPlayer();
                                }

                                prefixPlayer.setName(playerName);
                                prefixPlayer.setUUID(playerUUID);
                                prefixPlayer.setPrefix(prefixName);
                                prefixPlayer.save();
                            } else {
                                sender.sendMessage("Prefix '" + prefixName + "' doesn't exist!");
                            }
                        } else {
                            sender.sendMessage("No permission to use prefix '" + prefixName + "'!");
                        }
                    } else {
                        sender.sendMessage("/prefix change <prefix>");
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
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
                        } else {
                            sender.sendMessage("There are not prefixes configured!");
                        }
                    } else {
                        sender.sendMessage("No permission to list prefixes!");
                    }
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length > 3) {
                        if (sender.hasPermission("prefixchanger.delete")) {
                            final String prefixName = args[1].toLowerCase();
                            final long count = prefixRepository.deleteMany(MapFactory.create("name", prefixName));

                            if (count > 0) {
                                sender.sendMessage("Deleted " + count + " prefixes!");
                            } else {
                                sender.sendMessage("There are no prefixes to delete!");
                            }
                        } else {
                            sender.sendMessage("No permission to delete prefixes!");
                        }
                    } else {
                        sender.sendMessage("/prefix delete <name>");
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 3) {
                        if (sender.hasPermission("prefixchanger.create")) {
                            final String prefixName = args[1].toLowerCase();
                            final Prefix foundPrefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                            if (foundPrefix == null) {
                                final String displayName = args[2];
                                final List<String> lore = split(args, 3, args.length);
                                final Prefix prefix = new Prefix();

                                prefix.setName(prefixName);
                                prefix.setDisplayName(displayName);
                                prefix.setLore(lore);
                                prefix.save();
                            } else {
                                sender.sendMessage("The prefix '" + prefixName + "' already exists!");
                            }
                        } else {
                            sender.sendMessage("No permission to create prefixes!");
                        }
                    } else {
                        sender.sendMessage("/prefix create <name> <displayname> <lore>");
                    }
                } else if (args[0].equalsIgnoreCase("edit")) {
                    if (args.length > 3) {
                        if (sender.hasPermission("prefixchanger.edit")) {
                            final String prefixName = args[1].toLowerCase();
                            final Prefix foundPrefix = prefixRepository.findOne(MapFactory.create("name", prefixName));

                            if (foundPrefix != null) {
                                final String displayName = args[2];
                                final List<String> lore = split(args, 3, args.length);

                                foundPrefix.setName(prefixName);
                                foundPrefix.setDisplayName(displayName);
                                foundPrefix.setLore(lore);
                                foundPrefix.save();
                            } else {
                                sender.sendMessage("The prefix '" + prefixName + "' doesn't exist!");
                            }
                        } else {
                            sender.sendMessage("No permission to edit prefixes!");
                        }
                    } else {
                        sender.sendMessage("/prefix edit <name> <displayname> <lore>");
                    }
                } else {
                    sender.sendMessage("/prefix <change/create/edit>");
                }
            } else {
                sender.sendMessage("/prefix <change/create/edit>");
            }
        } else {
            sender.sendMessage("Can't use from the console!");
        }

        return false;
    }
}
