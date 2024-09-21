package fr.hashtek.spigot.hashgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGui implements InventoryHolder
{

	private final Inventory gui;
	
	private final int size;
	private final String title;
	
	
	/**
	 * Creates a new GUI.
	 * 
	 * @param	title	GUI's title
	 * @param	size	GUI's amount of lines (must be between 1 and 6).
	 */
	public HashGui(String title, int size)
	{
		if (size < 1 || size > 6)
			throw new IllegalArgumentException("Invalid size. A GUI can only have 1 to 6 lines.");
		
		this.title = title;
		this.size = size;
		
		this.gui = Bukkit.createInventory(this, this.getTotalSize(), this.title);
	}
	
	/**
	 * Creates a new instance of HashGUI from an existing Inventory.
	 * 
	 * @param	inventory	Inventory
	 */
	public HashGui(Inventory inventory)
	{
		this.gui = inventory;
		
		this.size = this.gui.getSize() / 9;
		this.title = this.gui.getTitle();
	}
	
	
	/**
	 * Opens the GUI for a player.
	 * 
	 * @param	player	Player
	 */
	public void open(Player player)
	{
		if (player.getOpenInventory() != null)
			this.close(player);
		
		player.openInventory(this.gui);
	}
	
	/**
	 * Closes the GUI for a player.
	 * 
	 * @param	player	Player
	 */
	public void close(Player player)
	{
		player.closeInventory();
	}
	
	/**
	 * Sets an item in the GUI.
	 * 
	 * @param	index	Slot index
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGui setItem(int index, ItemStack item)
	{
		this.gui.setItem(index, item);
		return this;
	}
	
	/**
	 * Sets an item in the GUI and registers its handler (if it exists).
	 * 
	 * @param	index	Slot index
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGui setItem(int index, HashItem item)
	{
		this.setItem(index, item.getItemStack());
		return this;
	}
	
	/**
	 * Removes an item from the GUI.
	 * 
	 * @param	index	Slot index
	 * @return	Returns itself.
	 */
	public HashGui removeItem(int index)
	{
		this.gui.setItem(index, null);
		return this;
	}

	/**
	 * Replaces every item of a certain name by another item.
	 * FIXME: You may want to perform further checks, not only the name.
	 *
	 * @param	toReplace		Name of the item to replace
	 * @param	toReplaceWith	Replacing item
	 * @return	Returns itself.
	 */
	public HashGui replaceAll(String toReplace, HashItem toReplaceWith)
	{
		for (int k = 0; k < this.getTotalSize(); k++) {
			final ItemStack item = this.gui.getItem(k);

			if (item == null || item.getType() == Material.AIR)
				continue;

			if (item.getItemMeta().getDisplayName().equals(toReplace))
				this.gui.setItem(k, toReplaceWith.getItemStack());
		}

		return this;
	}
	
	/**
	 * Updates player's current open inventory.
	 * 
	 * @param	player	Player
	 * @return	Returns itself.
	 */
	public HashGui update(Player player)
	{
		player.updateInventory();
		return this;
	}
	

	/**
	 * @return	GUI's inventory
	 */
	@Override
	public Inventory getInventory() {
		return this.gui;
	}
	
	/**
	 * @return	GUI's number of lines
	 */
	public int getSize()
	{
		return this.size;
	}
	
	/**
	 * @return	GUI's number of slots
	 * @apiNote	One line is 9 slots.
	 */
	public int getTotalSize()
	{
		return this.size * 9;
	}
	
	/**
	 * @return	GUI's title
	 */
	public String getTitle()
	{
		return this.title;
	}

}
