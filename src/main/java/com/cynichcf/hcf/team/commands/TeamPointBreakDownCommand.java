package com.cynichcf.hcf.team.commands;

import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamPointBreakDownCommand {

	@Command(names = { "team pointbr", "team pbr", "t pointbr", "t pbr" }, permission = "op")
	public static void teamPointBreakDown(Player player, @Param(name="team", defaultValue="self") Team team) {
		player.sendMessage(ChatColor.GOLD + "Point Breakdown of " + team.getName());

		for (String info : team.getPointBreakDown()) {
			player.sendMessage(info);
		}
	}

}
