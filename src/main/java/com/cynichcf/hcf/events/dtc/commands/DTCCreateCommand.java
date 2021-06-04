package com.cynichcf.hcf.events.dtc.commands;

import com.cynichcf.hcf.events.dtc.DTC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class DTCCreateCommand {

    @Command(names={ "DTC Create" }, permission="foxtrot.dtc.admin")
    public static void kothCreate(Player sender, @Param(name="dtc") String koth) {
        new DTC(koth, sender.getLocation());
        sender.sendMessage(ChatColor.GRAY + "Created a DTC named " + koth + ".");
    }

}
