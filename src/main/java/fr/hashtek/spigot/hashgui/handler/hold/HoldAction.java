package fr.hashtek.spigot.hashgui.handler.hold;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HoldAction
{

    /**
     * Function called when item is held.
     *
     * @param	player	Player who is holding the item
     * @param	item	Clicked item
     * @param	slot	Clicked slot
     */
    void execute(
        Player player,
        ItemStack item,
        int slot
    );

}
