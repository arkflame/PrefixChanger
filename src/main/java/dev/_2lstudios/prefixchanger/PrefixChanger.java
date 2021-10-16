package dev._2lstudios.prefixchanger;

import com.dotphin.milkshakeorm.DatabaseType;
import com.dotphin.milkshakeorm.MilkshakeORM;

import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.prefixchanger.commands.PrefixCommand;
import dev._2lstudios.prefixchanger.entities.Prefix;
import dev._2lstudios.prefixchanger.entities.PrefixPlayer;
import dev._2lstudios.prefixchanger.placeholderapi.PrefixChangerPlaceholder;

public class PrefixChanger extends JavaPlugin {
    @Override
    public void onEnable() {
        MilkshakeORM.connect(DatabaseType.MONGODB, "mongodb://localhost/database-name");
        MilkshakeORM.addRepository(Prefix.class);
        MilkshakeORM.addRepository(PrefixPlayer.class);

        new PrefixChangerPlaceholder(this).register();

        getCommand("prefixchanger").setExecutor(new PrefixCommand());
    }
}