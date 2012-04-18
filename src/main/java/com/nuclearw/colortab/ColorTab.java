package com.nuclearw.colortab;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ColorTab extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		getLogger().info("Finished Loading " + getDescription().getFullName());
	}

	@Override
	public void onDisable() {
		getLogger().info("Finished Unloading " + getDescription().getFullName());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();

		if(playerName.length() <= 14) {
			String pexPrefix = PermissionsEx.getPermissionManager().getUser(playerName).getPrefix();
			if(pexPrefix.startsWith("&") && pexPrefix.length() >= 2) {
				String pexPrefixColor = pexPrefix.substring(0, 2);
				String setName = colorize(pexPrefixColor + playerName);

				player.setPlayerListName(setName);
			}
		}
	}

	private String colorize(String string) {
		if(string == null){
			return "";
		}

		return string.replaceAll("&([a-z0-9])", "\u00A7$1");
	}
}
