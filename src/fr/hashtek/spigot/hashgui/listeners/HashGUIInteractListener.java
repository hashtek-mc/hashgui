package fr.hashtek.spigot.hashgui.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.handlers.interact.HashGUIInteraction;
import fr.hashtek.spigot.hashgui.handlers.interact.InteractHandler;

public class HashGUIInteractListener implements Listener {
	
	private final HashGUIInteraction interactManager;
	
	
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
	 * Executes the interaction actions linked to the interacted item.
	 *
	 * @param	player			Player
	 * @param	interactType	Interaction type
	 * @param	item			Item
	 */
	private void processInteraction(Player player, Action interactType, ItemStack item)
	{
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

		this.processInteraction(player, interactType, item);
	}

	/**
	 * Also interact handling.
	 * This event is used because in adventure mode,
	 * the client doesn't send a packet to the server
	 * when tapping a block.
	 */
	@EventHandler
	public void onBlockHit(PlayerAnimationEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getItemInHand();

		this.processInteraction(player, Action.LEFT_CLICK_BLOCK, item);
	}

}
