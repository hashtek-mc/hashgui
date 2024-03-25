package fr.hashtek.spigot.hashgui;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.hashtek.spigot.hashgui.handler.click.HashGUIClick;
import fr.hashtek.spigot.hashgui.handler.interact.HashGUIInteraction;
import fr.hashtek.spigot.hashgui.listener.HashGUIClickListener;
import fr.hashtek.spigot.hashgui.listener.HashGUIInteractListener;

public class HashGUIManager {
	
	private final JavaPlugin plugin;
	private final PluginManager pluginManager;
	
	private HashGUIClick clickManager;
	private HashGUIInteraction interactionManager;
	
	
	/**
	 * Creates a new instance of HashGUIManager.
	 * HashGUIManager aims to handle clicks and interactions
	 * for items.
	 * 
	 * @param	plugin			Main instance
	 * @param	pluginManager	Plugin manager
	 */
	public HashGUIManager(JavaPlugin plugin, PluginManager pluginManager)
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
	public HashGUIManager setup()
	{
		this.clickManager = new HashGUIClick();
		this.interactionManager = new HashGUIInteraction();
		
		this.pluginManager.registerEvents(new HashGUIClickListener(this.clickManager), this.plugin);
		this.pluginManager.registerEvents(new HashGUIInteractListener(this.interactionManager), this.plugin);
		
		return this;
	}
	
	
	/**
	 * @return	Click manager
	 */
	public HashGUIClick getClickManager()
	{
		return this.clickManager;
	}
	
	/**
	 * @return	Interaction manager
	 */
	public HashGUIInteraction getInteractionManager()
	{
		return this.interactionManager;
	}

}
