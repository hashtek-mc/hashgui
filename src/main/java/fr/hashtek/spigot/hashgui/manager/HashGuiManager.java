package fr.hashtek.spigot.hashgui.manager;

import fr.hashtek.spigot.hashgui.handler.destroy.HashGuiDestroy;
import fr.hashtek.spigot.hashgui.handler.hold.HashGuiHold;
import fr.hashtek.spigot.hashgui.handler.hit.HashGuiHit;
import fr.hashtek.spigot.hashgui.listener.*;
import fr.hashtek.spigot.hashitem.common.DefaultItems;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.hashtek.spigot.hashgui.handler.click.HashGuiClick;
import fr.hashtek.spigot.hashgui.handler.interact.HashGuiInteraction;

public class HashGuiManager
{
	
	private final JavaPlugin plugin;
	private final PluginManager pluginManager;
	
	private HashGuiClick clickManager;
	private HashGuiInteraction interactionManager;
	private HashGuiHold holdManager;
	private HashGuiHit hitManager;
	private HashGuiDestroy destroyManager;

	
	/**
	 * Creates a new instance of HashGuiManager.
	 * HashGuiManager aims to handle different interactions
	 * with the items (click, interact, hold, hit...)
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
	 * @return Returns itself.
	 */
	public HashGuiManager setup()
	{
		this.clickManager = new HashGuiClick();
		this.interactionManager = new HashGuiInteraction();
		this.holdManager = new HashGuiHold();
		this.hitManager = new HashGuiHit();
		this.destroyManager = new HashGuiDestroy();

		this.pluginManager.registerEvents(new HashGuiClickListener(this.clickManager), this.plugin);
		this.pluginManager.registerEvents(new HashGuiInteractListener(this.interactionManager), this.plugin);
		this.pluginManager.registerEvents(new HashGuiHoldListener(this.holdManager), this.plugin);
		this.pluginManager.registerEvents(new HashGuiHitListener(this.hitManager), this.plugin);
		this.pluginManager.registerEvents(new HashGuiDestroyListener(this.destroyManager), this.plugin);

		for (DefaultItems item : DefaultItems.values()) {
			item.getItem().build(this);
		}
		return this;
	}
	
	
	/**
	 * @return	Click manager
	 */
	public HashGuiClick getClickManager()
	{
		return this.clickManager;
	}
	
	/**
	 * @return	Interaction manager
	 */
	public HashGuiInteraction getInteractionManager()
	{
		return this.interactionManager;
	}

	/**
	 * @return	Hold manager
	 */
	public HashGuiHold getHoldManager()
	{
		return this.holdManager;
	}

	/**
	 * @return	Hit manager
	 */
	public HashGuiHit getHitManager()
	{
		return this.hitManager;
	}

	/**
	 * @return	Destroy manager
	 */
	public HashGuiDestroy getDestroyManager()
	{
		return this.destroyManager;
	}

}
