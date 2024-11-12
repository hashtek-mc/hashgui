package fr.hashtek.spigot.hashgui.handler.hit;

import fr.hashtek.spigot.hashgui.manager.HashGuiAbstractManager;
import fr.hashtek.spigot.hashitem.HashItem;

public class HitManager
	extends HashGuiAbstractManager<HitHandler>
{

	@Override
	public HashGuiAbstractManager<HitHandler> addItemHandlers(HashItem item)
	{
		return super.addItemHandlers(item, item.getHitHandlers());
	}

}
