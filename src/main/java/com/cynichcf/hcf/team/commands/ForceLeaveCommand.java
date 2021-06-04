package com.cynichcf.hcf.team.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ForceLeaveCommand {

    @Command(names={ "forceleave" }, permission="foxtrot.forceleave")
    public static void forceLeave(Player player) {
        Team team = HCF.getInstance().getTeamHandler().getTeam(player);

        if (team == null) {
            player.sendMessage(ChatColor.RED + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        team.removeMember(player.getUniqueId());
        HCF.getInstance().getTeamHandler().setTeam(player.getUniqueId(), null);
        player.sendMessage(ChatColor.YELLOW + "Force left your team.");
    }

}