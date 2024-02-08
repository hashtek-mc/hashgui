package fr.hashtek.spigot.hashgui.handlers.click;

import java.util.ArrayList;

import org.bukkit.event.inventory.ClickType;

public class ClickHandler {
	
	private ArrayList<ClickType> clickTypes;
	private ClickAction clickAction;

	
	/**
	 * Creates an empty instance of ClickHandler.
	 */
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
	 * Sets handler's click action. (on click, then do...)
	 * 
	 * @param	clickAction	Click action
	 * @return	Returns itself.
	 */
	public ClickHandler setClickAction(ClickAction clickAction)
	{
		this.clickAction = clickAction;
		return this;
	}
	
	
	/**
	 * Returns every assigned click types of this handler.
	 * 
	 * @return	Click types
	 */
	public ArrayList<ClickType> getClickTypes()
	{
		return this.clickTypes;
	}
	
	/**
	 * Returns the action assigned to this handler.
	 * 
	 * @return	Action
	 */
	public ClickAction getClickAction()
	{
		return this.clickAction;
	}
}
