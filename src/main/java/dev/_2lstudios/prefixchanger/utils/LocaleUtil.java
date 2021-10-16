package dev._2lstudios.prefixchanger.utils;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

public class LocaleUtil {
	public static String getLocale(final Player player) {
		try {
			final Object handler = player.getClass().getMethod("getHandle").invoke(player);
			final Field locale = handler.getClass().getField("locale");

			locale.setAccessible(true);

			return String.valueOf(locale.get(handler));
		} catch (final Exception exception) { /* Ignored */ }

		return "en_US";
	}
}