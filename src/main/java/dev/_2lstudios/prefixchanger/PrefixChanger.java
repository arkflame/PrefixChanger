package dev._2lstudios.prefixchanger;

import com.dotphin.milkshakeorm.DatabaseType;
import com.dotphin.milkshakeorm.MilkshakeORM;

import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.prefixchanger.entities.Prefix;
import dev._2lstudios.prefixchanger.entities.PrefixPlayer;

public class PrefixChanger extends JavaPlugin {
    @Override
    public void onEnable() {
        MilkshakeORM.connect(DatabaseType.MONGODB, "mongodb://localhost/database-name");
        MilkshakeORM.addRepository(Prefix.class);
        MilkshakeORM.addRepository(PrefixPlayer.class);
    }
}