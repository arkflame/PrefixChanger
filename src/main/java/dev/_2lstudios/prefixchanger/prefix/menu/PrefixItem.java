package dev._2lstudios.prefixchanger.prefix.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.prefixchanger.menu.MenuItemClickable;
import dev._2lstudios.prefixchanger.prefix.PrefixHandler;
import dev._2lstudios.prefixchanger.prefix.PrefixHandlerResult;
import dev._2lstudios.prefixchanger.prefix.entities.Prefix;

public class PrefixItem extends MenuItemClickable {
    private final static String ITEM_SUFFIX = ChatColor.translateAlternateColorCodes('&', "&7 (%prefix%)");
    
    private final PrefixHandler prefixHandler;
    private final Prefix prefix;

    public String getItemSuffix(final String prefixName) {
        return ITEM_SUFFIX.replace("%prefix%", prefixName);
    }

    public PrefixItem(final int slot, final Prefix prefix,
            final PrefixHandler prefixHandler) {
        super(new ItemStack(Material.FEATHER), slot);
        this.prefix = prefix;
        this.prefixHandler = prefixHandler;

        final ItemStack itemStack = getItemStack();
        final Material prefixMaterial = Material.getMaterial(prefix.getMaterialName());
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (prefixMaterial != null) {
            itemStack.setType(prefixMaterial);
        }

        itemMeta.setDisplayName(prefix.getDisplayName() + getItemSuffix(prefix.getName()));
        itemStack.setItemMeta(itemMeta);
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
