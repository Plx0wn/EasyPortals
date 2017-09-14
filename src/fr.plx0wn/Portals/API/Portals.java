package fr.plx0wn.Portals.API;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.plx0wn.ConfigFiles;
import fr.plx0wn.EasyPortals;
import fr.plx0wn.Utils;

public class Portals {

	static FileConfiguration settings = ConfigFiles.settingsConf;
	static FileConfiguration portalsConf = ConfigFiles.portalsConf;
	static FileConfiguration msgConf = ConfigFiles.msgConf;

	public static void createPortal(Player player, String portal) {
		if (Informationnal.portalExist(portal)) {
			Utils.sendColoredMessage(player, Utils.ColoredString(msgConf.getString("portals.creating.already-exist")));
		} else {
			portalsConf.set("portals." + portal + ".name", portal);
			portalsConf.set("portals." + portal + ".location.world", "none");
			portalsConf.set("portals." + portal + ".location.x", "none");
			portalsConf.set("portals." + portal + ".location.y", "none");
			portalsConf.set("portals." + portal + ".location.z", "none");
			portalsConf.set("portals." + portal + ".location.pitch", "none");
			portalsConf.set("portals." + portal + ".location.yaw", "none");
			portalsConf.set("portals." + portal + ".block.world", "none");
			portalsConf.set("portals." + portal + ".block.x", "none");
			portalsConf.set("portals." + portal + ".block.y", "none");
			portalsConf.set("portals." + portal + ".block.z", "none");
			ConfigFiles.saveFiles();
			Utils.sendColoredMessage(player, Utils
					.ColoredString(msgConf.getString("portals.creating.create-portal").replace("<portal>", portal)));
		}
	}

	public static void removePortal(Player player, String portal) {
		if (Informationnal.portalExist(portal)) {
			portalsConf.set("portals." + portal, null);
			ConfigFiles.saveFiles();
			Utils.sendColoredMessage(player, Utils
					.ColoredString(msgConf.getString("portals.removing.remove-portal").replace("<portal>", portal)));
		} else {
			Utils.sendColoredMessage(player, Utils.ColoredString(msgConf.getString("commands.dont-exist")));
		}
	}

	public static void connectPortalBlock(Player player, Block block, String portal) {
		int x = block.getLocation().getBlockX();
		int y = block.getLocation().getBlockY();
		int z = block.getLocation().getBlockZ();
		if (Informationnal.portalExist(portal)) {
			if (Informationnal.isPortalBlockType(block)) {
				portalsConf.set("portals." + portal + ".block.world", player.getWorld().getName());
				portalsConf.set("portals." + portal + ".block.x", x);
				portalsConf.set("portals." + portal + ".block.y", y);
				portalsConf.set("portals." + portal + ".block.z", z);
				ConfigFiles.saveFiles();
				Utils.sendColoredMessage(player, Utils.ColoredString(
						msgConf.getString("portals.teleport-block.defined-success").replace("<portal>", portal)));
			} else {
				Utils.sendColoredMessage(player,
						Utils.ColoredString(msgConf.getString("portals.teleport-block.defined-error")
								.replace("<block>", settings.getString("portals.portal-block"))));
			}
		} else {
			Utils.sendColoredMessage(player, Utils.ColoredString(msgConf.getString("commands.dont-exist")));
		}
	}

	public static void defineLocation(Player player, String portal) {
		if (Informationnal.portalExist(portal)) {
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			float pitch = player.getLocation().getPitch();
			float yaw = player.getLocation().getYaw();
			portalsConf.set("portals." + portal + ".location.world", player.getWorld().getName());
			portalsConf.set("portals." + portal + ".location.x", x);
			portalsConf.set("portals." + portal + ".location.y", y);
			portalsConf.set("portals." + portal + ".location.z", z);
			portalsConf.set("portals." + portal + ".location.pitch", pitch);
			portalsConf.set("portals." + portal + ".location.yaw", yaw);
			ConfigFiles.saveFiles();
			Utils.sendColoredMessage(player,
					Utils.ColoredString(msgConf.getString("portals.location.defined").replace("<portal>", portal)));
		} else {
			Utils.sendColoredMessage(player, Utils.ColoredString(msgConf.getString("commands.dont-exist")));
		}
	}

	public static void teleportToLocation(Player player, String portal) {
		if (portalsConf.get("portals." + portal + ".location.x") instanceof Double) {
			player.teleport(Informationnal.getLocation(portal));
		} else {
			Utils.sendColoredMessage(player, Utils.ColoredString(msgConf.getString("portals.location.not-defined")));
		}
	}

	public static void sendPortalsList(Player player) {
		Utils.sendColoredMessage(player, "");
		Utils.sendColoredMessage(player, Utils.ColoredString("&aList of portals : "));
		for (int i = 0; i < Informationnal.getPortalsList().size(); i++) {
			Utils.sendColoredMessage(player, Utils.ColoredString("&a- " + Informationnal.getPortalsList().get(i)));
		}
		Utils.sendColoredMessage(player, "");
	}

}
