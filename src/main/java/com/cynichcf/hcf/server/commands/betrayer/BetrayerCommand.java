package com.cynichcf.hcf.server.commands.betrayer;

import rip.lazze.libraries.command.Command;
import org.bukkit.entity.Player;

public class BetrayerCommand {

    @Command(names = {"betrayer"}, permission = "op")
    public static void betrayer(Player sender) {
        String[] msges = {
                "§c/betrayer list - Shows all betrayers.",
                "§c/betrayer add <player> <reason> - Add a betrayer for a reason.",
                "§c/betrayer remove <player> - Remove a betrayer."};

        sender.sendMessage(msges);
    }

}