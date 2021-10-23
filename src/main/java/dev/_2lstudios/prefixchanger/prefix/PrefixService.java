package dev._2lstudios.prefixchanger.prefix;

import java.util.HashMap;
import java.util.Map;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.FindOption;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import dev._2lstudios.prefixchanger.prefix.entities.Prefix;

public class PrefixService {
    private static final Map<String, Object> EMPTY_MAP = new HashMap<>();
    
    private final Repository<Prefix> repository;

    public PrefixService() {
        this.repository = MilkshakeORM.getRepository(Prefix.class);
    }

    public Prefix create(final String prefixName, final String displayName, final String materialName, final int data) {
        final Prefix prefix = new Prefix();

        prefix.setName(prefixName);
        prefix.setDisplayName(displayName);
        prefix.setMaterialName(materialName);
        prefix.setData(data);
        prefix.save();

        return prefix;
    }

    public Prefix[] getPrefixes() {
        return repository.findMany(EMPTY_MAP, new FindOption().sort("priority"));
    }

    public Prefix getPrefix(final String prefixName) {
        return repository.findOne(MapFactory.create("name", prefixName));
    }

    public long delete(final String prefixName) {
        return repository.deleteMany(MapFactory.create("name", prefixName));
    }
}
