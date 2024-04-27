package fr.hashtek.spigot.hashgui.handler.destroy;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DestroyAction
{

	/**
	 * Function called when a block is destroyed using a
	 * specific item.
	 * 
	 * @param	player	Player who destroyed the block
	 * @param	item	Used item
	 * @param	block	Destroyed block
	 */
    void execute(
		Player player,
		ItemStack item,
		Block block
    );
	
}
