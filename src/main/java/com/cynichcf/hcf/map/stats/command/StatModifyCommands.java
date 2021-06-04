package com.cynichcf.hcf.map.stats.command;

import com.cynichcf.hcf.map.stats.StatsEntry;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StatModifyCommands {

	@Command(names = "sm setkills", permission = "op")
	public static void setKills(Player player, @Param(name = "kills") int kills) {

		StatsEntry stats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(player);
		stats.setKills(kills);

		HCF.getInstance().getKillsMap().setKills(player.getUniqueId(), kills);

		player.sendMessage(ChatColor.GREEN + "You've set your own kills to: " + kills);
	}

	@Command(names = "sm setdeaths", permission = "op")
	public static void setDeaths(Player player, @Param(name = "deaths") int deaths) {

		StatsEntry stats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(player);
		stats.setDeaths(deaths);

		HCF.getInstance().getDeathsMap().setDeaths(player.getUniqueId(), deaths);

		player.sendMessage(ChatColor.GREEN + "You've set your own deaths to: " + deaths);
	}

	@Command(names = "sm setteamkills", permission = "op")
	public static void setTeamKills(Player player, @Param(name = "kills") int kills) {

		Team team = HCF.getInstance().getTeamHandler().getTeam(player);

		if (team != null) {
			team.setKills(kills);
			player.sendMessage(ChatColor.GREEN + "You've set your team's kills to: " + kills);
		}
	}

	@Command(names = "sm setteamdeaths", permission = "op")
	public static void setTeamDeaths(Player player, @Param(name = "deaths") int deaths) {
		Team team = HCF.getInstance().getTeamHandler().getTeam(player);

		if (team != null) {
			team.setDeaths(deaths);
			player.sendMessage(ChatColor.GREEN + "You've set your team's deaths to: " + deaths);
		}
	}

}
