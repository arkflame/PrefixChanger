package dev._2lstudios.prefixchanger.prefix;

import java.util.UUID;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;

public class PrefixPlayerService {
    private final Repository<PrefixPlayer> repository;

    public PrefixPlayerService() {
        this.repository = MilkshakeORM.getRepository(PrefixPlayer.class);
    }

    public PrefixPlayer getByUUID(final String uuid) {
        return repository.findOne(MapFactory.create("uuid", uuid));
    }

    public PrefixPlayer getByUUID(final UUID uuid) {
        return getByUUID(uuid.toString());
    }

    public PrefixPlayer getByName(final String name) {
        return repository.findOne(MapFactory.create("name", name));
    }
}
