package fr.hashtek.spigot.hashgui.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.handlers.interact.HashGUIInteraction;
import fr.hashtek.spigot.hashgui.handlers.interact.InteractHandler;

public class HashGUIInteractListener implements Listener {
	
	private HashGUIInteraction interactManager;
	
	
	/**
	 * Creates a new instance of HashGUIInteract, with
	 * an interact manager for interact handling.
	 * 
	 * @param	interactManager	Interact manager
	 */
	public HashGUIInteractListener(HashGUIInteraction interactManager)
	{
		this.interactManager = interactManager;
	}
	

	/**
	 * Interact handling
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if (event.getItem() == null)
			return;
		
		Player player = event.getPlayer();
		Action interactType = event.getAction();
		ItemStack item = event.getItem();
		ItemMeta meta = item.getItemMeta();
		String itemDisplayName = meta.getDisplayName();
		int slot = player.getInventory().getHeldItemSlot();
		ArrayList<InteractHandler> interactHandlers = this.interactManager.getInteractHandlers().get(itemDisplayName);
		
		if (interactHandlers == null || interactHandlers.isEmpty())
			return;
		
		interactHandlers.stream()
			.filter((InteractHandler handler) ->
				handler.getInteractTypes().contains(interactType))
			.forEach((InteractHandler handler) ->
				handler.getInteractAction().execute(player, item, slot));
	}

}
