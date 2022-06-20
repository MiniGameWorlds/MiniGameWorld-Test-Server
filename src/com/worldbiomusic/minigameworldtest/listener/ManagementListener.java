package com.worldbiomusic.minigameworldtest.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.worldbiomusic.minigameworldtest.MiniGameWorldTestMain;
import com.worldbiomusic.minigameworldtest.util.Setting;

public class ManagementListener implements Listener {
	private Set<UUID> playerChatLog;

	public ManagementListener() {
		this.playerChatLog = new HashSet<>();
	}

	private boolean enableEventIfOP(Cancellable e, Player p) {
		if (p.isOp()) {
			e.setCancelled(false);
			return true;
		}
		return false;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		// title
		p.sendTitle(ChatColor.YELLOW + "Welcome", "", 10, 20, 10);

		// tp to spawn
		p.teleport(Setting.SPAWN);

		// heal
		p.setHealth(p.getHealthScale());
		p.setFoodLevel(20);

		if (!p.isOp()) {
			p.setGameMode(GameMode.SURVIVAL);
		}
	}

	@EventHandler
	public void onBlockBroken(BlockBreakEvent e) {
		e.setCancelled(true);

		// check op
		enableEventIfOP(e, e.getPlayer());
	}

	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent e) {
		e.setCancelled(true);

		// check op
		enableEventIfOP(e, e.getPlayer());
	}

	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent e) {
		e.setCancelled(true);

		// check op
		enableEventIfOP(e, e.getPlayer());
	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent e) {
		e.setCancelled(true);

		// check op
		enableEventIfOP(e, e.getPlayer());
	}

	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			e.setCancelled(true);

			// check op
			enableEventIfOP(e, damager);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		e.setRespawnLocation(Setting.SPAWN);
	}

	/*
	 * Can send chat every 3 seconds
	 */
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		// check op
		if (enableEventIfOP(e, e.getPlayer())) {
			return;
		}

		UUID uuid = e.getPlayer().getUniqueId();

		// check player chat log
		if (this.playerChatLog.contains(uuid)) {
			e.setCancelled(true);
			return;
		}

		// log player
		this.playerChatLog.add(uuid);

		// remove after 3 seconds
		new BukkitRunnable() {
			@Override
			public void run() {
				playerChatLog.remove(uuid);
			}
		}.runTaskLater(MiniGameWorldTestMain.getInstance(), 20 * 3);

	}

	@EventHandler
	public void onPlayerInteractItemFrame(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.ITEM_FRAME
				|| e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME) {
			e.setCancelled(true);

			// check op
			enableEventIfOP(e, e.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Block b = e.getClickedBlock();
		if (b == null) {
			return;
		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (b.getType() == Material.CAKE) {
				e.setCancelled(true);

				// heal player
				Player p = e.getPlayer();
				p.setHealth(p.getHealthScale());
				p.setFoodLevel(20);
				p.sendTitle(ChatColor.GREEN + "Heal", "", 10, 20, 10);
				
				// sound
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
			}
		}
	}

	@EventHandler
	public void onMobSpawned(CreatureSpawnEvent e) {
		if (e.getSpawnReason() == SpawnReason.NATURAL) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void motd(ServerListPingEvent e) {
		String motd = "";
		motd += "" + ChatColor.RED + ChatColor.BOLD + "Mi";
		motd += "" + ChatColor.YELLOW + ChatColor.BOLD + "ni";
		motd += "" + ChatColor.GREEN + ChatColor.BOLD + "Ga";
		motd += "" + ChatColor.BLUE + ChatColor.BOLD + "me";
		e.setMotd(motd);
	}

}

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
