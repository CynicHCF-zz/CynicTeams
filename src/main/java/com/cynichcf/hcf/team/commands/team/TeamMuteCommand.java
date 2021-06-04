package com.cynichcf.hcf.team.commands.team;

import com.google.common.collect.ImmutableMap;

import com.cynichcf.hcf.util.CC;
import lombok.Getter;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.track.TeamActionTracker;
import com.cynichcf.hcf.team.track.TeamActionType;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class TeamMuteCommand {

    @Getter private static Map<UUID, String> teamMutes = new HashMap<>();

    @Command(names={ "team mute", "t mute", "f mute", "faction mute", "fac mute" }, permission="foxtrot.mutefaction")
    public static void teamMute(Player sender, @Param(name="team") Team team, @Param(name="time") int time, @Param(name="reason", wildcard=true) String reason) {
        int timeSeconds = time * 60;

        for (UUID player : team.getMembers()) {
            teamMutes.put(player, team.getName());

            Player bukkitPlayer = HCF.getInstance().getServer().getPlayer(player);

            if (bukkitPlayer != null) {
                bukkitPlayer.sendMessage(CC.translate("§c§lYour team has been muted for" + TimeUtils.formatIntoMMSS(timeSeconds) + " for " + reason + "."));
            }
        }

        TeamActionTracker.logActionAsync(team, TeamActionType.TEAM_MUTE_CREATED, ImmutableMap.of(
                "shadowMute", "false",
                "mutedById", sender.getUniqueId(),
                "mutedByName", sender.getName(),
                "duration", time
        ));

        new BukkitRunnable() {

            public void run() {
                TeamActionTracker.logActionAsync(team, TeamActionType.TEAM_MUTE_EXPIRED, ImmutableMap.of(
                        "shadowMute", "false"
                ));

                Iterator<Map.Entry<UUID, String>> mutesIterator = teamMutes.entrySet().iterator();

                while (mutesIterator.hasNext()) {
                    Map.Entry<UUID, String> mute = mutesIterator.next();

                    if (mute.getValue().equalsIgnoreCase(team.getName())) {
                        mutesIterator.remove();
                    }
                }
            }

        }.runTaskLater(HCF.getInstance(), timeSeconds * 20L);

        sender.sendMessage(CC.translate("§eMuted the team §9" + team.getName() + " §efor §c" + TimeUtils.formatIntoMMSS(timeSeconds) + " §efor §9" + reason + "§e."));
    }

}