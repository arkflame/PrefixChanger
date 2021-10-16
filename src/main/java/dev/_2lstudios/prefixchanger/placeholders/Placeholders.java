package dev._2lstudios.prefixchanger.placeholders;

public class Placeholders {
    public static String replace(String string, final Placeholder ...placeholders) {
        for (final Placeholder placeholder : placeholders) {
            string = placeholder.replace(string);
        }

        return string;
    }
}
