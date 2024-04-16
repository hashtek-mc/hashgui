package fr.hashtek.spigot.hashgui.page;

import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HashPage
{

    private final PaginatedHashGui parent;
    private final Map<Integer, HashItem> items;


    /**
     * Creates a new instance of HashPage.
     */
    public HashPage(PaginatedHashGui parent)
    {
        this.parent = parent;
        this.items = new HashMap<Integer, HashItem>();
    }


    private boolean checkItem(int slot)
    {
        final ItemStack i = this.parent.getInventory().getItem(slot);

        return i == null || i.getType() == Material.AIR;
    }

    public void setItem(int slot, HashItem item)
        throws IllegalArgumentException
    {
        if (!checkItem(slot))
            throw new IllegalArgumentException("Item can't be placed at the slot " + slot + ".");

        this.items.put(slot, item);
    }

    public void removeItem(int slot)
        throws IllegalArgumentException
    {
        if (!checkItem(slot))
            throw new IllegalArgumentException("Item can't be removed from the slot " + slot + ".");

        this.items.remove(slot);
    }

    public void clearItems()
    {
        this.items.clear();
    }

    public HashItem getItem(int slot)
    {
        return this.items.get(slot);
    }

    public Map<Integer, HashItem> getItems()
    {
        return this.items;
    }

}
