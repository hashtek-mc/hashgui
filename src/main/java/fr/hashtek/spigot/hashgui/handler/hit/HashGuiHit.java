package fr.hashtek.spigot.hashgui.handler.hit;

import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashGuiHit
{
	
	private final HashMap<Component, ArrayList<HitHandler>> hitHandlers;
	
	
	/**
	 * Creates a new instance of HashGuiHit.
	 */
	public HashGuiHit()
	{
		this.hitHandlers = new HashMap<Component, ArrayList<HitHandler>>();
	}


	/**
	 * Adds a hit handler for a certain title.
	 * 
	 * @param	title	Title
	 * @param	handler	Hit handler
	 * @return	Returns itself.
	 */
	private HashGuiHit addHitHandler(Component title, HitHandler handler)
	{
        this.hitHandlers.computeIfAbsent(title, k -> new ArrayList<HitHandler>());

		for (HitHandler h : this.hitHandlers.get(title)) {
			if (handler.equals(h)) {
				return this;
			}
		}

		this.hitHandlers.get(title).add(handler);
		return this;
	}

	/**
	 * Adds a hit handler for an item.
	 * 
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGuiHit addHitHandler(HashItem item)
	{
		List<HitHandler> hitHandlers = item.getHitHandlers();
		
		if (hitHandlers == null || hitHandlers.isEmpty())
			return this;
		
		final ItemMeta meta = item.getItemStack().getItemMeta();
		final Component itemName = meta.displayName();
		
		for (HitHandler handler: hitHandlers) {
			this.addHitHandler(itemName, handler);
		}
		return this;
	}


	/**
	 * @return	Registered hit handlers
	 */
	public HashMap<Component, ArrayList<HitHandler>> getHitHandlers()
	{
		return this.hitHandlers;
	}

}
