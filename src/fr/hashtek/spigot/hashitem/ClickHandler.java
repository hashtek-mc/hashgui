package fr.hashtek.spigot.hashitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.ClickType;

public class ClickHandler {
	
	private List<ClickType> clickTypes;
	private ClickAction clickAction;

	
	public ClickHandler() {}
	
	
	/**
	 * Adds a click type to the handler.
	 * 
	 * @param	clickType	Click type
	 * @return	Returns itself.
	 */
	public ClickHandler addClickType(ClickType clickType)
	{
		if (this.clickTypes == null)
			this.clickTypes = new ArrayList<ClickType>();
		
		if (!this.clickTypes.contains(clickType))
			this.clickTypes.add(clickType);
		
		return this;
	}
	
	/**
	 * Adds multiple click types to the handler.
	 * 
	 * @param	clickTypes	Click types
	 * @return	Returns itself.
	 */
	public ClickHandler addClickTypes(ClickType... clickTypes)
	{
		for (ClickType clickType: clickTypes)
			this.addClickType(clickType);
		return this;
	}
	
	/**
	 * Sets handler's click action. (on click -> ...)
	 * 
	 * @param	clickAction	Click action
	 * @return	Returns itself.
	 */
	public ClickHandler setClickAction(ClickAction clickAction)
	{
		this.clickAction = clickAction;
		return this;
	}
	
	
	public List<ClickType> getClickTypes()
	{
		return this.clickTypes;
	}
	
	public ClickAction getClickAction()
	{
		return this.clickAction;
	}
}
