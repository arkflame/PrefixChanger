package dev._2lstudios.prefixchanger.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixHandlerResult;
import dev._2lstudios.prefixchanger.prefix.menu.PrefixMenuHandler;

public class PrefixCommand implements CommandExecutor {
    private final PrefixHandler prefixHandler;
    private final PrefixMenuHandler prefixMenuHandler;

    public PrefixCommand(final PrefixHandler prefixHandler, final PrefixMenuHandler prefixMenuHandler) {
        this.prefixHandler = prefixHandler;
        this.prefixMenuHandler = prefixMenuHandler;
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
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.changePrefix(player, prefixName);

                        switch (prefixHandleResult) {
                            case SUCCESS: {
                                player.sendMessage("Your prefix was changed to '" + prefixName + "'!");
                                break;
                            }
                            case EXISTS: {
                                player.sendMessage("Prefix '" + prefixName + "' doesn't exist!");
                                break;
                            }
                            case PERMISSION: {
                                player.sendMessage("No permission to use prefix '" + prefixName + "'!");
                                break;
                            }
                            case ERROR: {
                                player.sendMessage("Error while trying to change your prefix!");
                                break;
                            }
                        }
                    } else {
                        sender.sendMessage("/prefix change <prefix>");
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    final PrefixHandlerResult prefixHandleResult = prefixHandler.listPrefixes(player);

                    switch (prefixHandleResult) {
                        case SUCCESS: {
                            break;
                        }
                        case EXISTS: {
                            player.sendMessage("There are no prefixes present!");
                            break;
                        }
                        case PERMISSION: {
                            player.sendMessage("No permission to use the list command!");
                            break;
                        }
                        case ERROR: {
                            player.sendMessage("Error while trying to list prefixes!");
                            break;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("open")) {
                    prefixMenuHandler.openMenu(player, 1);
                    player.sendMessage("Opening prefix menu...");
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length > 1) {
                        final String prefixName = args[1].toLowerCase();
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.delete(player, prefixName);

                        switch (prefixHandleResult) {
                            case SUCCESS: {
                                player.sendMessage("Deleted the prefix '" + prefixName + "'!");
                                break;
                            }
                            case EXISTS: {
                                player.sendMessage("There is no prefix with that name!");
                                break;
                            }
                            case PERMISSION: {
                                player.sendMessage("No permission to use the delete command!");
                                break;
                            }
                            case ERROR: {
                                player.sendMessage("Error while trying to delete prefix!");
                                break;
                            }
                        }
                    } else {
                        sender.sendMessage("/prefix delete <name>");
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    final PrefixHandlerResult prefixHandleResult = prefixHandler.clear(player);

                    switch (prefixHandleResult) {
                        case SUCCESS: {
                            player.sendMessage("Successfuly cleared all prefixes!");
                            break;
                        }
                        case EXISTS: {
                            player.sendMessage("There are no prefixes to clear!");
                            break;
                        }
                        case PERMISSION: {
                            player.sendMessage("No permission to use the clear command!");
                            break;
                        }
                        case ERROR: {
                            player.sendMessage("Error while trying to clear prefixes!");
                            break;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 3) {
                        final String prefixName = args[1].toLowerCase();
                        final String displayName = args[2];
                        final List<String> lore = split(args, 3, args.length);
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.create(player, prefixName,
                                displayName, lore);

                        switch (prefixHandleResult) {
                            case SUCCESS: {
                                player.sendMessage("Successfuly created prefix '" + prefixName + "'!");
                                break;
                            }
                            case EXISTS: {
                                player.sendMessage("The prefix already exists!");
                                break;
                            }
                            case PERMISSION: {
                                player.sendMessage("No permission to use the create command!");
                                break;
                            }
                            case ERROR: {
                                player.sendMessage("Error while trying to create prefix!");
                                break;
                            }
                        }
                    } else {
                        sender.sendMessage("/prefix create <name> <displayname> <lore>");
                    }
                } else if (args[0].equalsIgnoreCase("edit")) {
                    if (args.length > 3) {
                        final String prefixName = args[1].toLowerCase();
                        final String displayName = args[2];
                        final List<String> lore = split(args, 3, args.length);
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.edit(player, prefixName,
                                displayName, lore);

                        switch (prefixHandleResult) {
                            case SUCCESS: {
                                player.sendMessage("Successfuly edited prefix '" + prefixName + "'!");
                                break;
                            }
                            case EXISTS: {
                                player.sendMessage("The prefix doesn't exists!");
                                break;
                            }
                            case PERMISSION: {
                                player.sendMessage("No permission to use the edit command!");
                                break;
                            }
                            case ERROR: {
                                player.sendMessage("Error while trying to edit prefix!");
                                break;
                            }
                        }
                    } else {
                        sender.sendMessage("/prefix edit <name> <displayname> <lore>");
                    }
                } else {
                    sender.sendMessage("/prefix <change/create/edit>");
                }
            } else {
                sender.sendMessage("/prefix <change/create/edit/list/clear/delete>");
            }
        } else {
            sender.sendMessage("Can't use from the console!");
        }

        return false;
    }
}
