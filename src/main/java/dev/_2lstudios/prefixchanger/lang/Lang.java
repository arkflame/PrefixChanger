package dev._2lstudios.prefixchanger.lang;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.prefixchanger.placeholders.Placeholder;
import dev._2lstudios.prefixchanger.placeholders.Placeholders;

public class Lang {
    private final static String NOT_FOUND_TEXT = "&c&lERROR: &cKey %key% doesn't exist in the locale config file!";
    private final Map<String, String> messages = new HashMap<>();

    private void loadMessages(final ConfigurationSection section) {
        for (final String key : section.getKeys(false)) {
            final Object object = section.get(key);

            if (object instanceof ConfigurationSection) {
                loadMessages((ConfigurationSection) object);
            } else if (object instanceof String) {
                final String currentPath = section.getCurrentPath();
                final String fullPath;

                if (currentPath.isEmpty()) {
                    fullPath = key;
                } else {
                    fullPath = section.getCurrentPath() + "." + key;
                }

                messages.put(fullPath, (String) object);
            }
        }
    }

    Lang(final Configuration config) {
        loadMessages(config);
    }

    public String getMessage(final String key, final Placeholder... placeholders) {
        if (messages.containsKey(key)) {
            return Placeholders.replace(messages.get(key), placeholders);
        } else {
            return Placeholders.replace(NOT_FOUND_TEXT, new Placeholder("%key%", key));
        }
    }
}
