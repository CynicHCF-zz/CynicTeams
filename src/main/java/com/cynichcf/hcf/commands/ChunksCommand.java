package com.cynichcf.hcf.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import rip.lazze.libraries.command.Command;

public class ChunksCommand {

    @Command(names={"chunks"}, permission="op")
    public static void chunks(CommandSender sender) {
        sender.sendMessage((Object)ChatColor.GREEN + "Loaded chunks per world:");
        for (World world : Bukkit.getWorlds()) {
            sender.sendMessage((Object)ChatColor.YELLOW + world.getName() + ": " + (Object)ChatColor.RED + world.getLoadedChunks().length);
        }
    }
}

