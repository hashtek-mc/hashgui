package fr.hashtek.spigot.hashgui.listeners;

import java.util.List;

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
	
	private HashGUIClick clickManager;
	
	
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
	 * Click handling
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Inventory inventory = event.getClickedInventory();
		InventoryHolder holder = inventory.getHolder();
		
		if (!(event.getWhoClicked() instanceof Player))
			return;

		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		ClickType clickType = event.getClick();
		ItemStack item = event.getCurrentItem();
		ItemMeta meta = item.getItemMeta();
		String itemDisplayName = meta.getDisplayName();
		int slot = event.getSlot();
		List<ClickHandler> handlers = this.clickManager.getClickHandlers().get(itemDisplayName);
		
		if (handlers == null)
			return;

		HashGUI gui = holder instanceof HashGUI 
			? (HashGUI) holder
			: new HashGUI(inventory);
		
		handlers.stream()
	        .filter((ClickHandler handler) ->
	        	handler.getClickTypes().contains(clickType))
	        .forEach((ClickHandler handler) ->
	        	handler.getClickAction().execute(player, gui, item, slot));
	}
	
}
