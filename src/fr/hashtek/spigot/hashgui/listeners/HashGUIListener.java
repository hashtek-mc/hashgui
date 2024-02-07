package fr.hashtek.spigot.hashgui.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.hashtek.spigot.hashgui.HashGUI;
import fr.hashtek.spigot.hashitem.ClickHandler;

public class HashGUIListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Inventory inventory = event.getClickedInventory();
		
		if (!(inventory.getHolder() instanceof HashGUI) ||
			!(event.getWhoClicked() instanceof Player))
			return;

		event.setCancelled(true);
		
		HashGUI gui = (HashGUI) inventory.getHolder();
		Player player = (Player) event.getWhoClicked();
		ClickType clickType = event.getClick();
		ItemStack clickedItem = event.getCurrentItem();
		int clickedSlot = event.getSlot();
		List<ClickHandler> handlers = gui.getClickHandlers().get(clickedSlot);
		
		if (handlers == null)
			return;
		
		handlers.stream()
	        .filter(handler -> handler.getClickTypes().contains(clickType))
	        .forEach(handler -> handler.getClickAction().execute(player, gui, clickedItem, clickedSlot));
	}
	
}
