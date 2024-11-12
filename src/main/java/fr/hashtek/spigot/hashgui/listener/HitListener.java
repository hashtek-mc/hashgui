package fr.hashtek.spigot.hashgui.listener;

import fr.hashtek.spigot.hashgui.handler.hit.HitManager;
import fr.hashtek.spigot.hashgui.handler.hit.HitHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HitListener implements Listener
{

    private final HitManager hitManager;


    /**
     * Creates a new instance of HashGuiHitListener, with
     * a hit manager for hit handling.
     *
     * @param	hitManager  Hit manager
     */
    public HitListener(HitManager hitManager)
    {
        this.hitManager = hitManager;
    }


    /**
     * Executes the kill actions linked to the used item.
     *
     * @param	attacker	Player who hit
     * @param	victim		Player who got hit
     * @param	item        Used item
     * @param   isKill      Is the hit a kill
     * @param   hitManager  Hit manager
     */
    public static void processHit(
        Player attacker,
        Player victim,
        ItemStack item,
        boolean isKill,
        HitManager hitManager
    )
    {
        if (item == null ||
            item.getType() == Material.AIR) {
            return;
        }

        final ItemMeta meta = item.getItemMeta();
        final Component itemDisplayName = meta.displayName();
        final List<HitHandler> hitHandlers = hitManager.getHandlers().get(itemDisplayName);

        if (hitHandlers == null || hitHandlers.isEmpty()) {
            return;
        }

        hitHandlers.stream()
            .filter((HitHandler hitHandler) ->
                isKill == hitHandler.isOnlyKill())
            .forEach((HitHandler handler) ->
                handler.getHitAction().execute(attacker, victim, item));
    }

    /**
     * Hit handling.
     */
    @EventHandler
    public void onInteract(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player attacker &&
            event.getEntity() instanceof Player victim)) {
            return;
        }

        final ItemStack itemUsed = attacker.getInventory().getItemInMainHand();

        processHit(
            attacker,
            victim,
            itemUsed,
            victim.getHealth() - event.getFinalDamage() <= 0,
            this.hitManager
        );
    }

}
