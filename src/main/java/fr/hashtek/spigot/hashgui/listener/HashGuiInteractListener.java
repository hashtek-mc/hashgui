package fr.hashtek.spigot.hashgui.listener;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.GameMode;
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
	 * Creates a new instance of HashGuiInteractListener, with
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
	private boolean processInteraction(Player player, Action interactType, ItemStack item)
	{
		final ItemMeta meta = item.getItemMeta();
		final String itemDisplayName = meta.getDisplayName();
		final int slot = player.getInventory().getHeldItemSlot();
		final ArrayList<InteractHandler> interactHandlers = this.interactManager.getInteractHandlers().get(itemDisplayName);

		if (interactHandlers == null || interactHandlers.isEmpty())
			return false;

		interactHandlers.stream()
			.filter((InteractHandler handler) ->
				handler.getInteractTypes().contains(interactType))
			.forEach((InteractHandler handler) ->
				handler.getInteractAction().execute(player, item, slot));

		return true;
	}

	/**
	 * Interact handling
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if (event.getItem() == null)
			return;
		
		final Player player = event.getPlayer();
		final Action interactType = event.getAction();
		final ItemStack item = event.getItem();

		event.setCancelled(this.processInteraction(player, interactType, item));
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
		final Player player = event.getPlayer();
		final ItemStack item = event.getPlayer().getItemInHand();

		if (player.getGameMode() != GameMode.ADVENTURE)
			return;

		if (item.getType() == Material.AIR ||
			player.getTargetBlock((Set<Material>) null, 5).getType() == Material.AIR)
			return;

		this.processInteraction(player, Action.LEFT_CLICK_BLOCK, item);
	}

}
