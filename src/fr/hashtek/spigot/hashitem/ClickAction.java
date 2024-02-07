package fr.hashtek.spigot.hashitem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashgui.HashGUI;

public interface ClickAction {
	
	/**
	 * Function called when item is clicked.
	 * 
	 * @param	player		Player who clicked
	 * @param	gui			Clicked GUI
	 * @param	clickedItem	Clicked item
	 * @param	clickedSlot	Clicked slot
	 */
	public void execute(
		Player player,
		HashGUI gui,
		ItemStack clickedItem,
		int clickedSlot
	);

}
