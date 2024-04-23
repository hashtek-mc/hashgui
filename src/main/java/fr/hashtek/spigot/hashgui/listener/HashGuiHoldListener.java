package fr.hashtek.spigot.hashgui.listener;

import fr.hashtek.spigot.hashgui.handler.hold.HashGuiHold;
import fr.hashtek.spigot.hashgui.handler.hold.HoldHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class HashGuiHoldListener implements Listener
{

    private final HashGuiHold holdManager;


    /**
     * Creates a new instance of HashGuiHoldListener, with
     * an interact manager for hold handling.
     *
     * @param	holdManager Hold manager
     */
    public HashGuiHoldListener(HashGuiHold holdManager)
    {
        this.holdManager = holdManager;
    }


    /**
     * Executes the interaction actions linked to the held item.
     *
     * @param	player			Player
     * @param	item			Item
     */
    private void processHold(Player player, ItemStack item, boolean activeItem)
    {
        final ItemMeta meta = item.getItemMeta();
        final String itemDisplayName = meta.getDisplayName();
        final int slot = player.getInventory().getHeldItemSlot();
        final ArrayList<HoldHandler> holdHandlers = this.holdManager.getHoldHandlers().get(itemDisplayName);

        if (holdHandlers == null || holdHandlers.isEmpty())
            return;

        if (activeItem)
            holdHandlers.forEach((HoldHandler handler) ->
                handler.getHoldAction().execute(player, item, slot));
        else
            holdHandlers.forEach((HoldHandler handler) ->
                handler.getNotHoldAction().execute(player, item, slot));
    }

    /**
     * Hold handling
     */
    @EventHandler
    public void onInteract(PlayerItemHeldEvent event)
    {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItem(event.getNewSlot());
        final ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());

        if (item != null && item.getType() != Material.AIR)
            this.processHold(player, item, true);
        if (previousItem != null && previousItem.getType() != Material.AIR)
            this.processHold(player, previousItem, false);
    }

}
