package fr.plx0wn.Signs;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.plx0wn.ConfigFiles;
import fr.plx0wn.EasyPortals;
import fr.plx0wn.Utils;
import fr.plx0wn.Portals.API.Informationnal;
import fr.plx0wn.Portals.API.Portals;

public class Events implements Listener {

	static FileConfiguration settings = ConfigFiles.settingsConf;
	static FileConfiguration msgConf = ConfigFiles.msgConf;
	static FileConfiguration portalsConf = ConfigFiles.portalsConf;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChangeSign(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission("signs.create")) {
			if (e.getLine(0).equalsIgnoreCase(settings.getString("signs.first-line"))) {
				e.setLine(0, Utils.ColoredString("&1" + settings.getString("signs.first-line")));
				if (Informationnal.portalExist(e.getLine(1))) {
					e.setLine(1, e.getLine(1));
				} else {
					e.setLine(1, Utils.ColoredString("&4" + e.getLine(1)));
				}
			}
		} else {
			Utils.sendColoredMessage(e.getPlayer(), msgConf.getString("signs.no-permissions"));
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		try {
			if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)
					|| e.getClickedBlock().getType().equals(Material.SIGN_POST)
					|| e.getClickedBlock().getType().equals(Material.SIGN)) {
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					Sign sign = (Sign) e.getClickedBlock().getState();
					if (sign.getLine(0)
							.equalsIgnoreCase(Utils.ColoredString("&1" + settings.getString("signs.first-line")))) {
						if (e.getPlayer().hasPermission("portals.use")) {
							if (Informationnal.portalExist(sign.getLine(1))) {
								Portals.teleportToLocation(e.getPlayer(), sign.getLine(1));
							} else {
								Utils.sendColoredMessage(e.getPlayer(), msgConf.getString("portals.dont-exist"));
							}
						} else {
							Utils.sendColoredMessage(e.getPlayer(), msgConf.getString("system.no-permissions"));
						}
					}
				}
			}
		} catch (NullPointerException ex) {

		}
	}

}
