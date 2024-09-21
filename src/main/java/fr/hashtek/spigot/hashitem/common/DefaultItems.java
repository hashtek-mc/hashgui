package fr.hashtek.spigot.hashitem.common;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * This enum stores the default items.
 */
public enum DefaultItems
{

    ITEM_NOT_FOUND(
        new HashItem(Material.BARRIER)
            .setName(ChatColor.RED + "Item not found.")
            .addLore("" + ChatColor.GRAY + ChatColor.ITALIC + "I am a poor dev that can't do his work properly.")
    ),

    ITEM_BUILD_FAIL(
        new HashItem(Material.BARRIER)
            .setName(ChatColor.RED + "Item not found.")
            .addLore("" + ChatColor.GRAY + ChatColor.ITALIC + "I am a poor dev that can't do his work properly.")
    );


    private final HashItem item;


    /**
     * Creates a new Default Item.
     *
     * @param   item    Item
     */
    DefaultItems(HashItem item)
    {
        this.item = item
            .setUntakable(true);
    }


    /**
     * @return  Item
     */
    public HashItem getItem()
    {
        return this.item;
    }

}
