package fr.hashtek.spigot.hashgui.handler.destroy;

public class DestroyHandler
{

	private DestroyAction destroyAction;


	/**
	 * Sets handlers destroy action. (on block destroy, then do...)
	 * 
	 * @param	destroyAction	Destroy action
	 * @return	Itself
	 */
	public DestroyHandler setDestroyAction(DestroyAction destroyAction)
	{
		this.destroyAction = destroyAction;
		return this;
	}

	/**
	 * @param	handler	Handler to compare
	 * @return	{@code true} if both handlers contains the same instructions. Otherwise, {@code false}.
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
