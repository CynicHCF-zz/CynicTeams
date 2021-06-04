package com.cynichcf.hcf.commands;

import org.bukkit.entity.Player;

import com.cynichcf.hcf.server.SpawnTagHandler;
import rip.lazze.libraries.command.Command;

public class TagMeCommand {

    @Command(names={ "tagme" }, permission="op")
    public static void tagMe(Player sender) {
        SpawnTagHandler.addOffensiveSeconds(sender, SpawnTagHandler.getMaxTagTime());
    }

}