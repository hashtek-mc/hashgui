package fr.hashtek.spigot.hashgui.handler.click;

import fr.hashtek.spigot.hashgui.manager.HashGuiAbstractManager;
import fr.hashtek.spigot.hashitem.HashItem;

public class HashGuiClick
	extends HashGuiAbstractManager<ClickHandler>
{

	@Override
	public HashGuiAbstractManager<ClickHandler> addItemHandlers(HashItem item)
	{
		return super.addItemHandlers(item, item.getClickHandlers());
	}

}
