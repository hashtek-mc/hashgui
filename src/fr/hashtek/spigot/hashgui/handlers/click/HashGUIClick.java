package fr.hashtek.spigot.hashgui.handlers.click;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGUIClick {
	
	private HashMap<String, ArrayList<ClickHandler>> clickHandlers;
	
	
	/**
	 * Creates a HashGUIClick instance.
	 */
	public HashGUIClick()
	{
		this.clickHandlers = new HashMap<String, ArrayList<ClickHandler>>();
	}
	
	
	/**
	 * Adds a click handler for a certain title.
	 * 
	 * @param	title	Title
	 * @param	handler	Click handler
	 * @return	Returns itself.
	 */
	private HashGUIClick addClickHandler(String title, ClickHandler handler)
	{
		if (this.clickHandlers.get(title) == null)
			this.clickHandlers.put(title, new ArrayList<ClickHandler>());
		
		this.clickHandlers.get(title).add(handler);
		return this;
	}
	
	/**
	 * Adds every click handler from an item.
	 * 
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGUIClick addClickHandler(HashItem item)
	{
		List<ClickHandler> clickHandlers = item.getClickHandlers();
		
		if (clickHandlers.isEmpty())
			return this;
		
		ItemMeta meta = item.getItemStack().getItemMeta();
		String itemName = meta.getDisplayName();
		
		for (ClickHandler handler: item.getClickHandlers())
			this.addClickHandler(itemName, handler);
		
		return this;
	}
	
	
	/**
	 * Returns every registered click handler.
	 * 
	 * @return	Click handlers
	 */
	public HashMap<String, ArrayList<ClickHandler>> getClickHandlers()
	{
		return this.clickHandlers;
	}

}
