package com.cynichcf.hcf.map.stats.command;

import com.google.common.collect.Maps;
import com.cynichcf.hcf.map.stats.StatsHandler;
import com.cynichcf.hcf.util.CC;
import lombok.Getter;
import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class LeaderboardHologramsCommand {

    @Getter private Map<Location, StatsTopCommand.StatsObjective> leaderboardHolos = Maps.newHashMap();

    @Command(names = {"leaderboard add holo"}, hidden = true, permission = "op")
    public static void onHoloCreate(Player player, @Param(name = "objective")String objectiveName ) {
        StatsTopCommand.StatsObjective objective;
        try {
            objective = StatsTopCommand.StatsObjective.valueOf(objectiveName);
        } catch (Exception ex) {
            player.sendMessage(CC.translate("&cWrong thing headass"));
            return;
        }

        StatsHandler statsHandler = HCF.getInstance().getMapHandler().getStatsHandler();

        //statsHandler.setupHologram(player.getLocation(), objective);
    }


}
