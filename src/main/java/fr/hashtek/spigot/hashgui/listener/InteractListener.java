package fr.hashtek.spigot.hashgui.listener;

import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.handler.interact.InteractionManager;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;

public class InteractListener
	implements Listener
{
	
	private final InteractionManager interactManager;
	
	
	/**
	 * Creates a new instance of InteractListener, with
	 * an interact manager for interact handling.
	 * 
	 * @param	interactManager	Interact manager
	 */
	public InteractListener(InteractionManager interactManager)
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
	private boolean processInteraction(
		Player player,
		Action interactType,
		ItemStack item
	)
	{
		final ItemMeta meta = item.getItemMeta();
		final Component itemDisplayName = meta.displayName();
		final int slot = player.getInventory().getHeldItemSlot();
		final List<InteractHandler> interactHandlers = this.interactManager.getHandlers().get(itemDisplayName);

		if (interactHandlers == null || interactHandlers.isEmpty()) {
			return false;
		}

		interactHandlers.stream()
			.filter((InteractHandler handler) ->
				handler.getInteractTypes().contains(interactType))
			.forEach((InteractHandler handler) ->
				handler.getInteractAction().execute(player, item, slot));

		return true;
	}

	/**
	 * Interact handling.
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if (event.getItem() == null) {
			return;
		}

		final Player player = event.getPlayer();
		final Action interactType = event.getAction();
		final ItemStack item = event.getItem();

		event.setCancelled(this.processInteraction(player, interactType, item));
	}

}
