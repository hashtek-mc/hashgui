package fr.hashtek.spigot.hashgui.handler.interact;

import java.util.ArrayList;

import org.bukkit.event.block.Action;

public class InteractHandler {

	private ArrayList<Action> interactTypes;
	private InteractAction interactAction;
	
	
	public InteractHandler() {}
	
	
	/**
	 * Adds an interact type to the handler.
	 * 
	 * @param	interactType	Interact type
	 * @return	Returns itself.
	 */
	public InteractHandler addInteractType(Action interactType)
	{
		if (this.interactTypes == null)
			this.interactTypes = new ArrayList<Action>();
		
		if (!this.interactTypes.contains(interactType))
			this.interactTypes.add(interactType);
		
		return this;
	}
	
	/**
	 * Adds multiple interact types to the handler.
	 * 
	 * @param	interactTypes	Interact types
	 * @return	Returns itself.
	 */
	public InteractHandler addInteractTypes(Action... interactTypes)
	{
		for (Action interactType: interactTypes)
			this.addInteractType(interactType);
		
		return this;
	}

	/**
	 * Adds every interact types possible : Left and right click, on block & air.
	 *
	 * @return	Returns itself.
	 */
	public InteractHandler addAllInteractTypes()
	{
		return this.addInteractTypes(
			Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK, Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK);
	}
	
	/**
	 * Sets handler's interact action. (on interact, then do...)
	 * 
	 * @param	interactAction	Interact action
	 * @return	Returns itself.
	 */
	public InteractHandler setInteractAction(InteractAction interactAction)
	{
		this.interactAction = interactAction;
		return this;
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
