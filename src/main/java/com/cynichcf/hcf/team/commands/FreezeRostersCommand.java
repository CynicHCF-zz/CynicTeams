package com.cynichcf.hcf.team.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.TeamHandler;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FreezeRostersCommand {

    @Command(names={ "freezerosters" }, permission="op")
    public static void freezeRosters(Player sender) {
        TeamHandler teamHandler = HCF.getInstance().getTeamHandler();
        teamHandler.setRostersLocked(!teamHandler.isRostersLocked());

        sender.sendMessage(ChatColor.YELLOW + "Team rosters are now " + ChatColor.LIGHT_PURPLE + (teamHandler.isRostersLocked() ? "locked" : "unlocked") + ChatColor.YELLOW + ".");
    }

}