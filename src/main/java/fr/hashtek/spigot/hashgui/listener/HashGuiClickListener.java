package fr.hashtek.spigot.hashgui.listener;

import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.click.HashGuiClick;

public class HashGuiClickListener implements Listener
{
	
	private final HashGuiClick clickManager;
	
	
	/**
	 * Creates a new instance of HashGuiClickListener, with
	 * a click manager for click handling.
	 * 
	 * @param	clickManager	Click manager
	 */
	public HashGuiClickListener(HashGuiClick clickManager)
	{
		this.clickManager = clickManager;
	}


	/**
	 * Executes the click actions linked to the clicked item.
	 *
	 * @param	player		Player
	 * @param	clickType	Click type
	 * @param	gui			GUI
	 * @param	item		Item
	 * @param	slot		Slot
	 */
	private boolean processClick(
		Player player,
		ClickType clickType,
		HashGui gui,
		ItemStack item,
		int slot
	)
	{
		final ItemMeta meta = item.getItemMeta();
		final Component itemDisplayName = meta.displayName();
		final List<ClickHandler> handlers = this.clickManager.getHandlers().get(itemDisplayName);

		if (handlers == null) {
			return false;
		}

		handlers.stream()
			.filter((ClickHandler handler) -> {
				if (handler.isWhitelistOn() && !handler.isGuiInWhitelist(gui.getTitle())) {
					return false;
				}
				return handler.getClickTypes().contains(clickType);
			})
			.forEach((ClickHandler handler) ->
				handler.getClickAction().execute(player, gui, item, slot));

		return true;
	}

	/**
	 * Click handling.
	 */
	@EventHandler(ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (!(event.getWhoClicked() instanceof Player player) ||
			event.getClickedInventory() == null ||
			event.getCurrentItem() == null ||
			event.getCurrentItem().getType() == Material.AIR) {
			return;
		}

		final Inventory inventory = event.getClickedInventory();
		final InventoryHolder holder = inventory.getHolder();

        final ClickType clickType = event.getClick();
		final ItemStack item = event.getCurrentItem();
		final int slot = event.getSlot();

		final HashGui gui = holder instanceof HashGui
			? (HashGui) holder
			: new HashGui(inventory);

		if (item == null || item.getType() == Material.AIR) {
			return;
		}

		final boolean cancelEvent = this.processClick(player, clickType, gui, item, slot);

		event.setCancelled(cancelEvent);
	}

	/**
	 * Called for preventing items from moving from a HashGui to another
	 * inventory (e.g. player's inventory) if {@link HashGui#areItemsLockedIn()} is true.
	 * @apiNote Don't prevent the player from moving an item to his inventory by using his mouse.
	 *          This is a Minecraft issue, so it is pretty much unfixable.
	 */
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onItemDrag(InventoryClickEvent event)
	{
		final InventoryView view = event.getView();

		if (!(view.getTopInventory().getHolder() instanceof HashGui gui) ||
			view.getCursor() == null) {
			return;
		}

		if (!gui.areItemsLockedIn()) {
			return;
		}

		if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
			event.getAction() == InventoryAction.HOTBAR_SWAP ||
			event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
			event.setCancelled(true);
		}

	}

}
