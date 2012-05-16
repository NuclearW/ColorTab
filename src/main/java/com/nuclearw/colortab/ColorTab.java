package com.nuclearw.colortab;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
		colorName(player);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("ctmagic")) {
			if(args.length > 1) return false;

			Player target;

			if(args.length == 0) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("Console cannot have a magic colortab name");
					return true;
				} else {
					if(!sender.hasPermission("colortab.magic")) {
						sender.sendMessage("You don't have permission to do that.");
						return true;
					}
				}

				target = (Player) sender;
			} else {
				if(!sender.hasPermission("colortab.magic.other")) {
					sender.sendMessage("You don't have permission to do that.");
					return true;
				}

				target = getServer().getPlayer(args[0]);
			}

			if(target == null) {
				sender.sendMessage("Could not find target.");
				return true;
			} else {
				String magicName = ChatColor.LIGHT_PURPLE+""+ChatColor.MAGIC+target.getName()+target.getName();
				if(magicName.length() > 16) magicName = magicName.substring(0, 16);

				if(target.getPlayerListName().equals(magicName)) {
					colorName(target);
				} else {
					target.setPlayerListName(magicName);
				}
			}
		}
		return true;
	}

	private void colorName(Player player) {
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
