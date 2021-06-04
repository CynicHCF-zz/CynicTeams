package com.cynichcf.hcf.team.commands.team;

import com.google.common.collect.ImmutableMap;

import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.track.TeamActionTracker;
import com.cynichcf.hcf.team.track.TeamActionType;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class TeamUnMuteCommand {

    @Command(names={ "team unmute", "t unmute", "f unmute", "faction unmute", "fac unmute" }, permission="foxtrot.mutefaction")
    public static void teamUnMute(Player sender, @Param(name = "team") Team team) {
        TeamActionTracker.logActionAsync(team, TeamActionType.TEAM_MUTE_EXPIRED, ImmutableMap.of(
                "shadowMute", "false"
        ));

        Iterator<Map.Entry<UUID, String>> mutesIterator = TeamMuteCommand.getTeamMutes().entrySet().iterator();

        while (mutesIterator.hasNext()) {
            Map.Entry<UUID, String> mute = mutesIterator.next();

            if (mute.getValue().equalsIgnoreCase(team.getName())) {
                mutesIterator.remove();
            }
        }

        sender.sendMessage(CC.translate("§eUnmuted the team §9" + team.getName() + "§e."));
    }

}