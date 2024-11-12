package fr.hashtek.spigot.hashgui.listener;

import fr.hashtek.spigot.hashgui.handler.destroy.DestroyHandler;
import fr.hashtek.spigot.hashgui.handler.destroy.HashGuiDestroy;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HashGuiDestroyListener implements Listener
{

    private final HashGuiDestroy destroyManager;


    /**
     * Creates a new instance of HashGuiDestroyListener, with
     * a destroy manager for block destroy handling.
     *
     * @param	destroyManager Destroy manager
     */
    public HashGuiDestroyListener(HashGuiDestroy destroyManager)
    {
        this.destroyManager = destroyManager;
    }


    /**
     * Executes the destroy actions linked to the used item.
     *
     * @param   player          Player who destroyed the block
     * @param   itemUsed        Item used
     * @param   destroyedBlock  Destroyed block
     */
    private void processBlockDestroy(Player player, ItemStack itemUsed, Block destroyedBlock)
    {
        final ItemMeta meta = itemUsed.getItemMeta();
        final Component itemDisplayName = meta.displayName();
        final List<DestroyHandler> destroyHandlers = this.destroyManager.getHandlers().get(itemDisplayName);

        if (destroyHandlers == null || destroyHandlers.isEmpty()) {
            return;
        }

        destroyHandlers.forEach((DestroyHandler handler) ->
            handler.getDestroyAction().execute(player, itemUsed, destroyedBlock));
    }

    /**
     * Block destroy handling.
     */
    @EventHandler
    public void onInteract(BlockBreakEvent event)
    {
        final Player player = event.getPlayer();
        final ItemStack itemUsed = player.getInventory().getItemInMainHand();
        final Block destroyedBlock = event.getBlock();

        if (itemUsed.getType() == Material.AIR) {
            return;
        }

        this.processBlockDestroy(player, itemUsed, destroyedBlock);
    }

}
