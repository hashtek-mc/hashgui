package fr.hashtek.spigot.hashitem;

import java.util.ArrayList;
import java.util.List;

import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.destroy.DestroyHandler;
import fr.hashtek.spigot.hashgui.handler.hold.HoldHandler;
import fr.hashtek.spigot.hashgui.handler.hit.HitHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class HashItem
{
	
	private final ItemStack itemStack;
	private ItemMeta itemMeta;

	private List<ClickHandler> clickHandlers;
	private List<InteractHandler> interactHandlers;
	private List<HoldHandler> holdHandlers;
	private List<HitHandler> hitHandlers;
	private List<DestroyHandler> destroyHandlers;

	
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
	 * Creates a HashItem from an existing HashItem.
	 *
	 * @param	item	HashItem
	 */
	public HashItem(HashItem item)
	{
		this.itemStack = item.getItemStack().clone();
		this.itemMeta = item.getItemMeta().clone();
		this.itemStack.setItemMeta(this.itemMeta);
		this.clickHandlers = item.getClickHandlers();
		this.interactHandlers = item.getInteractHandlers();
		this.holdHandlers = item.getHoldHandlers();
		this.hitHandlers = item.getHitHandlers();
		this.destroyHandlers = item.getDestroyHandlers();
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
	 * Creates a Separator, a Stained Glass Pane of a certain color
	 * that does nothing, just for decoration.
	 *
	 * @param	glass		Stained Glass Pane color
	 * @param	guiManager	Gui manager
	 * @return	Created HashItem
	 */
	public static HashItem separator(Material glass, HashGuiManager guiManager)
	{
		Material mat = glass;

		if (!glass.name().endsWith("STAINED_GLASS_PANE")) {
			mat = Material.BLACK_STAINED_GLASS_PANE;
		}

		return new HashItem(mat, 1)
			.setName(Component.text(""))
			.setUntakable(true)
			.build(guiManager);
	}

	/**
	 * Special triggers handling.
	 * <ul>
	 *     <li><code>\n</code>: Line break</li>
	 * </ul>
	 */
	private void formatLore()
	{
		if (!this.itemMeta.hasLore()) {
			return;
		}

		final List<Component> newLore = new ArrayList<Component>();

		for (Component line : this.itemMeta.lore()) {
			if (!(line instanceof TextComponent l)) {
				continue;
			}
			final String lineAsString = l.content();
			String[] splittedLine = lineAsString.split("\\n");

			for (int i = 0; i < splittedLine.length; i++) {
				if (i > 0) {
					splittedLine[i] = ChatColor.getLastColors(splittedLine[i - 1]) + splittedLine[i];
				}
				newLore.add(Component.text(splittedLine[i]));
			}
		}

		this.itemMeta.lore(newLore);
	}

	/**
	 * Builds the item and registers its handlers.
	 *
	 * @param	guiManager	GUI Manager
	 * @return	Returns itself.
	 */
	public HashItem build(HashGuiManager guiManager)
	{
		this.formatLore();
		this.itemStack.setItemMeta(this.itemMeta);
		
		guiManager.getClickManager().addClickHandler(this);
		guiManager.getInteractionManager().addInteractHandler(this);
		guiManager.getHoldManager().addHoldHandler(this);
		guiManager.getHitManager().addHitHandler(this);
		guiManager.getDestroyManager().addDestroyHandler(this);
		return this;
	}

	/**
	 * Builds the item and registers its handlers, only for
	 * a certain GUI title (mainly the parent gui of the item).
	 *
	 * @param	guiTitle	GUI title
	 * @param	guiManager	GUI Manager
	 * @return	Returns itself.
	 */
	public HashItem build(Component guiTitle, HashGuiManager guiManager)
	{
		if (this.clickHandlers != null) {
			for (ClickHandler handler : this.clickHandlers) {
				handler.addGuiToWhitelist(guiTitle);
			}
		}

		return this.build(guiManager);
	}

	/**
	 * Builds the item and registers its handlers, only for
	 * a certain GUI (mainly the parent gui of the item).
	 *
	 * @param	gui		GUI
	 * @param	guiManager	GUI Manager
	 * @return	Returns itself.
	 */
	public HashItem build(HashGui gui, HashGuiManager guiManager)
	{
		return this.build(gui.getTitle(), guiManager);
	}
	
	/**
	 * Builds the item, without registering its handlers.
	 * 
	 * @return	Returns itself.
	 */
	public HashItem build()
	{
		this.formatLore();
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
	 * @param	durability	Durability to set
	 * @return	Returns itself.
	 */
	public HashItem setDurability(int durability)
	{
		if (!(this.itemMeta instanceof Damageable)) {
			/* System.err.println is used because HashLogger can't be. */
			System.err.println(
				"HashItem#setDurability: Called with an incompatible ItemMeta (not instance of Damageable).\n" +
				"HashItem used: " + this
			);
			return this;
		}

		((Damageable) this.itemMeta).setDamage(this.itemStack.getType().getMaxDurability() - durability);
		return this;
	}

	/**
	 * Sets item's durability.
	 * If you're manipulating {@link HashItem}s, please use
	 * {@link HashItem#setDurability(int)}.
	 * <p>
	 * This code is duplicated, but, I guess I can't do better.
	 *
	 * @param	item		Item
	 * @param	durability	Durability to set
	 */
	public static void setDurability(ItemStack item, int durability)
	{
		final ItemMeta meta = item.getItemMeta();

		if (!(meta instanceof Damageable)) {
			/* System.err.println is used because HashLogger can't be. */
			System.err.println(
				"HashItem#setDurability: Called with an incompatible ItemMeta (not instance of Damageable).\n" +
				"ItemStack used: " + item
			);
			return;
		}

		((Damageable) meta).setDamage(item.getType().getMaxDurability() - durability);
		item.setItemMeta(meta);
	}
	
	/**
	 * Sets item's data.
	 * TODO: Finish this function.
	 *
	 * @param	data	Item data.
	 * @return	Returns itself.
	 * @deprecated
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
	public HashItem setName(Component name)
	{
		this.itemMeta.displayName(name);
		return this;
	}

	/**
	 * Sets item's lore.
	 * 
	 * @param	lore	Item lore.
	 * @return	Returns itself.
	 */
	public HashItem setLore(List<Component> lore)
	{
		this.itemMeta.lore(lore);
		return this;
	}
	
	/**
	 * Adds a line to item's lore.
	 * 
	 * @param	line	Lore line.
	 * @return	Returns itself.
	 * @apiNote Handles line breaks ! (<code>>br/<</code>)
	 */
	public HashItem addLore(Component line)
	{
		final List<Component> lore = this.itemMeta.hasLore()
			? this.itemMeta.lore()
			: new ArrayList<Component>();

		lore.add(line);

		this.setLore(lore);
		return this;
	}

	/**
	 * Adds some lines to item's lore.
	 *
	 * @param	content		Content to add.
	 * @return	Returns itself.
	 */
	public HashItem addLore(List<Component> content)
	{
		for (Component line : content) {
			this.addLore(line);
		}
		return this;
	}

	/**
	 * Clears item's lore.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearLore()
	{
		this.itemMeta.lore(null);
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
	 * Clears item flags.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearFlags()
	{
		for (ItemFlag flag : this.itemMeta.getItemFlags()) {
			this.getItemMeta().removeItemFlags(flag);
		}
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
		this.itemMeta.setUnbreakable(unbreakable);
		return this;
	}
	
	/**
	 * @return	Is item unbreakable ?
	 */
	public boolean isUnbreakable()
	{
		return this.itemMeta.isUnbreakable();
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
	 * Clears item enchantments.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearEnchantments()
	{
		for (Enchantment enchantment : this.itemMeta.getEnchants().keySet()) {
			this.getItemMeta().removeEnchant(enchantment);
		}
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
	 * Sets leather armor color.
	 *
	 * @param	color				Wanted color
	 * @return	Returns itself.
	 * @throws	ClassCastException	If item is not a leather armor piece
	 */
	public HashItem setLeatherArmorColor(Color color)
		throws ClassCastException
	{
		if (!this.itemStack.getType().name().startsWith("LEATHER_")) {
			throw new ClassCastException("Item must be a leather armor piece.");
		}
		((LeatherArmorMeta) this.itemMeta).setColor(color);
		return this;
	}

	/* Handlers management */

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
		if (this.clickHandlers == null) {
			this.clickHandlers = new ArrayList<ClickHandler>();
		}
		this.clickHandlers.add(clickHandler);
		return this;
	}

	/**
	 * Clears item's click handlers.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearClickHandlers()
	{
		if (this.clickHandlers != null) {
			this.clickHandlers.clear();
		}
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
		if (this.interactHandlers == null) {
			this.interactHandlers = new ArrayList<InteractHandler>();
		}
		this.interactHandlers.add(interactHandler);
		return this;
	}

	/**
	 * Clears item's interact handlers.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearInteractHandlers()
	{
		if (this.interactHandlers != null) {
			this.interactHandlers.clear();
		}
		return this;
	}

	/**
	 * Adds a hold handler to the item.
	 *
	 * @param	holdHandler	Hold handler.
	 * @return	Returns itself.
	 */
	public HashItem addHoldHandler(HoldHandler holdHandler)
	{
		if (this.holdHandlers == null) {
			this.holdHandlers = new ArrayList<HoldHandler>();
		}
		this.holdHandlers.add(holdHandler);
		return this;
	}

	/**
	 * Clears item's hold handlers.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearHoldHandlers()
	{
		if (this.holdHandlers != null) {
			this.holdHandlers.clear();
		}
		return this;
	}

	/**
	 * Adds a hit handler to the item.
	 *
	 * @param	hitHandler	Hit handler.
	 * @return	Returns itself.
	 */
	public HashItem addHitHandler(HitHandler hitHandler)
	{
		if (this.hitHandlers == null) {
			this.hitHandlers = new ArrayList<HitHandler>();
		}
		this.hitHandlers.add(hitHandler);
		return this;
	}

	/**
	 * Clears item's hit handlers.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearHitHandlers()
	{
		if (this.hitHandlers != null) {
			this.hitHandlers.clear();
		}
		return this;
	}

	/**
	 * Adds a destroy handler to the item.
	 *
	 * @param	destroyHandler	Destroy handler.
	 * @return	Returns itself.
	 */
	public HashItem addDestroyHandler(DestroyHandler destroyHandler)
	{
		if (this.destroyHandlers == null) {
			this.destroyHandlers = new ArrayList<DestroyHandler>();
		}
		this.destroyHandlers.add(destroyHandler);
		return this;
	}

	/**
	 * Clears item's destroy handlers.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearDestroyHandlers()
	{
		if (this.destroyHandlers != null) {
			this.destroyHandlers.clear();
		}
		return this;
	}

	/**
	 * Clears item's handlers.
	 *
	 * @return	Returns itself.
	 */
	public HashItem clearHandlers()
	{
		this.clearClickHandlers();
		this.clearInteractHandlers();
		this.clearHoldHandlers();
		this.clearHitHandlers();
		this.clearDestroyHandlers();
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

	/**
	 * @return	Item's hold handlers.
	 */
	public List<HoldHandler> getHoldHandlers()
	{
		return this.holdHandlers;
	}

	/**
	 * @return	Item's hit handlers.
	 */
	public List<HitHandler> getHitHandlers()
	{
		return this.hitHandlers;
	}

	/**
	 * @return	Item's destroy handlers.
	 */
	public List<DestroyHandler> getDestroyHandlers()
	{
		return this.destroyHandlers;
	}

}
