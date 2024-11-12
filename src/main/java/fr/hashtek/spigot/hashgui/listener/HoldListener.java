package fr.hashtek.spigot.hashgui.listener;

import fr.hashtek.spigot.hashgui.handler.hold.HoldManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class HoldListener implements Listener
{

    private final HoldManager holdManager;


    /**
     * Creates a new instance of HoldListener, with
     * a hold manager for hold handling.
     *
     * @param   holdManager Hold manager
     */
    public HoldListener(HoldManager holdManager)
    {
        this.holdManager = holdManager;
    }


    /**
     * Hold handling.
     */
    @EventHandler
    public void onInteract(PlayerItemHeldEvent event)
    {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItem(event.getNewSlot());
        final ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());

        if (item != null && item.getType() != Material.AIR) {
            this.holdManager.processHold(player, item, true);
        }

        if (previousItem != null && previousItem.getType() != Material.AIR) {
            this.holdManager.processHold(player, previousItem, false);
        }
    }

    /**
     * If player drops the held item, then execute Not Hold action.
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType() == Material.AIR) {
            return;
        }

        this.holdManager.processHold(player, item, false);
    }

}
