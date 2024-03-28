package fr.hashtek.spigot.hashgui.handler.interact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGuiInteraction
{
	
	private final HashMap<String, ArrayList<InteractHandler>> interactHandlers;
	
	
	/**
	 * Creates a new instance of HashGUIInteraction.
	 */
	public HashGuiInteraction()
	{
		this.interactHandlers = new HashMap<String, ArrayList<InteractHandler>>();
	}
	
	
	/**
	 * Adds an interact handler for a certain title.
	 * 
	 * @param	title	Title
	 * @param	handler	Interaction handler
	 * @return	Returns itself.
	 */
	private HashGuiInteraction addInteractHandler(String title, InteractHandler handler)
	{
        this.interactHandlers.computeIfAbsent(title, k -> new ArrayList<InteractHandler>());
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
		List<InteractHandler> interactHandler = item.getInteractHandlers();
		
		if (interactHandler == null || interactHandler.isEmpty())
			return this;
		
		ItemMeta meta = item.getItemStack().getItemMeta();
		String itemName = meta.getDisplayName();
		
		for (InteractHandler handler: item.getInteractHandlers())
			this.addInteractHandler(itemName, handler);
		
		return this;
	}
	
	
	/**
	 * @return	Registered interact handlers
	 */
	public HashMap<String, ArrayList<InteractHandler>> getInteractHandlers()
	{
		return this.interactHandlers;
	}

}
