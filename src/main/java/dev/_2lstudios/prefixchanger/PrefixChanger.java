package dev._2lstudios.prefixchanger;

import com.dotphin.milkshakeorm.DatabaseType;
import com.dotphin.milkshakeorm.MilkshakeORM;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.prefixchanger.commands.PrefixCommand;
import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.listeners.InventoryClickListener;
import dev._2lstudios.prefixchanger.listeners.PlayerQuitListener;
import dev._2lstudios.prefixchanger.menu.MenuManager;
import dev._2lstudios.prefixchanger.placeholderapi.PrefixChangerPlaceholder;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixPlayerService;
import dev._2lstudios.prefixchanger.prefix.PrefixService;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;
import dev._2lstudios.prefixchanger.prefix.menu.PrefixMenuHandler;
import dev._2lstudios.prefixchanger.utils.ConfigUtil;

public class PrefixChanger extends JavaPlugin {
    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();
        final ConfigUtil configUtil = new ConfigUtil(this);
        final Configuration config = configUtil.create("%datafolder%/config.yml", "config.yml")
                .get("%datafolder%/config.yml");

        MilkshakeORM.connect(DatabaseType.MONGODB, config.getString("database_uri"));
        MilkshakeORM.addRepository(Prefix.class);
        MilkshakeORM.addRepository(PrefixPlayer.class);

        final LangManager langManager = new LangManager(configUtil, config.getString("lang"));
        final MenuManager menuManager = new MenuManager();
        final PrefixService prefixService = new PrefixService();
        final PrefixPlayerService prefixPlayerService = new PrefixPlayerService();
        final PrefixHandler prefixHandler = new PrefixHandler(prefixService, prefixPlayerService);
        final PrefixMenuHandler prefixMenuHandler = new PrefixMenuHandler(this, langManager, menuManager, prefixHandler, prefixService, prefixPlayerService);
        final InventoryClickListener inventoryClickListener = new InventoryClickListener(menuManager);

        pluginManager.registerEvents(inventoryClickListener, this);
        pluginManager.registerEvents(new PlayerQuitListener(inventoryClickListener), this);

        new PrefixChangerPlaceholder(this, prefixService, prefixPlayerService).register();

        getCommand("prefixchanger").setExecutor(new PrefixCommand(this, langManager, prefixHandler, prefixMenuHandler));
    }
}