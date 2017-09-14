package fr.plx0wn;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
	
	public static String ColoredString(String msg){
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void sendColoredMessage(Player player, String msg){
		player.sendMessage(ColoredString(msg));
	}

}
