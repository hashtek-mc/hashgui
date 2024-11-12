package fr.hashtek.spigot.hashgui.manager;

import fr.hashtek.spigot.hashgui.handler.destroy.DestroyManager;
import fr.hashtek.spigot.hashgui.handler.hold.HoldManager;
import fr.hashtek.spigot.hashgui.handler.hit.HitManager;
import fr.hashtek.spigot.hashgui.listener.*;
import fr.hashtek.spigot.hashitem.common.DefaultItems;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.hashtek.spigot.hashgui.handler.click.ClickManager;
import fr.hashtek.spigot.hashgui.handler.interact.InteractionManager;

public class HashGuiManager
{
	
	private final JavaPlugin plugin;
	private final PluginManager pluginManager;
	
	private ClickManager clickManager;
	private InteractionManager interactionManager;
	private HoldManager holdManager;
	private HitManager hitManager;
	private DestroyManager destroyManager;

	
	/**
	 * Creates a new instance of HashGuiManager.
	 * <p>
	 * HashGuiManager aims to handle different interactions
	 * with the items (click, interact, hold, hit...).
	 * </p>
	 * 
	 * @param	plugin			Main instance
	 * @param	pluginManager	Plugin manager
	 */
	public HashGuiManager(JavaPlugin plugin, PluginManager pluginManager)
	{
		this.plugin = plugin;
		this.pluginManager = pluginManager;
	}
	

	/**
	 * Setups the manager to handle different interactions
	 * for items.
	 * 
	 * @return Itself
	 */
	public HashGuiManager setup()
	{
		this.clickManager = new ClickManager();
		this.interactionManager = new InteractionManager();
		this.holdManager = new HoldManager();
		this.hitManager = new HitManager();
		this.destroyManager = new DestroyManager();

		this.pluginManager.registerEvents(new ClickListener(this.clickManager), this.plugin);
		this.pluginManager.registerEvents(new InteractListener(this.interactionManager), this.plugin);
		this.pluginManager.registerEvents(new HoldListener(this.holdManager), this.plugin);
		this.pluginManager.registerEvents(new HitListener(this.hitManager), this.plugin);
		this.pluginManager.registerEvents(new DestroyListener(this.destroyManager), this.plugin);

		this.pluginManager.registerEvents(new CloseListener(), this.plugin);

		for (DefaultItems item : DefaultItems.values()) {
			item.getItem().build(this);
		}

		return this;
	}

	/**
	 * Unregisters an item from the various managers.
	 *
	 * @param	itemName	Item's name
	 */
	public void unregisterItem(Component itemName)
	{
		this.clickManager.removeItemHandlers(itemName);
		this.interactionManager.removeItemHandlers(itemName);
		this.holdManager.removeItemHandlers(itemName);
		this.hitManager.removeItemHandlers(itemName);
		this.destroyManager.removeItemHandlers(itemName);
	}


	/**
	 * @return	Click manager
	 */
	public ClickManager getClickManager()
	{
		return this.clickManager;
	}
	
	/**
	 * @return	Interaction manager
	 */
	public InteractionManager getInteractionManager()
	{
		return this.interactionManager;
	}

	/**
	 * @return	Hold manager
	 */
	public HoldManager getHoldManager()
	{
		return this.holdManager;
	}

	/**
	 * @return	Hit manager
	 */
	public HitManager getHitManager()
	{
		return this.hitManager;
	}

	/**
	 * @return	Destroy manager
	 */
	public DestroyManager getDestroyManager()
	{
		return this.destroyManager;
	}

}
