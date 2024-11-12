package fr.hashtek.spigot.hashgui.listener;

import fr.hashtek.spigot.hashgui.HashGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseListener
    implements Listener
{

    /**
     * {@link HashGui#onClose(Player, HashGui)} handling.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        if (!(event.getPlayer() instanceof Player player) ||
            !(event.getInventory().getHolder() instanceof HashGui gui)) {
            return;
        }

        gui.onClose(player, gui);
    }

}
