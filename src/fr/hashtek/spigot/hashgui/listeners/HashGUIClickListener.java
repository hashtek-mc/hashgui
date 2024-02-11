package fr.hashtek.spigot.hashgui.listeners;

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

import fr.hashtek.spigot.hashgui.HashGUI;
import fr.hashtek.spigot.hashgui.handlers.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handlers.click.HashGUIClick;

public class HashGUIClickListener implements Listener {
	
	private final HashGUIClick clickManager;
	
	
	/**
	 * Creates a new instance of HashGUIClickListener, with
	 * a click manager for click handling.
	 * 
	 * @param	clickManager	Click manager
	 */
	public HashGUIClickListener(HashGUIClick clickManager)
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
	private void processClick(Player player, ClickType clickType, HashGUI gui, ItemStack item, int slot)
	{
		ItemMeta meta = item.getItemMeta();
		String itemDisplayName = meta.getDisplayName();
		List<ClickHandler> handlers = this.clickManager.getClickHandlers().get(itemDisplayName);

		if (handlers == null)
			return;

		handlers.stream()
			.filter((ClickHandler handler) ->
				handler.getClickTypes().contains(clickType))
			.forEach((ClickHandler handler) ->
				handler.getClickAction().execute(player, gui, item, slot));
	}

	/**
	 * Click handling
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (!(event.getWhoClicked() instanceof Player) ||
			event.getClickedInventory() == null ||
			event.getCurrentItem().getType() == Material.AIR)
			return;

		Inventory inventory = event.getClickedInventory();
		InventoryHolder holder = inventory.getHolder();

		Player player = (Player) event.getWhoClicked();
		ClickType clickType = event.getClick();
		ItemStack item = event.getCurrentItem();
		int slot = event.getSlot();

		HashGUI gui = holder instanceof HashGUI
				? (HashGUI) holder
				: new HashGUI(inventory);

		event.setCancelled(true);

		this.processClick(player, clickType, gui, item, slot);
	}
	
}
