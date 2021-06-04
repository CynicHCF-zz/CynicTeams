package com.cynichcf.hcf.team.upgrades.command;

import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.upgrades.menu.TeamUpgradesMenu;
import rip.lazze.libraries.command.Command;
import org.bukkit.entity.Player;

public class TeamUpgradesCommand {

    @Command(names = {"f upgrades", "t upgrades", "faction upgrades", "team upgrades"}, permission = "")
    public static void onCommand(Player player) {
        Team team = HCF.getInstance().getTeamHandler().getTeam(player.getUniqueId());
        if (team == null) {
            player.sendMessage(CC.translate("&cYou must be on a team to perform this command."));
            return;
        }
        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            player.sendMessage(CC.translate("&cThis is not available during KitMap"));
            return;
        }

        if(team.getCaptains().contains(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be a captain to perform this command"));
            return;
        }

        new TeamUpgradesMenu(team).openMenu(player);


    }
}
