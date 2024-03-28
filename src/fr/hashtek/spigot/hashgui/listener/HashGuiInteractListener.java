package fr.hashtek.spigot.hashgui.listener;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.handler.interact.HashGuiInteraction;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;

public class HashGuiInteractListener implements Listener
{
	
	private final HashGuiInteraction interactManager;
	
	
	/**
	 * Creates a new instance of HashGUIInteract, with
	 * an interact manager for interact handling.
	 * 
	 * @param	interactManager	Interact manager
	 */
	public HashGuiInteractListener(HashGuiInteraction interactManager)
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
	 * TODO: Verify getTargetBlock range (maybe 5 instead of 3).
	 */
	@EventHandler
	public void onBlockHit(PlayerAnimationEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getItemInHand();

		if (item.getType() == Material.AIR ||
			player.getTargetBlock((Set<Material>) null, 5).getType() == Material.AIR)
			return;

		this.processInteraction(player, Action.LEFT_CLICK_BLOCK, item);
	}

}
