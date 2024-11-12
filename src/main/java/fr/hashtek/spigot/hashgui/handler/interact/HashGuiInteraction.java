package fr.hashtek.spigot.hashgui.handler.interact;

import fr.hashtek.spigot.hashgui.manager.HashGuiAbstractManager;
import fr.hashtek.spigot.hashitem.HashItem;

public class HashGuiInteraction
	extends HashGuiAbstractManager<InteractHandler>
{

	@Override
	public HashGuiAbstractManager<InteractHandler> addItemHandlers(HashItem item)
	{
		return super.addItemHandlers(item, item.getInteractHandlers());
	}

}
