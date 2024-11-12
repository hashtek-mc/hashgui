package fr.hashtek.spigot.hashgui.handler.click;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.ClickType;

public class ClickHandler
{

	private List<Component> parentGuiWhitelist;
	private ArrayList<ClickType> clickTypes;
	private ClickAction clickAction;


	/**
	 * Adds a click type to the handler.
	 * 
	 * @param	clickType	Click type
	 * @return	Itself
	 */
	public ClickHandler addClickType(ClickType clickType)
	{
		if (this.clickTypes == null) {
			this.clickTypes = new ArrayList<ClickType>();
		}
		if (!this.clickTypes.contains(clickType)) {
			this.clickTypes.add(clickType);
		}
		return this;
	}
	
	/**
	 * Adds multiple click types to the handler.
	 * 
	 * @param	clickTypes	Click types
	 * @return	Itself
	 */
	public ClickHandler addClickTypes(ClickType... clickTypes)
	{
		for (ClickType clickType: clickTypes) {
			this.addClickType(clickType);
		}
		return this;
	}

	/**
	 * Adds every click types possible : Left, right and middle click, shifting or not.
	 *
	 * @return	Itself
	 */
	public ClickHandler addAllClickTypes()
	{
		return this.addClickTypes(
			ClickType.LEFT,
			ClickType.RIGHT,
			ClickType.SHIFT_LEFT,
			ClickType.SHIFT_RIGHT,
			ClickType.MIDDLE
		);
	}

	/**
	 * Sets handler's click action. (on click, then do...)
	 * 
	 * @param	clickAction	Click action
	 * @return	Itself
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
	 * @return  Itself
	 */
	public ClickHandler addGuiToWhitelist(Component guiTitle)
	{
		if (this.parentGuiWhitelist == null) {
			this.parentGuiWhitelist = new ArrayList<Component>();
		}
		this.parentGuiWhitelist.add(guiTitle);
		return this;
	}

	/**
	 * Adds multiple GUI titles to the handler's GUI whitelist.
	 *
	 * @param   guisTitle   GUIs' title
	 * @return  Itself
	 */
	public ClickHandler addGuisToWhitelist(List<Component> guisTitle)
	{
		if (this.parentGuiWhitelist == null) {
			this.parentGuiWhitelist = new ArrayList<Component>();
		}
		this.parentGuiWhitelist.addAll(guisTitle);
		return this;
	}

	/**
	 * @param	handler	Handler to compare
	 * @return	{@code true} if both handlers contains the same instructions. Otherwise, {@code false}.
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
	 * @return  {@code true} if given title is in the GUI whitelist. Otherwise, {@code false}.
	 */
	public boolean isGuiInWhitelist(Component guiTitle)
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
