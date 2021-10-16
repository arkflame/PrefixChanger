package dev._2lstudios.prefixchanger.hooks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIHook {
    public static String setPlaceholders(final OfflinePlayer player, final String text) {
        if (text != null) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                return PlaceholderAPI.setPlaceholders(player, text);
            }
        }

        return text;
    }

    public static String setPlaceholders(final CommandSender sender, final String text) {
        return text;
    }
}
