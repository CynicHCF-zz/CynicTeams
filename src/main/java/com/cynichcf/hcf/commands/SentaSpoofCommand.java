package com.cynichcf.hcf.commands;

import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SentaSpoofCommand {

    static List<String> names = Arrays.asList(
            "James", "John", "Robert", "William", "Michael",
            "David", "Richard", "Joseph", "Thomas", "Charles",
            "Christopher", "Daniel", "Matthew", "Anthony", "Donald",
            "Mark", "Paul", "Steven", "Andrew", "Kenneth",
            "Joshua", "Kevin", "Brian", "George", "Edward",
            "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan",
            "Jacob", "Eric", "Jonathan", "Stephen", "Larry"
    );

    @Command(names = {"senta spoof"}, permission = "me.senta.fuckface")
    public static void senta(Player sender, @Param(name = "amount") int amount) {
        if (sender.isOp()) {
            if (amount > 100000000) {
                sender.sendMessage(ChatColor.RED + "No.");
                return;
            }
            for (int i = 1; i <= amount; i++) {
                Bukkit.broadcastMessage(("§e" + names.get(new Random().nextInt(names.size())) + " has joined the server."));
            }
        }
    }
}