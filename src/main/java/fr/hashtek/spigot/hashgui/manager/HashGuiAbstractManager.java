package fr.hashtek.spigot.hashgui.manager;

import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HashGuiAbstractManager
    <T>
{

    private final Map<Component, List<T>> handlers;


    public HashGuiAbstractManager()
    {
        this.handlers = new HashMap<Component, List<T>>();
    }


    /**
     * Adds a handler to the handlers map, with a title assigned.
     *
     * @param   title   Title of the item assigned to the handler
     * @param   handler Handler to add
     */
    private void addItemHandlers(Component title, T handler)
    {
        this.handlers.computeIfAbsent(title, k -> new ArrayList<T>());

        for (T h : this.handlers.get(title)) {
            if (handler.equals(h)) {
                return;
            }
        }
        this.handlers.get(title).add(handler);
    }

    /**
     * Adds every handler of type T of a given item.
     *
     * @param   item        Item whose handlers will be added
     * @param   handlers    Item handlers
     * @return  Itself
     */
    protected HashGuiAbstractManager<T> addItemHandlers(HashItem item, List<T> handlers)
    {
        if (handlers == null || handlers.isEmpty()) {
            return this;
        }

        for (T handler : handlers) {
            this.addItemHandlers(item.getItemStack().getItemMeta().displayName(), handler);
        }
        return this;
    }

    /**
     * This must call {@link #addItemHandlers(HashItem, List)} with
     * the appropriate handler list.
     *
     * @param   item    Item whose handlers will be added
     * @return  Itself
     * @see     {@link fr.hashtek.spigot.hashgui.handler.click.HashGuiClick#addItemHandlers(HashItem)}
     */
    public abstract HashGuiAbstractManager<T> addItemHandlers(HashItem item);


    public Map<Component, List<T>> getHandlers()
    {
        return this.handlers;
    }

}
