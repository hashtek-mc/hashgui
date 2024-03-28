package fr.hashtek.spigot.hashgui.handler.click;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashgui.HashGui;

public interface ClickAction
{
	
	/**
	 * Function called when item is clicked.
	 * 
	 * @param	player	Player who clicked
	 * @param	gui		Clicked GUI
	 * @param	item	Clicked item
	 * @param	slot	Clicked slot
	 */
	void execute(
		Player player,
		HashGui gui,
		ItemStack item,
		int slot
	);

}
