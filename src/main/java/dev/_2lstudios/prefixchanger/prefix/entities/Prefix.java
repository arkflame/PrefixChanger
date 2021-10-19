package dev._2lstudios.prefixchanger.prefix.entities;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class Prefix extends Entity {
    @ID
    public String id;

    @Prop
    public String name;

    @Prop
    public String displayName;

    @Prop
    public String materialName;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(final String materialName) {
        this.materialName = materialName;
    }
}
