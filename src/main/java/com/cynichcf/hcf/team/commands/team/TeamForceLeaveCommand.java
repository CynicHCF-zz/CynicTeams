package com.cynichcf.hcf.team.commands.team;

import com.cynichcf.hcf.server.SpawnTagHandler;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.claims.LandBoard;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.nametag.FrozenNametagHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamForceLeaveCommand {

    @Command(names={  "team forceleave", "t forceleave", "f forceleave", "faction forceleave", "fac forceleave", "t fl", "team fl" }, permission="")
    public static void forceLeave(Player sender) {
        if (HCF.getInstance().getDeathbanMap().isDeathbanned(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "You can't do this while you are deathbanned.");
            return;
        }

        Team team = HCF.getInstance().getTeamHandler().getTeam(sender);

        if (team == null) {
            sender.sendMessage(ChatColor.GRAY + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if (team.isOwner(sender.getUniqueId()) && team.getSize() > 1) {
            sender.sendMessage(ChatColor.RED + "Please choose a new leader before leaving your team!");
            return;
        }

        if (LandBoard.getInstance().getTeam(sender.getLocation()) == team) {
            sender.sendMessage(ChatColor.RED + "You cannot leave your team while on team territory.");
            return;
        }

        if (team.removeMember(sender.getUniqueId())) {
            team.disband();
            HCF.getInstance().getTeamHandler().setTeam(sender.getUniqueId(), null);
            sender.sendMessage(ChatColor.DARK_AQUA + "Successfully left and disbanded team!");
        } else {
            HCF.getInstance().getTeamHandler().setTeam(sender.getUniqueId(), null);
            team.flagForSave();

            if (SpawnTagHandler.isTagged(sender)) {
                team.setDTR(team.getDTR() - 1);
                team.sendMessage(ChatColor.RED + sender.getName() + " forcibly left the team. Your team has lost 1 DTR.");

                sender.sendMessage(ChatColor.RED + "You have forcibly left your team. Your team lost 1 DTR.");
            } else {
                team.sendMessage(ChatColor.YELLOW + sender.getName() + " has left the team.");

                sender.sendMessage(ChatColor.DARK_AQUA + "Successfully left the team!");
            }
        }

        FrozenNametagHandler.reloadPlayer(sender);
        FrozenNametagHandler.reloadOthersFor(sender);
    }
}
