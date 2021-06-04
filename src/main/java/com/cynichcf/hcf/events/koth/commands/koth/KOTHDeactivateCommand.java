package com.cynichcf.hcf.events.koth.commands.koth;

import com.cheatbreaker.api.CheatBreakerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.cynichcf.hcf.events.Event;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

import java.util.concurrent.TimeUnit;

public class KOTHDeactivateCommand {

    @Command(names={ "KOTH stop", "event deactivate" }, permission="foxtrot.koth.admin")
    public static void kothDectivate(CommandSender sender, @Param(name="koth") Event koth) {
        koth.deactivate();
        sender.sendMessage(ChatColor.GRAY + "Stopped " + koth.getName() + " event.");
    }

}
