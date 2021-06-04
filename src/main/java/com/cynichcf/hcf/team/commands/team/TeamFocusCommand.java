package com.cynichcf.hcf.team.commands.team;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.object.CBWaypoint;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamFocusCommand {



    @Command(names = {"f focus", "t focus", "faction focus", "team focus"}, permission = "")
    public static void focus(Player sender, @Param(name = "player") Team targetTeam){

        Team senderTeam = HCF.getInstance().getTeamHandler().getTeam(sender);
        CBWaypoint cbWaypoint = null;

        if (senderTeam == null){
            sender.sendMessage(ChatColor.GRAY + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if (senderTeam == targetTeam){
            sender.sendMessage(ChatColor.RED + "You cannot focus a player on your team!");
            return;
        }

        if (senderTeam.getFactionFocused() == targetTeam) {
            senderTeam.setFactionFocus(null);
            senderTeam.sendMessage(ChatColor.GREEN + "Faction focus has been disabled.");
            CBWaypoint finalCbWaypoint = cbWaypoint;
        } else if (targetTeam.getHQ() != null){
            senderTeam.setFactionFocus(targetTeam);
            cbWaypoint = new CBWaypoint(targetTeam.getName() + "'s HQ", targetTeam.getHQ().getBlockX(), targetTeam.getHQ().getBlockY(), targetTeam.getHQ().getBlockZ(), targetTeam.getHQ().getWorld().getUID().toString(), -16776961, true, true);
            CBWaypoint finalCbWaypoint1 = cbWaypoint;
            senderTeam.getOnlineMembers().forEach(player -> CheatBreakerAPI.getInstance().sendWaypoint(player, finalCbWaypoint1));
            CheatBreakerAPI.getInstance().sendWaypoint(sender, finalCbWaypoint1);
            senderTeam.sendMessage(ChatColor.LIGHT_PURPLE + targetTeam.getName() + ChatColor.YELLOW + " has been focused by " + ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.YELLOW + ".");
        } if (targetTeam.getHQ() == null){
            senderTeam.sendMessage(ChatColor.RED + "Home: None (not set)");
        }
    }
}