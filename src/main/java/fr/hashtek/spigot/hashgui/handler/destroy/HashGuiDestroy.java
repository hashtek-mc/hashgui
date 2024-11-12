package fr.hashtek.spigot.hashgui.handler.destroy;

import fr.hashtek.spigot.hashgui.manager.HashGuiAbstractManager;
import fr.hashtek.spigot.hashitem.HashItem;

public class HashGuiDestroy
	extends HashGuiAbstractManager<DestroyHandler>
{

	@Override
	public HashGuiAbstractManager<DestroyHandler> addItemHandlers(HashItem item)
	{
		return super.addItemHandlers(item, item.getDestroyHandlers());
	}

}