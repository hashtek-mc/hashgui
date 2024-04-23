package fr.hashtek.spigot.hashgui.manager;

import fr.hashtek.spigot.hashgui.handler.hold.HashGuiHold;
import fr.hashtek.spigot.hashgui.listener.HashGuiHoldListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.hashtek.spigot.hashgui.handler.click.HashGuiClick;
import fr.hashtek.spigot.hashgui.handler.interact.HashGuiInteraction;
import fr.hashtek.spigot.hashgui.listener.HashGuiClickListener;
import fr.hashtek.spigot.hashgui.listener.HashGuiInteractListener;

public class HashGuiManager
{
	
	private final JavaPlugin plugin;
	private final PluginManager pluginManager;
	
	private HashGuiClick clickManager;
	private HashGuiInteraction interactionManager;
	private HashGuiHold holdManager;
	
	
	/**
	 * Creates a new instance of HashGUIManager.
	 * HashGUIManager aims to handle clicks and interactions
	 * for items.
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
	 * Setups the manager to handle clicks and interactions
	 * for items.
	 * 
	 * @return Returns itself.
	 */
	public HashGuiManager setup()
	{
		this.clickManager = new HashGuiClick();
		this.interactionManager = new HashGuiInteraction();
		this.holdManager = new HashGuiHold();

		this.pluginManager.registerEvents(new HashGuiClickListener(this.clickManager), this.plugin);
		this.pluginManager.registerEvents(new HashGuiInteractListener(this.interactionManager), this.plugin);
		this.pluginManager.registerEvents(new HashGuiHoldListener(this.holdManager), this.plugin);

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

}
