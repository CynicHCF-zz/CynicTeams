package com.cynichcf.hcf.team.commands.team;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import org.bukkit.entity.*;
import org.bukkit.*;

public class TeamOpenCommand {

    @Command(names={ "team open", "t open", "f open", "faction open", "fac open" }, permission="")
    public static void teamOpen(Player sender) {
        if (!(sender instanceof Player)) {
        sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
        return;
    }
     Team playerFaction = HCF.getInstance().getTeamHandler().getTeam(sender);
        if (playerFaction == null) {
        sender.sendMessage(ChatColor.RED + "You are not in a faction.");
        return;
    }
        if (!playerFaction.isOwner(sender.getUniqueId())) {
        sender.sendMessage(ChatColor.RED + "You must be a faction leader to do this.");
        return;
    }
        boolean newOpen = !playerFaction.isOpen();
        playerFaction.setOpen(newOpen);
        playerFaction.sendMessage(ChatColor.YELLOW + sender.getName() + " has " + (newOpen ? (ChatColor.GREEN + "opened") : (ChatColor.RED + "closed")) + ChatColor.YELLOW + " the faction to public.");
        return;
        }
    }
