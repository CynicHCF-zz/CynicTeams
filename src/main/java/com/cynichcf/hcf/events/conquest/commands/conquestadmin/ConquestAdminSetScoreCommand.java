package com.cynichcf.hcf.events.conquest.commands.conquestadmin;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.conquest.ConquestHandler;
import com.cynichcf.hcf.events.conquest.game.ConquestGame;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ConquestAdminSetScoreCommand {

    @Command(names={ "conquestadmin setscore" }, permission="op")
    public static void conquestAdminSetScore(CommandSender sender, @Param(name="team") Team team, @Param(name="score") int score) {
        ConquestGame game = HCF.getInstance().getConquestHandler().getGame();

        if (game == null) {
            sender.sendMessage(ChatColor.RED + "Conquest is not active.");
            return;
        }

        game.getTeamPoints().put(team.getUniqueId(), score);
        sender.sendMessage(ConquestHandler.PREFIX + " " + ChatColor.GOLD + "Updated the score for " + team.getName() + ChatColor.GOLD + ".");
    }

}