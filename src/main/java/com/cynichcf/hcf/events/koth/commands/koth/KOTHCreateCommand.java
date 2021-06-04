package com.cynichcf.hcf.events.koth.commands.koth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.events.koth.KOTH;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class KOTHCreateCommand {

    @Command(names={ "KOTH Create" }, permission="foxtrot.koth.admin")
    public static void kothCreate(Player sender, @Param(name="koth") String koth) {
        new KOTH(koth, sender.getLocation());
        sender.sendMessage(ChatColor.GRAY + "Created a KOTH named " + koth + ".");
    }

}