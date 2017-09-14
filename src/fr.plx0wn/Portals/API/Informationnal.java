package fr.plx0wn.Portals.API;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.plx0wn.ConfigFiles;
import fr.plx0wn.EasyPortals;
import fr.plx0wn.Utils;

public class Informationnal {

	static FileConfiguration settings = ConfigFiles.settingsConf;
	static FileConfiguration portalsConf = ConfigFiles.portalsConf;
	static FileConfiguration msgConf = ConfigFiles.msgConf;

	public static boolean portalExist(String portal) {
		if (portalsConf.contains("portals." + portal)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean isPortalBlockType(Block block) {
		if (settings.get("portals.portal-block") instanceof String) {
			if (block.getType().equals(Material.getMaterial(settings.getString("portals.portal-block")))) {
				return true;
			} else {
				return false;
			}
		} else {
			if (block.getType().equals(Material.getMaterial(settings.getInt("portals.portal-block")))) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static String getPortalByBlock(Block block) {
		String str = null;
		for (String portal : portalsConf.getConfigurationSection("portals").getKeys(false)) {
			if (portalsConf.getString("portals." + portal + ".block.world")
					.equalsIgnoreCase(block.getWorld().getName())) {
				if (portalsConf.getInt("portals." + portal + ".block.x") == (block.getLocation().getBlockX())) {
					if (portalsConf.getInt("portals." + portal + ".block.y") == (block.getLocation().getBlockY())) {
						if (portalsConf.getInt("portals." + portal + ".block.z") == (block.getLocation().getBlockZ())) {
							str = portal;
						}
					}
				}
			}
		}
		return str;
	}

	public static Location getLocation(String portal) {
		World world = Bukkit.getServer().getWorld(portalsConf.getString("portals." + portal + ".location.world"));
		double x = portalsConf.getDouble("portals." + portal + ".location.x");
		double y = portalsConf.getDouble("portals." + portal + ".location.y");
		double z = portalsConf.getDouble("portals." + portal + ".location.z");
		float pitch = portalsConf.getInt("portals." + portal + ".location.pitch");
		float yaw = portalsConf.getInt("portals." + portal + ".location.yaw");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static ArrayList<String> getPortalsList() {
		ArrayList<String> list = new ArrayList<>();
		if (portalsConf.contains("portals")) {
			for (String portal : portalsConf.getConfigurationSection("portals").getKeys(false)) {
				list.add(portal);
			}
		}
		return list;
	}

	public static void getPortalInfos(Player player, String portal) {
		if (portalExist(portal)) {
			Utils.sendColoredMessage(player, "");
			Utils.sendColoredMessage(player, "&aInformation for portal : &l" + portal);
			if (portalsConf.get("portals." + portal + ".location.x") instanceof String) {
				Utils.sendColoredMessage(player, "&a- Teleport Location: &enot defined");
			} else {
				String world = portalsConf.getString("portals." + portal + ".location.world");
				double x = portalsConf.getInt("portals." + portal + ".location.x");
				double y = portalsConf.getInt("portals." + portal + ".location.y");
				double z = portalsConf.getInt("portals." + portal + ".location.z");
				Utils.sendColoredMessage(player, "&a- Teleport Location: world: &e" + world + "&a, x: &e" + x
						+ "&a, y: &e" + y + "&a, z: &e" + z);
			}
			if (portalsConf.get("portals." + portal + ".block.x") instanceof String) {
				Utils.sendColoredMessage(player, "&a- Teleport Block: &enot defined");
			} else {
				String world = portalsConf.getString("portals." + portal + ".block.world");
				double x = portalsConf.getInt("portals." + portal + ".block.x");
				double y = portalsConf.getInt("portals." + portal + ".block.y");
				double z = portalsConf.getInt("portals." + portal + ".block.z");
				Utils.sendColoredMessage(player,
						"&a- Teleport Block: world: &e" + world + "&a, x: &e" + x + "&a, y: &e" + y + "&a, z: &e" + z);
			}
			Utils.sendColoredMessage(player, "");
		}
	}

}
