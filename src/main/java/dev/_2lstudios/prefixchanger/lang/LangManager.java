package dev._2lstudios.prefixchanger.lang;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.placeholders.Placeholder;
import dev._2lstudios.prefixchanger.utils.ConfigUtil;
import dev._2lstudios.prefixchanger.utils.LocaleUtil;

public class LangManager {
    private final Map<String, Lang> languages = new HashMap<>();
    private final String defaultLocale;

    public LangManager(final ConfigUtil configUtil, final String defaultLocale) {
        final File folder = new File(configUtil.getDataFolder() + "/lang/");

        LangExtractor.extractAll(folder);

        if (folder.isDirectory()) {
            for (final File file : folder.listFiles()) {
                final Configuration config = configUtil.get(file.getPath());

                languages.put(file.getName().replace(".yml", ""), new Lang(config));
            }
        }

        this.defaultLocale = defaultLocale;
    }

    private String getLocale(final CommandSender sender) {
        if (sender instanceof Player) {
            return LocaleUtil.getLocale((Player) sender).toLowerCase();
        }

        return null;
    }

    private Lang getLang(final String rawLocale) {
        if (rawLocale != null && rawLocale.contains("_")) {
            final String[] locale = rawLocale.split("_");
            final String langCode = locale[0];
            final String region = locale[1];

            if (languages.containsKey(langCode + "_" + region)) {
                return languages.get(langCode + "_" + region);
            } else if (languages.containsKey(langCode)) {
                return languages.get(langCode);
            } else if (languages.containsKey(defaultLocale)) {
                return languages.get(defaultLocale);
            }
        } else if (languages.containsKey(defaultLocale)) {
            return languages.get(defaultLocale);
        }

        return null;
    }

    public String getMessage(CommandSender sender, String key, final Placeholder... placeholders) {
        final String rawLocale = getLocale(sender);
        final Lang lang = getLang(rawLocale);

        if (lang != null) {
            return ChatColor.translateAlternateColorCodes('&', lang.getMessage(key, placeholders));
        } else {
            return ChatColor.translateAlternateColorCodes('&', "&cNo lang files had been found!");
        }
    }

    public void sendMessage(CommandSender sender, String key, final Placeholder... placeholders) {
        if (sender != null) {
            sender.sendMessage(getMessage(sender, key, placeholders));
        }
    }
}
