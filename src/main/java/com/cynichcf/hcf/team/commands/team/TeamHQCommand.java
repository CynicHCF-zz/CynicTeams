package com.cynichcf.hcf.team.commands.team;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamHQCommand {

    @Command(names={ "team hq", "t hq", "f hq", "faction hq", "fac hq", "team home", "t home", "f home", "faction home", "fac home", "home", "hq" }, permission="")
    public static void teamHQ(Player sender) {
        if (HCF.getInstance().getDeathbanMap().isDeathbanned(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "You can not do this while you are deathbanned.");
            return;
        }

        Team team = HCF.getInstance().getTeamHandler().getTeam(sender);

        if (team == null) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if (team.getHQ() == null) {
            sender.sendMessage(ChatColor.RED + "Sadly there is no HQ point set.");
            return;
        }

        if (HCF.getInstance().getServerHandler().isEOTW()) {
            sender.sendMessage(ChatColor.RED + "You cannot teleport to your Team HQ during the End of the World!");
            return;
        }

        if (sender.hasMetadata("frozen")) {
            sender.sendMessage(ChatColor.RED + "You cannot teleport to your Team HQ while you're frozen!");
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(sender)) {
            sender.sendMessage(ChatColor.RED + "You cannot teleport to the Team HQ during a duel!");
            return;
        }

        if (HCF.getInstance().getPvPTimerMap().hasTimer(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "Use /pvp enable to toggle your PvP Timer off!");
            return;
        }
/**
        boolean charge = team != LandBoard.getInstance().getTeam(sender.getLocation()) && !Foxtrot.getInstance().getConfig().getBoolean("legions");

        if (charge && team.getBalance() < (Foxtrot.getInstance().getServerHandler().isHardcore() ? 20 : 50)) {
            sender.sendMessage(ChatColor.RED + "Your team needs at least $" + (Foxtrot.getInstance().getServerHandler().isHardcore() ? 20 : 50) + " to teleport to your team headquarters.");
            return;
        }
 */

        HCF.getInstance().getServerHandler().beginHQWarp(sender, team, 10, false);
    }
}