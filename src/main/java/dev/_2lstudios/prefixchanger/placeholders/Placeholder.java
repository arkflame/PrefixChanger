package dev._2lstudios.prefixchanger.placeholders;

public class Placeholder {
    private final String key;
    private final String value;

    public Placeholder(final String key, final Object value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public String replace(final String string) {
        return string.replace(key, value);
    }
}
