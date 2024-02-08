package fr.hashtek.spigot.hashgui.handlers.interact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashitem.HashItem;

public class HashGUIInteraction {
	
	private HashMap<String, ArrayList<InteractHandler>> interactHandlers;
	
	
	public HashGUIInteraction()
	{
		this.interactHandlers = new HashMap<String, ArrayList<InteractHandler>>();
	}
	
	
	private HashGUIInteraction addInteractHandler(String title, InteractHandler handler)
	{
		if (this.interactHandlers.get(title) == null)
			this.interactHandlers.put(title, new ArrayList<InteractHandler>());
		
		this.interactHandlers.get(title).add(handler);
		return this;
	}
	
	public HashGUIInteraction addInteractHandler(HashItem item)
	{
		List<InteractHandler> interactHandler = item.getInteractHandlers();
		
		if (interactHandler.isEmpty())
			return this;
		
		ItemMeta meta = item.getItemStack().getItemMeta();
		String itemName = meta.getDisplayName();
		
		for (InteractHandler handler: item.getInteractHandlers())
			this.addInteractHandler(itemName, handler);
		
		return this;
	}
	
	
	public HashMap<String, ArrayList<InteractHandler>> getInteractHandlers()
	{
		return this.interactHandlers;
	}

}
