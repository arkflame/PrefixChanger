package dev._2lstudios.prefixchanger.prefix.menu;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.prefixchanger.menu.MenuItemClickable;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixHandlerResult;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;

public class PrefixItem extends MenuItemClickable {
    private final PrefixHandler prefixHandler;
    private final Prefix prefix;

    public PrefixItem(final ItemStack itemStack, final int slot, final Prefix prefix,
            final PrefixHandler prefixHandler) {
        super(itemStack, slot);
        this.prefix = prefix;
        this.prefixHandler = prefixHandler;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    @Override
    public void click(final Player player) {
        final String prefixName = prefix.getName();
        final PrefixHandlerResult prefixHandlerResult = prefixHandler.changePrefix(player, prefixName);

        switch (prefixHandlerResult) {
            case SUCCESS: {
                player.sendMessage("Your prefix was changed to '" + prefixName + "'!");
                player.playSound(player.getLocation(), Sound.valueOf("EXPERIENCE_PICKUP"), 1f, 0.5f);
                break;
            }
            case EXISTS: {
                player.sendMessage("Prefix '" + prefixName + "' doesn't exist!");
                player.playSound(player.getLocation(), Sound.valueOf("ENDERMAN_TELEPORT"), 1f, 0.5f);
                break;
            }
            case PERMISSION: {
                player.sendMessage("No permission to use prefix '" + prefixName + "'!");
                player.playSound(player.getLocation(), Sound.valueOf("ENDERMAN_TELEPORT"), 1f, 0.5f);
                break;
            }
            case ERROR: {
                player.sendMessage("Error while trying to change your prefix!");
                player.playSound(player.getLocation(), Sound.valueOf("ENDERMAN_TELEPORT"), 1f, 0.5f);
                break;
            }
        }
    }
}
