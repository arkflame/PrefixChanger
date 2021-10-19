package dev._2lstudios.prefixchanger.prefix.menu;

import java.util.UUID;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.prefixchanger.menu.MenuItemClickable;
import dev._2lstudios.prefixchanger.prefix.entities.PrefixPlayer;

public class GhostPrefixItem extends MenuItemClickable {
    private final Repository<PrefixPlayer> prefixPlayerRepository;

    public GhostPrefixItem(final int slot) {
        super(new ItemStack(Material.SHEARS), slot);
        this.prefixPlayerRepository = MilkshakeORM.getRepository(PrefixPlayer.class);

        final ItemStack itemStack = getItemStack();
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName("Prefix Fantasma");
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public void click(final Player player) {
        final String playerName = player.getName();
        final UUID playerUUID = player.getUniqueId();
        PrefixPlayer prefixPlayer = prefixPlayerRepository
                .findOne(MapFactory.create("uuid", playerUUID.toString()));

        if (prefixPlayer == null) {
            prefixPlayer = prefixPlayerRepository.findOne(MapFactory.create("name", playerName));
        }

        if (prefixPlayer == null) {
            prefixPlayer = new PrefixPlayer();
        }

        prefixPlayer.setName(playerName);
        prefixPlayer.setUUID(playerUUID);
        prefixPlayer.setPrefix("");
        prefixPlayer.save();

        player.sendMessage("Activaste el prefix 'fantasma' correctamente!");
    }
}
