package fr.hashtek.spigot.hashgui.handlers.interact;

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
	 * Returns every assigned interact types of this handler.
	 * 
	 * @return	Interact types
	 */
	public ArrayList<Action> getInteractTypes()
	{
		return this.interactTypes;
	}
	
	/**
	 * Returns the action assigned to this handler.
	 * 
	 * @return	Action
	 */
	public InteractAction getInteractAction()
	{
		return this.interactAction;
	}
	
}
