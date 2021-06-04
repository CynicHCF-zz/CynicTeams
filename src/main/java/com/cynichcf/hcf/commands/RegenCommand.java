package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.TimeUtils;

public class RegenCommand {

    @Command(names={ "Regen", "DTR" }, permission="")
    public static void regen(Player sender, @Param(name="team", defaultValue="self") Team team) {
        if (!sender.isOp()) {
            team = HCF.getInstance().getTeamHandler().getTeam(sender);
        }

        if (team == null) {
            sender.sendMessage(ChatColor.GRAY + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if (team.getMaxDTR() == team.getDTR()) {
            sender.sendMessage(ChatColor.GRAY + "Your team is currently at max DTR, which is " + ChatColor.AQUA + team.getMaxDTR() + ChatColor.GRAY + ".");
            return;
        }

        sender.sendMessage(ChatColor.GRAY + "Your team has a max DTR of " + ChatColor.AQUA + team.getMaxDTR() + ChatColor.GRAY + ".");
        sender.sendMessage(ChatColor.GRAY + "You are regaining DTR at a rate of " + ChatColor.AQUA + Team.DTR_FORMAT.format(team.getDTRIncrement() * 60) + "/hour" + ChatColor.GRAY + ".");
        sender.sendMessage(ChatColor.GRAY + "At this rate, it will take you " + ChatColor.AQUA + (hrsToRegain(team) == -1 ? "Infinity" : hrsToRegain(team)) + ChatColor.GRAY + " hours to fully gain all DTR.");

        if (team.getDTRCooldown() > System.currentTimeMillis()) {
            sender.sendMessage(ChatColor.GRAY + "Your team is on DTR cooldown for " + ChatColor.AQUA + TimeUtils.formatIntoDetailedString((int) (team.getDTRCooldown() - System.currentTimeMillis()) / 1000) + ChatColor.GRAY + ".");
        }
    }

    private static double hrsToRegain(Team team) {
        double diff = team.getMaxDTR() - team.getDTR();
        double dtrIncrement = team.getDTRIncrement();

        if (dtrIncrement == 0D) {
            return (-1);
        }

        double required = diff / dtrIncrement;
        double h = required / 60D;

        return (Math.round(10.0 * h) / 10.0);
    }

}