package fr.hashtek.spigot.hashitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;

public class HashItem
{
	
	private final ItemStack itemStack;
	private ItemMeta itemMeta;

	private List<ClickHandler> clickHandlers;
	private List<InteractHandler> interactHandlers;
	
	
	/**
	 * Creates a new HashItem.
	 * 
	 * @param	type	Material type
	 */
	public HashItem(Material type)
	{
		this(type, 1, null);
	}
	
	/**
	 * Creates a new HashItem.
	 * 
	 * @param	type	Material type
	 * @param	amount	Amount of items
	 */
	public HashItem(Material type, int amount)
	{
		this(type, amount, null);
	}
	
	/**
	 * Creates a new HashItem.
	 * 
	 * @param	type	Material type
	 * @param	data	Item's data
	 */
	public HashItem(Material type, Byte data)
	{
		this(type, 1, data);
	}
	
	/**
	 * Creates a new HashItem.
	 * 
	 * @param	type	Material type
	 * @param	amount	Amount of items
	 * @param	data	Item's data
	 */
	public HashItem(Material type, int amount, Byte data)
	{
		this.itemStack = new ItemStack(type, amount, data == null ? (byte) 0 : data);
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


	public static HashItem separator(Byte glassColor, HashGuiManager guiManager)
	{
		return new HashItem(Material.STAINED_GLASS_PANE, 1, glassColor)
			.setName("")
			.setUntakable(true)
			.build(guiManager);
	}

	/**
	 * Builds the item and registers its handlers.
	 * 
	 * @return	Returns itself.
	 */
	public HashItem build(HashGuiManager guiManager) throws IllegalArgumentException
	{
		this.itemStack.setItemMeta(this.itemMeta);
		
		guiManager.getClickManager().addClickHandler(this);
		guiManager.getInteractionManager().addInteractHandler(this);

		return this;
	}
	
	/**
	 * Builds the item, without registering its handlers.
	 * 
	 * @return	Returns itself.
	 */
	public HashItem build() throws IllegalArgumentException
	{
		this.itemStack.setItemMeta(this.itemMeta);
		return this;
	}

	/**
	 * Overrides the set ItemMeta.
	 * Mainly used in {@link HashSkull}.
	 *
	 * @param	itemMeta	Item meta
	 * @return	Returns itself.
	 * @apiNote	Must not be used outside of this library.
	 */
	public HashItem setItemMeta(ItemMeta itemMeta)
	{
		this.itemMeta = itemMeta;
		return this;
	}

	/**
	 * @return	Item's meta
	 * @apiNote	Must not be used outside of this library.
	 */
	public ItemMeta getItemMeta()
	{
		return this.itemMeta;
	}
	
	/**
	 * @return	Built item's ItemStack
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
	 * TODO: Finish this function ASAP.
	 */
	public HashItem setData(Byte data)
	{
		return this;
	}
	
	/**
	 * Sets item's name.
	 * 
	 * @param	name	Item name.
	 * @return	Returns itself.
	 */
	public HashItem setName(String name)
	{
		this.itemMeta.setDisplayName(ChatColor.RESET + name);
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
		
		flags.forEach(this.itemMeta::addItemFlags);
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
	 * @return	Is item unbreakable ?
	 */
	public boolean isUnbreakable()
	{
		return this.itemMeta.spigot().isUnbreakable();
	}
	
	/**
	 * Adds an enchantment to the item.
	 * 
	 * @param	enchantment	Enchantment type
	 * @param	level		Enchantment level
	 * @return	Returns itself.
	 */
	public HashItem addEnchant(Enchantment enchantment, int level)
	{
		this.itemMeta.addEnchant(enchantment, level, true);
		return this;
	}
	
	/**
	 * Removes an enchantment from the item.
	 * 
	 * @param	enchantment	Enchantment type
	 * @return	Returns itself.
	 */
	public HashItem removeEnchant(Enchantment enchantment)
	{
		this.itemMeta.removeEnchant(enchantment);
		return this;
	}

	/**
	 * Makes the item untakable, or not.
	 *
	 * @param 	untakable	Untakable
	 * @return	Returns itself.
	 */
	public HashItem setUntakable(boolean untakable)
	{
		if (!untakable) {
			this.clickHandlers.clear();
			return this;
		}

		this.addClickHandler(
			new ClickHandler()
				.addAllClickTypes()
				.setClickAction((player, gui, item, slot) -> {})
		);

		return this;
	}

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
	 * Adds an interact handler to the item.
	 * If a handler with the same Action is already registered,
	 * both will run on interact.
	 * 
	 * @param	interactHandler	Interact handler.
	 * @return	Returns itself.
	 */
	public HashItem addInteractHandler(InteractHandler interactHandler)
	{
		if (this.interactHandlers == null)
			this.interactHandlers = new ArrayList<InteractHandler>();
					
		this.interactHandlers.add(interactHandler);
		return this;
	}
	
	/**
	 * @return	Item's click handlers.
	 */
	public List<ClickHandler> getClickHandlers()
	{
		return this.clickHandlers;
	}
	
	/**
	 * @return	Item's interact handlers.
	 */
	public List<InteractHandler> getInteractHandlers()
	{
		return this.interactHandlers;
	}
}
