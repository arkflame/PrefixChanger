package dev._2lstudios.prefixchanger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.placeholders.Placeholder;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixHandlerResult;
import dev._2lstudios.prefixchanger.prefix.menu.PrefixMenuHandler;

public class PrefixCommand implements CommandExecutor {
    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final LangManager langManager;
    private final PrefixHandler prefixHandler;
    private final PrefixMenuHandler prefixMenuHandler;

    public PrefixCommand(final Plugin plugin, final LangManager langManager, final PrefixHandler prefixHandler,
            final PrefixMenuHandler prefixMenuHandler) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
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

    public void onPrefixCommand(CommandSender sender, Command command, String label, String[] args) {
        scheduler.runTaskAsynchronously(plugin, () -> {
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
                                langManager.sendMessage(sender, "not_existent",
                                        new Placeholder("%prefix%", prefixName));
                                break;
                            }
                            case PERMISSION: {
                                langManager.sendMessage(sender, "permission_error");
                                break;
                            }
                            case ERROR: {
                                langManager.sendMessage(sender, "error");
                                break;
                            }
                            }
                        } else {
                            langManager.sendMessage(sender, "delete_usage", new Placeholder("%label%", label));
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
                                langManager.sendMessage(sender, "already_exists");
                                break;
                            }
                            case PERMISSION: {
                                langManager.sendMessage(sender, "permission_error");
                                break;
                            }
                            case ERROR: {
                                langManager.sendMessage(sender, "error");
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
                                langManager.sendMessage(sender, "not_existent",
                                        new Placeholder("%prefix%", prefixName));
                                break;
                            }
                            case PERMISSION: {
                                langManager.sendMessage(sender, "permission_error");
                                break;
                            }
                            case ERROR: {
                                langManager.sendMessage(sender, "error");
                                break;
                            }
                            }
                        } else {
                            langManager.sendMessage(sender, "edit_usage", new Placeholder("%label%", label));
                        }
                    } else if (args[0].equalsIgnoreCase("priority")) {
                        if (args.length > 2) {
                            final String prefixName = args[1].toLowerCase();
                            final int priority = getInteger(args[2]);
                            final PrefixHandlerResult prefixHandleResult = prefixHandler.editPriority(player, prefixName, priority);

                            switch (prefixHandleResult) {
                            case SUCCESS: {
                                langManager.sendMessage(sender, "edited", new Placeholder("%prefix%", prefixName));
                                break;
                            }
                            case EXISTS: {
                                langManager.sendMessage(sender, "not_existent",
                                        new Placeholder("%prefix%", prefixName));
                                break;
                            }
                            case PERMISSION: {
                                langManager.sendMessage(sender, "permission_error");
                                break;
                            }
                            case ERROR: {
                                langManager.sendMessage(sender, "error");
                                break;
                            }
                            }
                        } else {
                            langManager.sendMessage(sender, "edit_usage", new Placeholder("%label%", label));
                        }
                    } else if (args[0].equalsIgnoreCase("priorityall")) {
                        if (args.length > 1) {
                            final int priority = getInteger(args[1]);
                            final PrefixHandlerResult prefixHandleResult = prefixHandler.editPriority(player, priority);

                            switch (prefixHandleResult) {
                            case SUCCESS: {
                                langManager.sendMessage(sender, "priority_all", new Placeholder("%prefix%", ""));
                                break;
                            }
                            case EXISTS: {
                                langManager.sendMessage(sender, "no_prefixes");
                                break;
                            }
                            case PERMISSION: {
                                langManager.sendMessage(sender, "permission_error");
                                break;
                            }
                            case ERROR: {
                                langManager.sendMessage(sender, "error");
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
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        onPrefixCommand(sender, command, label, args);

        return true;
    }
}
