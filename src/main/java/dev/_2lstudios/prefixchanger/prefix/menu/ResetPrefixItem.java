package dev._2lstudios.prefixchanger.prefix.menu;

import java.util.UUID;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.prefixchanger.lang.LangManager;
import dev._2lstudios.prefixchanger.menu.MenuItemClickable;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;

public class ResetPrefixItem extends MenuItemClickable {
    private final LangManager langManager;
    private final Repository<PrefixPlayer> prefixPlayerRepository;

    public ResetPrefixItem(final LangManager langManager, final String displayName, final int slot) {
        super(new ItemStack(Material.SHEARS), slot);
        this.langManager = langManager;
        this.prefixPlayerRepository = MilkshakeORM.getRepository(PrefixPlayer.class);

        final ItemStack itemStack = getItemStack();
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public void click(final Player player) {
        final UUID playerUUID = player.getUniqueId();
        final PrefixPlayer prefixPlayer = prefixPlayerRepository.findOne(MapFactory.create("uuid", playerUUID.toString()));

        if (prefixPlayer != null) {
            prefixPlayer.delete();
        }

        player.sendMessage(langManager.getMessage(player, "reset"));
    }
}
