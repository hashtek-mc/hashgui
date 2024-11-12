package fr.hashtek.spigot.hashgui.handler.interact;

import java.util.ArrayList;

import org.bukkit.event.block.Action;

public class InteractHandler
{

	private ArrayList<Action> interactTypes;
	private InteractAction interactAction;


	/**
	 * Creates an empty instance of InteractHandler.
	 */
	public InteractHandler() {}
	
	
	/**
	 * Adds an interact type to the handler.
	 * 
	 * @param	interactType	Interact type
	 * @return	Itself
	 */
	public InteractHandler addInteractType(Action interactType)
	{
		if (this.interactTypes == null) {
			this.interactTypes = new ArrayList<Action>();
		}
		if (!this.interactTypes.contains(interactType)) {
			this.interactTypes.add(interactType);
		}
		return this;
	}
	
	/**
	 * Adds multiple interact types to the handler.
	 * 
	 * @param	interactTypes	Interact types
	 * @return	Itself
	 */
	public InteractHandler addInteractTypes(Action... interactTypes)
	{
		for (Action interactType: interactTypes) {
			this.addInteractType(interactType);
		}
		return this;
	}

	/**
	 * Adds every interact types possible : Left and right click, on block & air.
	 *
	 * @return	Itself.
	 */
	public InteractHandler addAllInteractTypes()
	{
		return this.addInteractTypes(
			Action.RIGHT_CLICK_AIR,
			Action.RIGHT_CLICK_BLOCK,
			Action.LEFT_CLICK_AIR,
			Action.LEFT_CLICK_BLOCK
		);
	}
	
	/**
	 * Sets handlers interact action. (on interact, then do...)
	 * 
	 * @param	interactAction	Interact action
	 * @return	Itself.
	 */
	public InteractHandler setInteractAction(InteractAction interactAction)
	{
		this.interactAction = interactAction;
		return this;
	}

	/**
	 * @param	handler	Handler to compare
	 * @return	{@code true} if both handlers contains the same instructions. Otherwise, {@code false}.
	 */
	public boolean equals(InteractHandler handler)
	{
		return
			this.interactAction.equals(handler.interactAction) ||
			this.interactTypes.containsAll(handler.getInteractTypes());
	}
	
	
	/**
	 * @return	Assigned interact types
	 */
	public ArrayList<Action> getInteractTypes()
	{
		return this.interactTypes;
	}
	
	/**
	 * @return	Assigned action
	 */
	public InteractAction getInteractAction()
	{
		return this.interactAction;
	}
	
}
