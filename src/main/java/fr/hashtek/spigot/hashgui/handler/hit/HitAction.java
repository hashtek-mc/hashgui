package fr.hashtek.spigot.hashgui.handler.hit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HitAction
{

	/**
	 * Function called when item is used in a hit.
	 * 
	 * @param	attacker	Player who hit
	 * @param	victim	Player who got hit
	 * @param	item	Used item
	 */
    void execute(
		Player attacker,
		Player victim,
		ItemStack item
    );
	
}
