package fr.plx0wn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.plx0wn.Portals.PortalsCommands;

public class EasyPortals extends JavaPlugin {

	public static Plugin instance;

	public void onEnable() {
		instance = this;

		getCommand("portals").setExecutor(new PortalsCommands());

		ConfigFiles.createFiles();

		Bukkit.getServer().getPluginManager().registerEvents(new fr.plx0wn.Portals.Events(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new fr.plx0wn.Signs.Events(), this);
	}
}
