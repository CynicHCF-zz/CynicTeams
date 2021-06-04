package com.cynichcf.hcf.team.commands.team;

import com.cheatbreaker.api.object.CBWaypoint;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.RallyPoint;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class TeamRallyCommand {

    @Command(names={ "team rally", "t rally", "f rally", "faction rally", "fac rally", "team setrally", "t setrally", "f setrally", "faction setrally", "fac setrally" }, permission="")
    public static void rally(Player sender) {
        Team senderTeam = HCF.getInstance().getTeamHandler().getTeam(sender);
        if (senderTeam == null) {
            sender.sendMessage(ChatColor.GRAY + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if(!senderTeam.hasRallyPermission(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Only members with /f perms can do this.");
            return;
        }

        CBWaypoint cbWaypoint = new CBWaypoint("Rally", sender.getLocation().getBlockX(), sender.getLocation().getBlockY(), sender.getLocation().getBlockZ(), sender.getLocation().getWorld().getUID().toString(), -16776961, true, true);
        RallyPoint rallyPoint = new RallyPoint(sender.getLocation(), cbWaypoint, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));

        senderTeam.setRally(rallyPoint);
        senderTeam.sendMessage(ChatColor.DARK_AQUA + sender.getName() + " has updated the team's rally point! This will last for 10 minutes.");

    }

    @Command(names={ "team wiperally", "t wiperally", "f wiperally", "faction wiperally", "fac wiperally" }, permission="")
    public static void wiperally(Player sender) {
        Team senderTeam = HCF.getInstance().getTeamHandler().getTeam(sender);
        if (senderTeam == null) {
            sender.sendMessage(ChatColor.GRAY + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if(!senderTeam.hasRallyPermission(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Only members with /t perms can do this.");
            return;
        }

        RallyPoint rallyPoint = senderTeam.getRally();
        if(rallyPoint == null) {
            sender.sendMessage(ChatColor.GRAY + "Your team does not have a rally set, to set one, use /t rally.");
            return;
        }

        senderTeam.setRally(null);
        senderTeam.sendMessage(ChatColor.DARK_AQUA + sender.getName() + " has wiped the team's rally.");

    }

}
