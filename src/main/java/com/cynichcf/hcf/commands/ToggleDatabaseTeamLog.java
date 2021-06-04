package com.cynichcf.hcf.commands;

import org.bukkit.entity.Player;

import com.cynichcf.hcf.team.track.TeamActionTracker;
import rip.lazze.libraries.command.Command;

public class ToggleDatabaseTeamLog {

    @Command(names = {"toggledatabaseteamlog" }, permission = "op")
    public static void toggleDatabaseTeamLog(Player sender) {
        TeamActionTracker.setDatabaseLogEnabled(!TeamActionTracker.isDatabaseLogEnabled());
        sender.sendMessage("Enabled: " + TeamActionTracker.isDatabaseLogEnabled());
    }

}