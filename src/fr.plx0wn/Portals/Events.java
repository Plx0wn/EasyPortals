package fr.plx0wn.Portals;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.plx0wn.ConfigFiles;
import fr.plx0wn.EasyPortals;
import fr.plx0wn.Utils;
import fr.plx0wn.Portals.API.Informationnal;
import fr.plx0wn.Portals.API.Portals;

public class Events implements Listener {

	public static ArrayList<Player> blockDefining = new ArrayList<>();
	static FileConfiguration settings = ConfigFiles.settingsConf;
	static FileConfiguration tmp = ConfigFiles.defineConf;
	static FileConfiguration msgConf = ConfigFiles.msgConf;
	static FileConfiguration portalsConf = ConfigFiles.portalsConf;

	@EventHandler
	public static void onClick(PlayerInteractEvent e) {
		if (blockDefining.contains(e.getPlayer())) {
			if (Informationnal.isPortalBlockType(e.getClickedBlock())) {
				if ((Informationnal.getPortalByBlock(e.getClickedBlock()) == null)) {
					if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						String tmpstr = tmp.getString("tmp." + e.getPlayer().getName() + ".portal");
						Portals.connectPortalBlock(e.getPlayer(), e.getClickedBlock(), tmpstr);
						blockDefining.remove(e.getPlayer());
						tmp.set("tmp", null);
					}
				} else {
					e.getPlayer()
							.sendMessage(Utils.ColoredString(
									msgConf.getString("portals.teleport-block.already-defined").replace("<portal>",
											Informationnal.getPortalByBlock(e.getClickedBlock()))));
					blockDefining.remove(e.getPlayer());
				}
			} else {
				Utils.sendColoredMessage(e.getPlayer(),
						Utils.ColoredString(msgConf.getString("portals.teleport-block.defined-error")
								.replace("<block>", settings.getString("portals.portal-block"))));
				blockDefining.remove(e.getPlayer());
			}
		}
	}

	@EventHandler
	public static void onWalk(PlayerMoveEvent e) {
		Block block = e.getTo().getBlock().getRelative(BlockFace.DOWN);
		if (Informationnal.isPortalBlockType(block)) {
			try {
				if (!(Informationnal.getPortalByBlock(block) == null)) {
					Portals.teleportToLocation(e.getPlayer(), Informationnal.getPortalByBlock(block));
				}
			} catch (NullPointerException ex) {

			}
		}
	}

	@EventHandler
	public static void onBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		if (Informationnal.isPortalBlockType(block)) {
			try {
				if (!(Informationnal.getPortalByBlock(block) == null)) {
					String portal = Informationnal.getPortalByBlock(block);
					portalsConf.set("portals." + portal + ".block.world", "none");
					portalsConf.set("portals." + portal + ".block.x", "none");
					portalsConf.set("portals." + portal + ".block.y", "none");
					portalsConf.set("portals." + portal + ".block.z", "none");
					ConfigFiles.saveFiles();
					Utils.sendColoredMessage(e.getPlayer(), Utils.ColoredString(
							msgConf.getString("portals.teleport-block.block-destroyed").replace("<portal>", portal)));
				}
			} catch (NullPointerException ex) {

			}
		}
	}

}
