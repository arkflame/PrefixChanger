package dev._2lstudios.prefixchanger;

import com.dotphin.milkshakeorm.DatabaseType;
import com.dotphin.milkshakeorm.MilkshakeORM;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.prefixchanger.commands.PrefixCommand;
import dev._2lstudios.prefixchanger.entities.Prefix;
import dev._2lstudios.prefixchanger.entities.PrefixPlayer;
import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.placeholderapi.PrefixChangerPlaceholder;
import dev._2lstudios.prefixchanger.utils.ConfigUtil;

public class PrefixChanger extends JavaPlugin {
    @Override
    public void onEnable() {
        final ConfigUtil configUtil = new ConfigUtil(this);

        configUtil.create("%datafolder%/config.yml", "config.yml");
    
        final Configuration config = configUtil.get("%datafolder%/config.yml");
        final LangManager langManager = new LangManager(configUtil, config.getString("lang"));

        MilkshakeORM.connect(DatabaseType.MONGODB, config.getString("uri"));
        MilkshakeORM.addRepository(Prefix.class);
        MilkshakeORM.addRepository(PrefixPlayer.class);

        new PrefixChangerPlaceholder(this).register();

        getCommand("prefixchanger").setExecutor(new PrefixCommand());
    }
}