package dev._2lstudios.prefixchanger.database;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev._2lstudios.prefixchanger.prefix.Prefix;

public interface PrefixDatabase {
    public Collection<Prefix> getPrefixes();
    
    public Prefix getPrefixByName(final String prefixName);

    public Prefix getPrefixByPlayer(final String uuidString);

    public Prefix getPrefixByPlayer(final UUID uuid);

    public Prefix getPrefixByPlayer(final Player player);
}
