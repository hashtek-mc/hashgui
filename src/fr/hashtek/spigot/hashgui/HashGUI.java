package fr.hashtek.spigot.hashgui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGUI implements InventoryHolder {

	private final Inventory gui;
	
	private final int size;
	private final String title;
	
	
	/**
	 * Creates a new GUI.
	 * 
	 * @param	title	GUI's title
	 * @param	size	GUI's amount of lines (must be between 1 and 6).
	 */
	public HashGUI(String title, int size)
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
	public HashGUI(Inventory inventory)
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
	public HashGUI setItem(int index, ItemStack item)
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
	public HashGUI setItem(int index, HashItem item)
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
	public HashGUI removeItem(int index)
	{
		this.gui.setItem(index, null);
		return this;
	}
	
	/**
	 * Updates player's current open inventory.
	 * 
	 * @param	player	Player
	 * @return	Returns itself.
	 */
	public HashGUI update(Player player)
	{
		player.updateInventory();
		return this;
	}
	

	/**
	 * Returns the Inventory in this GUI.
	 * 
	 * @return	Inventory
	 */
	@Override
	public Inventory getInventory() {
		return this.gui;
	}
	
	/**
	 * Returns the number of lines in this GUI.
	 * 
	 * @return	Number of lines
	 */
	public int getSize()
	{
		return this.size;
	}
	
	/**
	 * Returns the number of slots in this GUI (1 line is 9 slots).
	 * 
	 * @return	Number of slots
	 */
	public int getTotalSize()
	{
		return this.size * 9;
	}
	
	/**
	 * Returns the title of this GUI.
	 * 
	 * @return	Title
	 */
	public String getTitle()
	{
		return this.title;
	}

}
