package fr.hashtek.spigot.hashgui.handler.hold;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashGuiHold
{

    private final HashMap<String, ArrayList<HoldHandler>> holdHandlers;


    /**
     * Creates a new instance of HashGUIClick.
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
        List<HoldHandler> clickHandlers = item.getHoldHandlers();

        if (clickHandlers == null || clickHandlers.isEmpty())
            return this;

        ItemMeta meta = item.getItemStack().getItemMeta();
        String itemName = meta.getDisplayName();

        for (HoldHandler handler : item.getHoldHandlers())
            this.addHoldHandler(itemName, handler);

        return this;
    }


    /**
     * @return	Every registered hold handler
     */
    public HashMap<String, ArrayList<HoldHandler>> getHoldHandlers()
    {
        return this.holdHandlers;
    }

}
