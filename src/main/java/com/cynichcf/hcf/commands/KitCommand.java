package com.cynichcf.hcf.commands;

import org.bukkit.entity.Player;

import rip.lazze.libraries.command.Command;

public class KitCommand {

    @Command(names = "kit, mapkit", permission = "")
    public static void kit(Player sender) {
        String sharp = "Sharpness 1";
        String prot = "Protection 1";
        String bow = "Power 3";
        sender.sendMessage("§eEnchant Limits: §7" + prot + ", " + sharp + ", " + bow);
    }
}
