package com.worldbiomusic.minigameworldtest.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.worldbiomusic.minigameworldtest.MiniGameWorldTestMain;

public class Utils {
	public static String messagePrefix = ChatColor.BOLD + "MiniGameWorld-Test-Server" + ChatColor.RESET;
//	static Logger logger = main.getLogger();
	static ConsoleCommandSender sender = MiniGameWorldTestMain.getInstance().getServer().getConsoleSender();

	private static String getMessagePrefixString() {
		return "[" + messagePrefix + "] ";
	}

	public static void sendMsg(Player p, String msg) {
		p.sendMessage(getMessagePrefixString() + msg);
	}

	public static void info(String msg) {
		sender.sendMessage(getMessagePrefixString() + msg);
	}

	public static void warning(String msg) {
		sender.sendMessage(ChatColor.YELLOW + getMessagePrefixString() + msg);
	}

	public static void broadcast(String msg) {
		Bukkit.broadcastMessage(getMessagePrefixString() + msg);
	}

}
