package fr.hashtek.spigot.hashgui.handler.click;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGuiClick
{
	
	private final HashMap<Component, ArrayList<ClickHandler>> clickHandlers;
	
	
	/**
	 * Creates a new instance of HashGuiClick.
	 */
	public HashGuiClick()
	{
		this.clickHandlers = new HashMap<Component, ArrayList<ClickHandler>>();
	}
	
	
	/**
	 * Adds a click handler for a certain title.
	 * 
	 * @param	title	Title
	 * @param	handler	Click handler
	 * @return	Returns itself.
	 */
	private HashGuiClick addClickHandler(Component title, ClickHandler handler)
	{
        this.clickHandlers.computeIfAbsent(title, k -> new ArrayList<ClickHandler>());

		for (ClickHandler h : this.clickHandlers.get(title)) {
			if (handler.equals(h)) {
				return this;
			}
		}
		this.clickHandlers.get(title).add(handler);
		return this;
	}
	
	/**
	 * Adds every click handler from an item.
	 * 
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGuiClick addClickHandler(HashItem item)
	{
		List<ClickHandler> clickHandlers = item.getClickHandlers();
		
		if (clickHandlers == null || clickHandlers.isEmpty()) {
			return this;
		}
		
		final ItemMeta meta = item.getItemStack().getItemMeta();
		final Component itemName = meta.displayName();
		
		for (ClickHandler handler : clickHandlers) {
			this.addClickHandler(itemName, handler);
		}
		return this;
	}
	
	
	/**
	 * @return	Every registered click handler
	 */
	public HashMap<Component, ArrayList<ClickHandler>> getClickHandlers()
	{
		return this.clickHandlers;
	}

}
