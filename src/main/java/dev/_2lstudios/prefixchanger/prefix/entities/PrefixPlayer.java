package dev._2lstudios.prefixchanger.prefix.entities;

import java.util.UUID;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PrefixPlayer extends Entity {
    @ID
    public String id;

    @Prop
    public String uuid;

    @Prop
    public String name;

    @Prop
    public String prefix;

    public UUID getUUID() {
        if (uuid == null) {
            return null;
        }
        
        return UUID.fromString(uuid);
    }

    public void setUUID(final UUID uuid) {
        this.uuid = uuid.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
