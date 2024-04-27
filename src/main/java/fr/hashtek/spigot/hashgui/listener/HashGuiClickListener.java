package fr.hashtek.spigot.hashgui.listener;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
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
	private boolean processClick(Player player, ClickType clickType, HashGui gui, ItemStack item, int slot)
	{
		final ItemMeta meta = item.getItemMeta();
		final String itemDisplayName = meta.getDisplayName();
		final List<ClickHandler> handlers = this.clickManager.getClickHandlers().get(itemDisplayName);

		if (handlers == null)
			return false;

		handlers.stream()
			.filter((ClickHandler handler) ->
				handler.getClickTypes().contains(clickType))
			.forEach((ClickHandler handler) ->
				handler.getClickAction().execute(player, gui, item, slot));

		return true;
	}

	/**
	 * Click handling.
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (!(event.getWhoClicked() instanceof Player) ||
			event.getClickedInventory() == null ||
			event.getCurrentItem().getType() == Material.AIR)
			return;

		final Inventory inventory = event.getClickedInventory();
		final InventoryHolder holder = inventory.getHolder();

		final Player player = (Player) event.getWhoClicked();
		final ClickType clickType = event.getClick();
		final ItemStack item = event.getCurrentItem();
		final int slot = event.getSlot();

		final HashGui gui = holder instanceof HashGui
			? (HashGui) holder
			: new HashGui(inventory);

		final boolean cancelEvent = this.processClick(player, clickType, gui, item, slot);

		event.setCancelled(cancelEvent);
	}
	
}
