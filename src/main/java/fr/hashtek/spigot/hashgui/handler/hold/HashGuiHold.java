package fr.hashtek.spigot.hashgui.handler.hold;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashGuiHold
{

    private final HashMap<String, ArrayList<HoldHandler>> holdHandlers;


    /**
     * Creates a new instance of HashGuiHold.
     */
    public HashGuiHold()
    {
        this.holdHandlers = new HashMap<String, ArrayList<HoldHandler>>();
    }


    /**
     * Adds a hold handler for a certain title.
     *
     * @param	title	Title
     * @param	handler	Hold handler
     * @return	Returns itself.
     */
    private HashGuiHold addHoldHandler(String title, HoldHandler handler)
    {
        this.holdHandlers.computeIfAbsent(title, k -> new ArrayList<HoldHandler>());

        for (HoldHandler h : this.holdHandlers.get(title))
            if (handler.equals(h))
                return this;

        this.holdHandlers.get(title).add(handler);
        return this;
    }

    /**
     * Adds every hold handler from an item.
     *
     * @param	item	Item
     * @return	Returns itself.
     */
    public HashGuiHold addHoldHandler(HashItem item)
    {
        List<HoldHandler> holdHandlers = item.getHoldHandlers();

        if (holdHandlers == null || holdHandlers.isEmpty())
            return this;

        final ItemMeta meta = item.getItemStack().getItemMeta();
        final String itemName = meta.getDisplayName();

        for (HoldHandler handler : holdHandlers)
            this.addHoldHandler(itemName, handler);

        return this;
    }

    /**
     * Executes the interaction actions linked to the held item.
     *
     * @param	player  Player
     * @param	item	Item
     */
    public void processHold(Player player, ItemStack item, boolean activeItem)
    {
        final ItemMeta meta = item.getItemMeta();
        final String itemDisplayName = meta.getDisplayName();
        final int slot = player.getInventory().getHeldItemSlot();
        final ArrayList<HoldHandler> holdHandlers = this.getHoldHandlers().get(itemDisplayName);

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
     * Refreshes Armor state. Because when directly setting an
     * armor to a player, slot modification event is not fired.
     *
     * @param   player  Player
     */
    public void refreshArmorState(Player player)
    {
        final PlayerInventory inventory = player.getInventory();

        for (ItemStack armorPiece : inventory.getArmorContents()) {
            if (armorPiece == null || armorPiece.getType() == Material.AIR || !armorPiece.hasItemMeta())
                continue;

            this.processHold(
                player,
                armorPiece,
                this.holdHandlers.containsKey(armorPiece.getItemMeta().getDisplayName())
            );
        }
    }


    /**
     * @return	Every registered hold handler
     */
    public HashMap<String, ArrayList<HoldHandler>> getHoldHandlers()
    {
        return this.holdHandlers;
    }

}
