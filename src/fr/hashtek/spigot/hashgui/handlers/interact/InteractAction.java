package fr.hashtek.spigot.hashgui.handlers.interact;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface InteractAction {

	/**
	 * Function called when item is interacted.
	 * 
	 * @param	player	Player who interacted
	 * @param	item	Interacted item
	 * @param	slot	Interacted slot
	 */
    boolean execute(
		Player player,
		ItemStack item,
		int slot
    );
	
}
