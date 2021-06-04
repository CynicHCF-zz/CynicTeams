package com.cynichcf.hcf.team.commands.team;

import com.cheatbreaker.api.object.CBWaypoint;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeamInfoCommand {

    @Command(names={ "team info", "t info", "f info", "faction info", "fac info", "team who", "t who", "f who", "faction who", "fac who", "team show", "t show", "f show", "faction show", "fac show", "team i", "t i", "f i", "faction i", "fac i" }, permission="")
    public static void teamInfo(Player sender, @Param(name="team", defaultValue="self", tabCompleteFlags={ "noteams", "players" }) Team team) {
        new BukkitRunnable() {

            public void run() {
                Team exactPlayerTeam = HCF.getInstance().getTeamHandler().getTeam(UUIDUtils.uuid(team.getName()));

                if (exactPlayerTeam != null && exactPlayerTeam != team) {
                    exactPlayerTeam.sendTeamInfo(sender);
                }

                team.sendTeamInfo(sender);
                if (team.getMembers().contains(sender.getUniqueId())) return;

                team.getOnlineMembers().forEach(player -> {
                    if(HCF.getInstance().getFDisplayMap().isToggled(player.getUniqueId())) {
                        CBWaypoint cbWaypoint = new CBWaypoint(team.getName(), team.getHQ().getBlockX(), team.getHQ().getBlockY(), team.getHQ().getBlockZ(), team.getHQ().getWorld().getUID().toString(), -16776961, true, true);
                        team.setFactionHQRally(cbWaypoint);
                    } else {
                        return;
                    }
                });
            }

        }.runTaskAsynchronously(HCF.getInstance());

        new BukkitRunnable() {

            public void run() {
                    team.setFactionHQRally(null);
            }
        }.runTaskLater(HCF.getInstance(), 20*60*5);
    }

}