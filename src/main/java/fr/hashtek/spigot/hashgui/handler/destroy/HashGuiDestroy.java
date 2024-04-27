package fr.hashtek.spigot.hashgui.handler.destroy;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashGuiDestroy
{
	
	private final HashMap<String, ArrayList<DestroyHandler>> destroyHandlers;
	
	
	/**
	 * Creates a new instance of HashGuiDestroy.
	 */
	public HashGuiDestroy()
	{
		this.destroyHandlers = new HashMap<String, ArrayList<DestroyHandler>>();
	}


	/**
	 * Adds a destroy handler for a certain title.
	 * 
	 * @param	title	Title
	 * @param	handler	Destroy handler
	 * @return	Returns itself.
	 */
	private HashGuiDestroy addDestroyHandler(String title, DestroyHandler handler)
	{
        this.destroyHandlers.computeIfAbsent(title, k -> new ArrayList<DestroyHandler>());

		for (DestroyHandler h : this.destroyHandlers.get(title))
			if (handler.equals(h))
				return this;

		this.destroyHandlers.get(title).add(handler);
		return this;
	}

	/**
	 * Adds a destroy handler for an item.
	 * 
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGuiDestroy addDestroyHandler(HashItem item)
	{
		List<DestroyHandler> destroyHandlers = item.getDestroyHandlers();
		
		if (destroyHandlers == null || destroyHandlers.isEmpty())
			return this;
		
		final ItemMeta meta = item.getItemStack().getItemMeta();
		final String itemName = meta.getDisplayName();
		
		for (DestroyHandler handler: destroyHandlers)
			this.addDestroyHandler(itemName, handler);
		
		return this;
	}


	/**
	 * @return	Registered interact handlers
	 */
	public HashMap<String, ArrayList<DestroyHandler>> getDestroyHandlers()
	{
		return this.destroyHandlers;
	}

}
