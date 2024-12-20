package fr.hashtek.spigot.hashgui.mask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.hashtek.spigot.hashitem.common.DefaultItems;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashitem.HashItem;

public class Mask
{

	private final HashGui gui;
	private final Map<Character, HashItem> items;
	private final List<String> pattern;


	/**
	 * Creates a mask for a GUI, from an Inventory.
	 * Mainly used for Player inventories.
	 *
	 * @param	inventory	Inventory
	 */
	public Mask(Inventory inventory)
	{
		this(new HashGui(inventory));
	}

	/**
	 * Creates a mask for a GUI, from a GUI.
	 * 
	 * @param	gui		HashGui.
	 */
	public Mask(HashGui gui)
	{
		this.gui = gui;
		this.items = new HashMap<Character, HashItem>();
		this.pattern = new ArrayList<String>();
		
		for (int k = 0; k < this.gui.getSize(); k++) {
			this.pattern.add(null);
		}
	}
	
	
	/**
	 * Sets an item for a character, which will be used in {@link Mask#pattern(String)}.
	 * 
	 * @param	character	Character index
	 * @param	item		Item that will be used
	 * @return	Itself
	 */
	public Mask setItem(Character character, HashItem item)
	{
		this.items.put(character, item);
		return this;
	}
	
	/**
	 * Sets an item for a character, which will be used in {@link Mask#pattern(String)}.
	 * @apiNote	Because item is an {@link ItemStack}, handlers won't be supported.
	 * 
	 * @param	character	Character index
	 * @param	item		Item that will be used
	 * @return	Itself
	 */
	public Mask setItem(Character character, ItemStack item)
	{
		return this.setItem(character, new HashItem(item));
	}
	
	/**
	 * Sets a pattern with a specific index in the mask.
	 * 
	 * @param	index						Line index in the GUI
	 * @param	pattern						Pattern
	 * @return	Itself
	 * @throws	IllegalArgumentException	Wrong pattern (not 9 characters long)
	 * @throws	IndexOutOfBoundsException	Wrong index
	 */
	public Mask pattern(int index, String pattern)
		throws IllegalArgumentException, IndexOutOfBoundsException
	{
		final int guiSize = this.gui.getSize();
		
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index must be greater than or equal to 0 (index is " + index + ")");
		}
		
		if (index > guiSize) {
			throw new IndexOutOfBoundsException("Index is too big (index is " + index + ", GUI size is " + guiSize + ").");
		}
		
		if (pattern.length() != 9) {
			throw new IllegalArgumentException("A pattern must be 9 characters long.");
		}
		
		if (index == 0) {
			int patternIndex = this.pattern.indexOf(null);
			
			if (patternIndex == -1) {
				throw new IllegalStateException("Patterns are full.");
			}
			this.pattern.set(patternIndex, pattern);
		} else {
			this.pattern.set(index - 1, pattern);
		}

		return this;
	}
	
	/**
	 * Adds a pattern in the mask.
	 * 
	 * @param	pattern		Pattern
	 * @return	Itself
	 * @throws	IllegalArgumentException	Wrong pattern (not 9 characters long)
	 */
	public Mask pattern(String pattern)
		throws IllegalArgumentException
	{
		return this.pattern(0, pattern);
	}
	
	/**
	 * Applies the mask to the linked GUI.
	 * <p>
	 * <strong>NOTE:</strong>
	 * <br>
	 *   - Spaces (<code> </code>) are <u>replaced by {@link Material#AIR}</u>.
	 * <br>
	 *   - At (<code>@</code>) are <u>ignored</u>.
	 * 
	 * @return	Itself
	 */
	public Mask apply()
	{
	    for (int rowIndex = 0; rowIndex < this.pattern.size(); rowIndex++) {
	        final String iteratedPattern = this.pattern.get(rowIndex);
	        if (iteratedPattern == null) {
				continue;
			}

	        for (int columnIndex = 0; columnIndex < iteratedPattern.length(); columnIndex++) {
	            final char iteratedPatternChar = iteratedPattern.charAt(columnIndex);
				final int guiIndex = columnIndex + (rowIndex * 9);

				if (iteratedPatternChar == '@') {
					continue;
				}

	            if (iteratedPatternChar == ' ') {
					final ItemStack i = this.gui.getInventory().getItem(guiIndex);

					if (i != null && i.getType() != Material.AIR) {
						this.gui.setItem(guiIndex, new ItemStack(Material.AIR));
					}
					continue;
				}

	            this.gui.setItem(
					guiIndex,
					this.items.getOrDefault(
						iteratedPatternChar,
						DefaultItems.ITEM_NOT_FOUND.getItem()
					)
				);
	        }
	    }
	    
	    return this;
	}

	/**
	 * @return	Linked GUI
	 */
	public HashGui getGui()
	{
		return this.gui;
	}

	/**
	 * @return	Items
	 */
	public Map<Character, HashItem> getItems()
	{
		return this.items;
	}

	/**
	 * Returns the item linked to a character.
	 *
	 * @param	character	Character
	 * @return	Item linked to the given character
	 */
	public HashItem getItem(Character character)
	{
		return this.items.get(character);
	}

	/**
	 * @return	Pattern
	 */
	public List<String> getPattern()
	{
		return this.pattern;
	}
	
}
