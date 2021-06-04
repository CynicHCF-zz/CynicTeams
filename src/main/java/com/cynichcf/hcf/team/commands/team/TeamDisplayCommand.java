package com.cynichcf.hcf.team.commands.team;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.object.CBWaypoint;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamDisplayCommand {

    @Command(names={ "team display", "t display", "f display", "faction display", "fac display" }, permission="")
    public static void teamDisplay(Player sender, @Param(name="team") Team team) {
        if (HCF.getInstance().getDeathbanMap().isDeathbanned(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "You can not do this while you are deathbanned, come back later!");
            return;
        }

        Team senderTeam = HCF.getInstance().getTeamHandler().getTeam(sender);

        if (senderTeam == null) {
            sender.sendMessage(ChatColor.RED + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if (!senderTeam.isOwner(sender.getUniqueId()) && !senderTeam.isCoLeader(sender.getUniqueId()) && !senderTeam.hasDisplayPermission(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.DARK_AQUA + "You lack permissions for that, ask your leader if this is an error.");
            return;
        }

//        if (senderTeam.equals(team)) {
//            sender.sendMessage(ChatColor.RED + "You cannot display your own team!");
//            return;
//        }

        if (team.getHQ() == null) {
            sender.sendMessage(ChatColor.RED + "Sadly there is no HQ point set.");
            return;
        }

        CBWaypoint waypoint = new CBWaypoint(team.getName(), team.getHQ(), -16776961, true);
        for (Player member : senderTeam.getOnlineMembers()) {
            CheatBreakerAPI.getInstance().sendWaypoint(member, waypoint);
        }

        senderTeam.sendMessage(ChatColor.DARK_AQUA + sender.getName() + " has set the HQ point and has created a waypoint.");


    }

}