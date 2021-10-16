package dev._2lstudios.prefixchanger.prefix;

import java.util.Arrays;
import java.util.List;

public class Prefix {
    private final String prefix;
    private final String displayname;
    private final List<String> lore;

    public Prefix(final String prefix, final String displayname, final String lore) {
        this.prefix = prefix;
        this.displayname = displayname;
        this.lore = Arrays.asList(lore.split("\n"));
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDisplayname() {
        return displayname;
    }

    public List<String> getLore() {
        return lore;
    }
}
