package fr.hashtek.spigot.hashgui.handler.click;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.ClickType;

public class ClickHandler
{

	private List<String> parentGuiWhitelist;
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
	 * Adds every click types possible : Left, right and middle click, shifting or not.
	 *
	 * @return	Returns itself.
	 */
	public ClickHandler addAllClickTypes()
	{
		return this.addClickTypes(
			ClickType.LEFT, ClickType.RIGHT, ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT, ClickType.MIDDLE);
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
	 * Adds a GUI title to the handler's GUI whitelist.
	 *
	 * @param   guiTitle    GUI's title
	 * @return  Returns itself.
	 */
	public ClickHandler addGuiToWhitelist(String guiTitle)
	{
		if (this.parentGuiWhitelist == null)
			this.parentGuiWhitelist = new ArrayList<String>();

		this.parentGuiWhitelist.add(guiTitle);
		return this;
	}

	/**
	 * Adds multiple GUI titles to the handler's GUI whitelist.
	 *
	 * @param   guisTitle   GUIs' title
	 * @return  Returns itself.
	 */
	public ClickHandler addGuisToWhitelist(List<String> guisTitle)
	{
		if (this.parentGuiWhitelist == null)
			this.parentGuiWhitelist = new ArrayList<String>();

		this.parentGuiWhitelist.addAll(guisTitle);
		return this;
	}

	/**
	 * @param	handler	Handler to compare
	 * @return	Returns true if both handlers contains the same instructions
	 */
	public boolean equals(ClickHandler handler)
	{
		return
			this.clickAction.equals(handler.clickAction) ||
			this.clickTypes.containsAll(handler.getClickTypes());
	}
	
	
	/**
	 * @return	Assigned click types
	 */
	public ArrayList<ClickType> getClickTypes()
	{
		return this.clickTypes;
	}
	
	/**
	 * @return	Assigned action
	 */
	public ClickAction getClickAction()
	{
		return this.clickAction;
	}

	/**
	 * @param   guiTitle    GUI's title
	 * @return  True if given title is in the GUI whitelist.
	 */
	public boolean isGuiInWhitelist(String guiTitle)
	{
		return this.parentGuiWhitelist.contains(guiTitle);
	}

	/**
	 * @return  True if whitelist is on.
	 */
	public boolean isWhitelistOn()
	{
		return this.parentGuiWhitelist != null;
	}

}
