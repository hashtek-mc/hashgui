package fr.hashtek.spigot.hashgui.mask;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashitem.HashItem;

public class Mask
{

	private final HashGui gui;
	private final HashMap<Character, HashItem> items;
	private final ArrayList<String> pattern;
	
	
	/**
	 * Creates a mask for a GUI, from a GUI.
	 * 
	 * @param	gui	HashGUI.
	 */
	public Mask(HashGui gui) {
		this.gui = gui;
		this.items = new HashMap<Character, HashItem>();
		this.pattern = new ArrayList<String>();
		
		for (int k = 0; k < this.gui.getSize(); k++)
			this.pattern.add(null);
	};
	
	
	/**
	 * Sets an item for a character, which will be used in {@link Mask#pattern(String)}.
	 * 
	 * @param	character	Character index
	 * @param	item		Item that will be used
	 * @return	Returns itself.
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
	 * @return	Returns itself.
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
	 * @return	Returns itself.
	 * @throws	IllegalArgumentException	Wrong pattern (not 9 characters long)
	 * @throws	IndexOutOfBoundsException	Wrong index
	 */
	public Mask pattern(int index, String pattern)
		throws IllegalArgumentException, IndexOutOfBoundsException
	{
		int guiSize = this.gui.getSize();
		
		if (index < 0)
			throw new IndexOutOfBoundsException(
				"Index must be greater than or equal to 0 (index is " + index + ")");
		
		if (index > guiSize)
			throw new IndexOutOfBoundsException(
				"Index is too big (index is " + index + ", GUI size is " + guiSize + ").");
		
		if (pattern.length() != 9)
			throw new IllegalArgumentException("A pattern must be 9 characters long.");
		
		if (index == 0) {
			int patternIndex = this.pattern.indexOf(null);
			
			if (patternIndex == -1)
				throw new IllegalStateException("Patterns are full.");

			this.pattern.set(patternIndex, pattern);
		} else
			this.pattern.set(index - 1, pattern);
		return this;
	}
	
	/**
	 * Adds a pattern in the mask.
	 * 
	 * @param	pattern						Pattern
	 * @return	Returns itself.
	 * @throws	IllegalArgumentException	Wrong pattern (not 9 characters long)
	 */
	public Mask pattern(String pattern)
		throws IllegalArgumentException
	{
		return this.pattern(0, pattern);
	}
	
	/**
	 * Applies the mask to the linked GUI.
	 * 
	 * @return	Returns itself.
	 */
	public Mask apply()
	{
	    HashItem placeholderItem = new HashItem(Material.BARRIER)
            .setName("§cItem not found.")
            .addLore("§7§oI am a poor dev that can't do his work properly.")
            .build();

	    for (int rowIndex = 0; rowIndex < this.pattern.size(); rowIndex++) {
	        String iteratedPattern = this.pattern.get(rowIndex);
	        if (iteratedPattern == null)
	        	continue;

	        for (int columnIndex = 0; columnIndex < iteratedPattern.length(); columnIndex++) {
	            char iteratedPatternChar = iteratedPattern.charAt(columnIndex);
	            if (iteratedPatternChar == ' ')
	            	continue;

	            int guiIndex = columnIndex + (rowIndex * 9);
	            
	            this.gui.setItem(guiIndex, this.items.getOrDefault(iteratedPatternChar, placeholderItem));
	        }
	    }
	    
	    return this;
	}
	
}
