package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.listener.EndListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rip.lazze.libraries.command.Command;

public class ToggleEndCommand {

    @Command(names={ "ToggleEnd" }, permission="foxtrot.toggleend")
    public static void toggleEnd(Player sender) {
        EndListener.endActive = !EndListener.endActive;
        sender.sendMessage(ChatColor.YELLOW + "End enabled? " + ChatColor.LIGHT_PURPLE + (EndListener.endActive ? "Yes" : "No"));
    }

}