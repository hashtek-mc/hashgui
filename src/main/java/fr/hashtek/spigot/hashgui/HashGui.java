package fr.hashtek.spigot.hashgui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashitem.HashItem;

import java.util.Objects;

public class HashGui implements InventoryHolder
{

	private final Inventory gui;
	
	private final int size;
	private final Component title;
	private final boolean areItemsLockedIn;


	/**
	 * Creates a new GUI.
	 *
	 * @param	title		GUI's title
	 * @param	size		GUI's amount of lines (must be between 1 and 6)
	 */
	public HashGui(Component title, int size)
	{
		this(title, size, true);
	}

	/**
	 * Creates a new GUI.
	 *
	 * @param	title				GUI's title
	 * @param	size				GUI's amount of lines (must be between 1 and 6)
	 * @param 	areItemsLockedIn	Are items present in the gui locked inside the inventory?
	 */
	public HashGui(
		Component title,
		int size,
		boolean areItemsLockedIn
	)
	{
		if (size < 1 || size > 6) {
			throw new IllegalArgumentException("Invalid size. A GUI can only have 1 to 6 lines.");
		}
		
		this.title = title;
		this.size = size;
		this.areItemsLockedIn = areItemsLockedIn;

		this.gui = Bukkit.createInventory(this, this.getTotalSize(), this.title);
	}

	/**
	 * Creates a new instance of HashGUI from an existing Inventory.
	 *
	 * @param	inventory	Inventory
	 */
	public HashGui(Inventory inventory)
	{
		this(inventory, true);
	}

	/**
	 * Creates a new instance of HashGUI from an existing Inventory.
	 * TODO(l.81): Try to get the inventory's title.
	 *
	 * @param	inventory	Inventory
	 * @param 	areItemsLockedIn	Are items present in the gui locked inside the inventory?
	 */
	public HashGui(Inventory inventory, boolean areItemsLockedIn)
	{
		this.gui = inventory;
		
		this.size = this.gui.getSize() / 9;
		this.title = Component.text("??");
		this.areItemsLockedIn = areItemsLockedIn;
	}

	
	/**
	 * Opens the GUI for a player.
	 * 
	 * @param	player	Player
	 */
	public void open(Player player)
	{
		player.getOpenInventory().close();
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
	 * Function to execute when the GUI is closed by any way.
	 *
	 * @param	player	Player
	 * @param	gui		Closed GUI
	 * @apiNote By default, this function is empty.</br>
	 * 			Override it if you want to execute some instructions on GUI close.
	 */
	public void onClose(Player player, HashGui gui) {}

	/**
	 * Adds an item in the GUI to the first free slot.
	 *
	 * @param	item	Item to add
	 * @return	Itself
	 */
	public HashGui addItem(ItemStack item)
	{
		this.gui.addItem(item);
		return this;
	}

	/**
	 * Adds an item in the GUI to the first free slot
	 * and registers its handlers (if any exists).
	 *
	 * @param	item	Item to add
	 * @return	Itself
	 */
	public HashGui addItem(HashItem item)
	{
		return this.addItem(item.getItemStack());
	}

	/**
	 * Sets an item in the GUI at a given index.
	 * 
	 * @param	index	Slot index
	 * @param	item	Item
	 * @return	Itself
	 */
	public HashGui setItem(int index, ItemStack item)
	{
		this.gui.setItem(index, item);
		return this;
	}
	
	/**
	 * Sets an item in the GUI at a given index
	 * and registers its handlers (if any exists).
	 * 
	 * @param	index	Slot index
	 * @param	item	Item to add
	 * @return	Itself
	 */
	public HashGui setItem(int index, HashItem item)
	{
		return this.setItem(index, item.getItemStack());
	}
	
	/**
	 * Removes an item from the GUI.
	 * 
	 * @param	index	Slot index
	 * @return	Itself
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
	 * @return	Itself
	 */
	public HashGui replaceAll(Component toReplace, HashItem toReplaceWith)
	{
		for (int k = 0; k < this.getTotalSize(); k++) {
			final ItemStack item = this.gui.getItem(k);

			if (item == null || item.getType() == Material.AIR) {
				continue;
			}

			if (Objects.equals(item.getItemMeta().displayName(), toReplace)) {
				this.gui.setItem(k, toReplaceWith.getItemStack());
			}
		}

		return this;
	}
	
	/**
	 * Updates player's current open inventory.
	 * 
	 * @param	player	Player
	 * @return	Itself
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
	public Inventory getInventory()
	{
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
	public Component getTitle()
	{
		return this.title;
	}

	/**
	 * @return	True if items are locked inside the gui
	 */
	public boolean areItemsLockedIn()
	{
		return this.areItemsLockedIn;
	}

}
