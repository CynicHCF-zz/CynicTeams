package com.cynichcf.hcf.powers.commands;

import com.cynichcf.hcf.powers.Fight;
import com.cynichcf.hcf.util.CC;
import mkremins.fanciful.FancyMessage;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

/*
Made by Cody at 2:45 AM on 8/10/20
 */
public class FightsCommand {

    AtomicInteger i = new AtomicInteger();
    //a quick way to debug fights
    @Command(names = "fights", permission = "foxtrot.managefights")
    public static void onFights(Player player) {

        if (HCF.getInstance().getFightHandler().getFights().size() == 0) {
            player.sendMessage(CC.translate("&eNo fights currently"));
            return;
        }

        FancyMessage fancyMessage = new FancyMessage();

        player.sendMessage(CC.MENU_BAR);
        player.sendMessage(CC.translate("&eActive Fights"));
        player.sendMessage(CC.MENU_BAR);
        for (Fight fight : HCF.getInstance().getFightHandler().getFights()) {
            Team team = fight.getTeam1();
            Team team2 = fight.getTeam2();

            fancyMessage.text(CC.translate("&eAttacking: ") + team.getName() + "(" + team.getOnlineMembers().size() + ") " + ChatColor.GREEN + " Defending: " + team2.getName() + "(" + team2.getOnlineMembers().size() + ")")
                    .tooltip(ChatColor.GREEN + "More info")
                    .command("/fight info " + team.getName() + " " + team2.getName()).send(player);
        }
        player.sendMessage(CC.MENU_BAR);
    }

    @Command(names = "fight info", permission = "foxtrot.managefights")
    public static void onFightInfo(Player player, @Param(name = "team") Team team, @Param(name = "team2") Team team2) {

        Fight fight = HCF.getInstance().getFightHandler().getFight(team + ":" + team2);

        player.sendMessage(CC.translate("&eFight Statistics:"));
        player.sendMessage(CC.translate("&e" + team.getName() + " hits: " + fight.getHits().get(team)));
        player.sendMessage(CC.translate("&e" + team2.getName() + " hits: " + fight.getHits().get(team2)));
        player.sendMessage(CC.translate("&e" + (fight.getHits().get(team) > fight.getHits().get(team2) ? team.getName() + " is winning" : team2.getName() + " is winning")));

    }

    @Command(names = {"addfight", "makefight", "createfight", "fightmake"}, permission = "foxtrot.managefights", hidden = true)
    public static void onFightAdd(Player player, @Param(name = "team1", tabCompleteFlags = {"no teams on"})Team team1, @Param(name = "team2", tabCompleteFlags = {"no teams on"})Team team2) {
        //Standard error checking procedure.
        if (team1 == null) {
            player.sendMessage(CC.translate("Team one is null or non existent"));
            return;
        }
        if (team2 == null) {
            player.sendMessage(CC.translate("Team two is null or non existent"));
            return;
        }
        if(team1.getMembers().size() == 0 || team2.getMembers().size() == 0) {
            player.kickPlayer(CC.translate("&eThere are no members on in one of the teams, are you on meth?"));
            return;
        }

        Fight fight = new Fight(team1.getName() + ":" + team2.getName(), team1, team2, 0L, 0);
        HCF.getInstance().getFightHandler().createFight(fight);
    }

    @Command(names = {"removefight", "killfight", "remfight", "fightremove"}, permission = "foxtrot.managefights", hidden = true)
    public static void onFightRemove(Player player, @Param(name = "team1", tabCompleteFlags = {"no teams on"})Team team1, @Param(name = "team2") Team team2) {
        //Standard error checking procedure.
        if (team1 == null) {
            player.sendMessage(CC.translate("Team one is null or non existent"));
            return;
        }
        if (team2 == null) {
            player.sendMessage(CC.translate("Team two is null or non existent"));
            return;
        }
        if(team1.getMembers().size() == 0 || team2.getMembers().size() == 0) {
            player.kickPlayer(CC.translate("&eThere are no members on in one of the teams, are you on meth?"));
        }

        HCF.getInstance().getFightHandler().removeFight(team1.getName() + ":" + team2.getName());
    }

}
