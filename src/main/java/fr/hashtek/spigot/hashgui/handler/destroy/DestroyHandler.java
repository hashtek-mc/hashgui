package fr.hashtek.spigot.hashgui.handler.destroy;

public class DestroyHandler
{

	private DestroyAction destroyAction;


	/**
	 * Creates an empty instance of InteractHandler.
	 */
	public DestroyHandler() {}


	/**
	 * Sets handler's destroy action. (on block destroy, then do...)
	 * 
	 * @param	destroyAction	Interact action
	 * @return	Returns itself.
	 */
	public DestroyHandler setDestroyAction(DestroyAction destroyAction)
	{
		this.destroyAction = destroyAction;
		return this;
	}

	/**
	 * @param	handler	Handler to compare
	 * @return	Returns true if both handlers contains the same instructions
	 */
	public boolean equals(DestroyHandler handler)
	{
		return this.destroyAction.equals(handler.destroyAction);
	}


	/**
	 * @return	Assigned action
	 */
	public DestroyAction getDestroyAction()
	{
		return this.destroyAction;
	}
	
}
