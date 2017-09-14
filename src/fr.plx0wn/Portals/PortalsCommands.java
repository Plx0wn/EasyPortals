package fr.plx0wn.Portals;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.plx0wn.ConfigFiles;
import fr.plx0wn.EasyPortals;
import fr.plx0wn.Utils;
import fr.plx0wn.Portals.API.Informationnal;
import fr.plx0wn.Portals.API.Portals;

public class PortalsCommands implements CommandExecutor {

	static FileConfiguration settings = ConfigFiles.settingsConf;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = ((Player) sender).getPlayer();
			if (player.hasPermission("portals.create")) {
				if (label.equalsIgnoreCase("portals")) {
					if (args.length == 0) {
						Utils.sendColoredMessage(player, "");
						Utils.sendColoredMessage(player,
								Utils.ColoredString("&a- /portals create <portal name> &e&l: create new portal"));
						Utils.sendColoredMessage(player,
								Utils.ColoredString("&a- /portals remove <portal name> &e&l: remove portal"));
						Utils.sendColoredMessage(player, Utils
								.ColoredString("&a- /portals setlocation <portal name> &e&l: set new portal location"));
						Utils.sendColoredMessage(player,
								Utils.ColoredString("&a- /portals setblock <portal name> &e&l: set new portal block"));
						Utils.sendColoredMessage(player, Utils
								.ColoredString("&a- /portals info <portal name> &e&l: show informations of portal"));
						Utils.sendColoredMessage(player,
								Utils.ColoredString("&a- /portals reload &e&l: reload configs files."));
						Utils.sendColoredMessage(player, "");
					}

					ArrayList<String> commandslist = new ArrayList<>();
					commandslist.add("create");
					commandslist.add("remove");
					commandslist.add("setlocation");
					commandslist.add("setblock");
					commandslist.add("info");
					commandslist.add("reload");
					commandslist.add("list");

					if (args.length == 1) {
						if (commandslist.contains(args[0])) {
							if (args[0].equalsIgnoreCase("create")) {
								Utils.sendColoredMessage(player,
										Utils.ColoredString("&cTry /portals create <portal name>"));
							}
							if (args[0].equalsIgnoreCase("remove")) {
								Utils.sendColoredMessage(player,
										Utils.ColoredString("&cTry /portals remove <portal name>"));
							}
							if (args[0].equalsIgnoreCase("setlocation")) {
								Utils.sendColoredMessage(player,
										Utils.ColoredString("&cTry /portals location <portal name>"));
							}
							if (args[0].equalsIgnoreCase("setblock")) {
								Utils.sendColoredMessage(player,
										Utils.ColoredString("&cTry /portals setblock <portal name>"));
							}
							if (args[0].equalsIgnoreCase("info")) {
								Utils.sendColoredMessage(player,
										Utils.ColoredString("&cTry /portals info <portal name>"));
							}
							if (args[0].equalsIgnoreCase("reload")) {
								ConfigFiles.reloadFiles(player);
							}
							if (args[0].equalsIgnoreCase("list")) {
								Portals.sendPortalsList(player);
							}
						} else {
							Utils.sendColoredMessage(player, "&cUnknow command. Type /portals");
						}
					}
					if (args.length == 2) {
						if (commandslist.contains(args[0])) {
							if (args[0].equalsIgnoreCase("create")) {
								Portals.createPortal(player, args[1]);
							}
							if (args[0].equalsIgnoreCase("remove")) {
								Portals.removePortal(player, args[1]);
							}
							if (args[0].equalsIgnoreCase("setlocation")) {
								Portals.defineLocation(player, args[1]);
							}
							if (args[0].equalsIgnoreCase("setblock")) {

								if (Informationnal.portalExist(args[1])) {
									Events.blockDefining.add(player);
									ConfigFiles.defineConf.set("tmp." + player.getName() + ".portal", args[1]);
									ConfigFiles.saveFiles();
									Utils.sendColoredMessage(player, Utils.ColoredString(
											ConfigFiles.msgConf.getString("portals.teleport-block.define")));
								} else {
									Utils.sendColoredMessage(player,
											Utils.ColoredString(ConfigFiles.msgConf.getString("commands.dont-exist")));
								}
							}
							if (args[0].equalsIgnoreCase("info")) {
								Informationnal.getPortalInfos(player, args[1]);
							}
						} else {
							Utils.sendColoredMessage(player, "&cUnknow command. Type /portals");
						}
					}

				}
			} else {
				Utils.sendColoredMessage(player, ConfigFiles.msgConf.getString("system.no-permissions"));
			}
		} else {
			sender.sendMessage(Utils.ColoredString("&cYou need to be a player!"));
		}
		return false;
	}

}
