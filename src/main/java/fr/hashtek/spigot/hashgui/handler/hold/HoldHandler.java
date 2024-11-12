package fr.hashtek.spigot.hashgui.handler.hold;

public class HoldHandler
{

    private HoldAction holdAction;
    private HoldAction notHoldAction;


    /**
     * Sets handler's hold action. (when held, then do...)
     *
     * @param	holdAction	Hold action
     * @return	Itself
     */
    public HoldHandler setHoldAction(HoldAction holdAction)
    {
        this.holdAction = holdAction;
        return this;
    }

    /**
     * Sets handler's not hold action. (when not held, then do...)
     *
     * @param	notHoldAction   Hold action
     * @return	Itself
     */
    public HoldHandler setNotHoldAction(HoldAction notHoldAction)
    {
        this.notHoldAction = notHoldAction;
        return this;
    }

    /**
     * @param	handler Handler to compare
     * @return	{@code true} if both handlers contains the same instructions. Otherwise, {@code false}.
     */
    public boolean equals(HoldHandler handler)
    {
        return this.holdAction.equals(handler.holdAction) &&
            this.notHoldAction.equals(handler.notHoldAction);
    }


    /**
     * @return	Assigned action when holding.
     */
    public HoldAction getHoldAction()
    {
        return this.holdAction;
    }

    /**
     * @return  Assigned action when not holding.
     */
    public HoldAction getNotHoldAction()
    {
        return this.notHoldAction;
    }

}
