package fr.hashtek.spigot.hashgui.handler.interact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGuiInteraction
{
	
	private final HashMap<Component, ArrayList<InteractHandler>> interactHandlers;
	
	
	/**
	 * Creates a new instance of HashGuiInteraction.
	 */
	public HashGuiInteraction()
	{
		this.interactHandlers = new HashMap<Component, ArrayList<InteractHandler>>();
	}
	
	
	/**
	 * Adds an interact handler for a certain title.
	 * 
	 * @param	title	Title
	 * @param	handler	Interaction handler
	 * @return	Returns itself.
	 */
	private HashGuiInteraction addInteractHandler(Component title, InteractHandler handler)
	{
        this.interactHandlers.computeIfAbsent(title, k -> new ArrayList<InteractHandler>());

		for (InteractHandler h : this.interactHandlers.get(title)) {
			if (handler.equals(h)) {
				return this;
			}
		}
		this.interactHandlers.get(title).add(handler);
		return this;
	}
	
	/**
	 * Adds every interaction handler from an item.
	 * 
	 * @param	item	Item
	 * @return	Returns itself.
	 */
	public HashGuiInteraction addInteractHandler(HashItem item)
	{
		List<InteractHandler> interactHandlers = item.getInteractHandlers();
		
		if (interactHandlers == null || interactHandlers.isEmpty())
			return this;
		
		final ItemMeta meta = item.getItemStack().getItemMeta();
		final Component itemName = meta.displayName();
		
		for (InteractHandler handler: interactHandlers) {
			this.addInteractHandler(itemName, handler);
		}
		return this;
	}
	
	
	/**
	 * @return	Registered interact handlers
	 */
	public HashMap<Component, ArrayList<InteractHandler>> getInteractHandlers()
	{
		return this.interactHandlers;
	}

}
