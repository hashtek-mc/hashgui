package fr.hashtek.spigot.hashitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HashItem {
	
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	/**
	 * Item's click handlers
	 */
	private List<ClickHandler> clickHandlers;
	
	
	/**
	 * @param	type	Material type
	 */
	public HashItem(Material type)
	{
		this(type, 1, null);
	}
	
	/**
	 * @param	type	Material type
	 * @param	amount	Amount of items
	 */
	public HashItem(Material type, int amount)
	{
		this(type, amount, null);
	}
	
	/**
	 * @param	type	Material type
	 * @param	data	Item's data
	 */
	public HashItem(Material type, Byte data)
	{
		this(type, 1, data);
	}
	
	/**
	 * @param	type	Material type
	 * @param	amount	Amount of items
	 * @param	data	Item's data
	 */
	public HashItem(Material type, int amount, Byte data)
	{
		if (data == null)
			this.itemStack = new ItemStack(type, amount);
		else
			this.itemStack = new ItemStack(type, amount, data);
		
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	/**
	 * Creates a HashItem from an existing ItemStack.
	 * 
	 * @param	item	ItemStack
	 */
	public HashItem(ItemStack item)
	{
		this.itemStack = item;
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	
	/**
	 * Builds the ItemStack.
	 */
	public HashItem build() throws IllegalArgumentException
	{
		this.itemStack.setItemMeta(this.itemMeta);
		
		return this;
	}
	
	/**
	 * Returns the built ItemStack.
	 * 
	 * @return	Item's ItemStack
	 */
	public ItemStack getItemStack()
	{
		return this.itemStack;
	}
	
	/**
	 * Sets item's type.
	 * 
	 * @param	type	Item type.
	 * @return	Returns itself.
	 */
	public HashItem setType(Material type)
	{
		this.itemStack.setType(type);
		return this;
	}
	
	/**
	 * Sets item's amount.
	 * 
	 * @param	amount	Amount of items
	 * @return	Returns itself.
	 */
	public HashItem setAmount(int amount)
	{
		this.itemStack.setAmount(amount);
		return this;
	}
	
	/**
	 * Sets item's durability.
	 * 
	 * @param	durability	Item durability.
	 * @return	Returns itself.
	 */
	public HashItem setDurability(short durability)
	{
		this.itemStack.setDurability(durability);
		return this;
	}
	
	/**
	 * Sets item's data.
	 * 
	 * @param	data	Item data.
	 * @return	Returns itself.
	 * @deprecated
	 */
	public HashItem setData(Byte data)
	{
		// TODO: Finish this.
		return this;
	}
	
	/**
	 * Sets item's title.
	 * 
	 * @param	title	Item title.
	 * @return	Returns itself.
	 */
	public HashItem setTitle(String title)
	{
		this.itemMeta.setDisplayName(ChatColor.RESET + title);
		return this;
	}
	
	/**
	 * Sets item's lore.
	 * 
	 * @param	lore	Item lore.
	 * @return	Returns itself.
	 */
	public HashItem setLore(List<String> lore)
	{
		this.itemMeta.setLore(lore);
		return this;
	}
	
	/**
	 * Adds a line to item's lore.
	 * 
	 * @param	line	Lore line.
	 * @return	Returns itself.
	 */
	public HashItem addLore(String line)
	{
		List<String> lore;
		
		if (this.itemMeta.hasLore())
			lore = this.itemMeta.getLore();
		else
			lore = new ArrayList<String>();
		
		lore.add(line);
		
		this.itemMeta.setLore(lore);
		return this;
	}
	
	/**
	 * Sets item's flags.
	 * 
	 * @param	flags	Item flags.
	 * @return	Returns itself.
	 */
	public HashItem setFlags(List<ItemFlag> flags)
	{
		this.itemMeta.getItemFlags().clear();
		
		flags.forEach(flag -> this.itemMeta.addItemFlags(flag));
		return this;
	}
	
	/**
	 * Adds a flag to item's flags.
	 * 
	 * @param	flag	Item flag.
	 * @return	Returns itself.
	 */
	public HashItem addFlag(ItemFlag flag)
	{
		this.itemMeta.addItemFlags(flag);
		return this;
	}
	
	/**
	 * Makes item unbreakable.
	 * 
	 * @param	unbreakable	Unbreakable
	 * @return	Returns itself.
	 */
	public HashItem setUnbreakable(boolean unbreakable)
	{
		this.itemMeta.spigot().setUnbreakable(unbreakable);
		return this;
	}
	
	/**
	 * Returns if item is unbreakable
	 * 
	 * @return	Is item unbreakable ?
	 */
	public boolean isUnbreakable()
	{
		return this.itemMeta.spigot().isUnbreakable();
	}
	
	/*
	 * TODO: Enchants
	 */

	/**
	 * Adds a click handler to the item.
	 * If a handler with the same ClickType is already registered,
	 * both will run on click. 
	 * 
	 * @param	clickHandler	Click handler.
	 * @return	Returns itself.
	 */
	public HashItem addClickHandler(ClickHandler clickHandler)
	{
		if (this.clickHandlers == null)
			this.clickHandlers = new ArrayList<ClickHandler>();
					
		this.clickHandlers.add(clickHandler);
		
		return this;
	}
	
	/**
	 * Returns item's click handlers
	 * 
	 * @return	Item's click handlers.
	 */
	public List<ClickHandler> getClickHandlers()
	{
		return this.clickHandlers;
	}
}
