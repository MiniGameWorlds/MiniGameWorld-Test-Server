package com.worldbiomusic.minigameworldtest;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.worldbiomusic.minigameworldtest.listener.ManagementListener;
import com.worldbiomusic.minigameworldtest.util.Utils;

public class MiniGameWorldTestMain extends JavaPlugin {

	private static MiniGameWorldTestMain instance;
	private ManagementListener manageListener;

	public static MiniGameWorldTestMain getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;

		Utils.info(ChatColor.GREEN + "MiniGameWorldTestMain ON");

		this.manageListener = new ManagementListener();
		getServer().getPluginManager().registerEvents(this.manageListener, this);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		Utils.info(ChatColor.RED + "MiniGameWorldTestMain OFF");
	}
}
