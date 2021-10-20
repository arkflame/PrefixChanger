package dev._2lstudios.prefixchanger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.placeholders.Placeholder;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixHandlerResult;
import dev._2lstudios.prefixchanger.prefix.menu.PrefixMenuHandler;

public class PrefixCommand implements CommandExecutor {
    private final LangManager langManager;
    private final PrefixHandler prefixHandler;
    private final PrefixMenuHandler prefixMenuHandler;

    public PrefixCommand(final LangManager langManager, final PrefixHandler prefixHandler, final PrefixMenuHandler prefixMenuHandler) {
        this.langManager = langManager;
        this.prefixHandler = prefixHandler;
        this.prefixMenuHandler = prefixMenuHandler;
    }

    private int getInteger(final String text) {
        try {
            return Integer.parseInt(text);
        } catch (final NumberFormatException e) {
            // Ignored
        }

        return 0;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length > 1) {
                        final String prefixName = args[1].toLowerCase();
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.delete(player, prefixName);

                        switch (prefixHandleResult) {
                        case SUCCESS: {
                            langManager.sendMessage(sender, "deleted", new Placeholder("%prefix%", prefixName));
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
                        langManager.sendMessage(sender, "delete_usage", new Placeholder("%label%", label));
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    final PrefixHandlerResult prefixHandleResult = prefixHandler.clear(player);

                    switch (prefixHandleResult) {
                    case SUCCESS: {
                        langManager.sendMessage(sender, "cleared");
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
                    if (args.length > 4) {
                        final String prefixName = args[1].toLowerCase();
                        final String displayName = args[2];
                        final String materialName = args[3].toUpperCase();
                        final int data = getInteger(args[4]);
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.create(player, prefixName,
                                displayName, materialName, data);

                        switch (prefixHandleResult) {
                        case SUCCESS: {
                            langManager.sendMessage(sender, "created", new Placeholder("%prefix%", prefixName));
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
                        langManager.sendMessage(sender, "create_usage", new Placeholder("%label%", label));
                    }
                } else if (args[0].equalsIgnoreCase("edit")) {
                    if (args.length > 4) {
                        final String prefixName = args[1].toLowerCase();
                        final String displayName = args[2];
                        final String materialName = args[3].toUpperCase();
                        final int data = getInteger(args[4]);
                        final PrefixHandlerResult prefixHandleResult = prefixHandler.edit(player, prefixName,
                                displayName, materialName, data);

                        switch (prefixHandleResult) {
                        case SUCCESS: {
                            langManager.sendMessage(sender, "edited", new Placeholder("%prefix%", prefixName));
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
                        langManager.sendMessage(sender, "edit_usage", new Placeholder("%label%", label));
                    }
                } else {
                    langManager.sendMessage(sender, "commands", new Placeholder("%label%", label));
                }
            } else {
                prefixMenuHandler.openMenu(player, 1);
                langManager.sendMessage(sender, "opening_menu");
            }
        } else {
            langManager.sendMessage(sender, "no_console");
        }

        return false;
    }
}
