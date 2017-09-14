package fr.plx0wn;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.defaults.ReloadCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ConfigFiles {

	public static FileConfiguration portalsConf, msgConf, defineConf, settingsConf;
	public static File portalsFile, msgFile, defineFile, settingsFile;

	public static void createFiles() {
		settingsFile = new File(EasyPortals.instance.getDataFolder(), "settings.yml");
		portalsFile = new File(EasyPortals.instance.getDataFolder(), "portals.yml");
		msgFile = new File(EasyPortals.instance.getDataFolder(), "messages.yml");
		defineFile = new File(EasyPortals.instance.getDataFolder(), "tmp.yml");

		if (!settingsFile.exists()) {
			settingsFile.getParentFile().mkdirs();
			EasyPortals.instance.saveResource("settings.yml", false);
		}

		if (!portalsFile.exists()) {
			portalsFile.getParentFile().mkdirs();
			EasyPortals.instance.saveResource("portals.yml", false);
		}

		if (!msgFile.exists()) {
			msgFile.getParentFile().mkdirs();
			EasyPortals.instance.saveResource("messages.yml", false);
		}

		if (!defineFile.exists()) {
			msgFile.getParentFile().mkdirs();
			EasyPortals.instance.saveResource("tmp.yml", false);
		}

		try {
			settingsConf = new YamlConfiguration();
			settingsConf.load(settingsFile);

			portalsConf = new YamlConfiguration();
			portalsConf.load(portalsFile);

			msgConf = new YamlConfiguration();
			msgConf.load(msgFile);

			defineConf = new YamlConfiguration();
			defineConf.load(defineFile);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveFiles() {
		try {
			settingsConf.save(settingsFile);

			portalsConf.save(portalsFile);

			msgConf.save(msgFile);

			defineConf.save(defineFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reloadFiles(Player player) {
		try {
			settingsConf.load(settingsFile);
			player.sendMessage(Utils.ColoredString("&asettings.yml correctly reloaded"));
		} catch (Exception e) {
			e.printStackTrace();
			player.sendMessage(Utils.ColoredString("&csettings.yml can't be reloaded. See logs."));
		}
		try {
			portalsConf.load(portalsFile);
			player.sendMessage(Utils.ColoredString("&aportals.yml correctly reloaded"));
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
			player.sendMessage(Utils.ColoredString("&cportals.yml can't be reloaded. See logs."));
		}
		try {
			msgConf.load(msgFile);
			player.sendMessage(Utils.ColoredString("&amessages.yml correctly reloaded"));
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
			player.sendMessage(Utils.ColoredString("&cmessages.yml can't be reloaded. See logs."));
		}
		try {
			defineConf.load(defineFile);

		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

}
