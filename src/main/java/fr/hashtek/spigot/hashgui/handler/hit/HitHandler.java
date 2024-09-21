package fr.hashtek.spigot.hashgui.handler.hit;

public class HitHandler
{

	private HitAction hitAction;
	private boolean isOnlyKill;


	/**
	 * Creates an empty instance of HitHandler.
	 */
	public HitHandler()
	{
		this.isOnlyKill = false;
	}


	/**
	 * Makes that the action is fired only when the hit results
	 * in a kill. Or not.
	 *
	 * @param	isOnlyKill	Only kill
	 * @return	Returns itself.
	 */
	public HitHandler setOnlyKill(boolean isOnlyKill)
	{
		this.isOnlyKill = isOnlyKill;
		return this;
	}

	/**
	 * Sets handler's hit action. (on hit, then do...)
	 * 
	 * @param	hitAction	Interact action
	 * @return	Returns itself.
	 */
	public HitHandler setHitAction(HitAction hitAction)
	{
		this.hitAction = hitAction;
		return this;
	}

	/**
	 * @param	handler	Handler to compare
	 * @return	Returns true if both handlers contains the same instructions
	 */
	public boolean equals(HitHandler handler)
	{
		return
			this.hitAction.equals(handler.hitAction) &&
			this.isOnlyKill == handler.isOnlyKill();
	}


	/**
	 * @return	True if the handler fired only when the hit results in a kill
	 */
	public boolean isOnlyKill()
	{
		return this.isOnlyKill;
	}

	/**
	 * @return	Assigned action
	 */
	public HitAction getHitAction()
	{
		return this.hitAction;
	}
	
}
