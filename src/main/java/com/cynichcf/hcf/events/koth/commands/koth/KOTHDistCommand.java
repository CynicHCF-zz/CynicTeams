package com.cynichcf.hcf.events.koth.commands.koth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.events.Event;
import com.cynichcf.hcf.events.EventType;
import com.cynichcf.hcf.events.koth.KOTH;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class KOTHDistCommand {

    @Command(names={ "KOTH Dist" }, permission="foxtrot.koth.admin")
    public static void kothDist(Player sender, @Param(name="koth") Event koth, @Param(name="distance") int distance) {
        if (koth.getType() != EventType.KOTH) {
            sender.sendMessage(ChatColor.RED + "Can only set distance for KOTHs");
            return;
        }

        ((KOTH) koth).setCapDistance(distance);
        sender.sendMessage(ChatColor.GRAY + "Set max distance for the " + koth.getName() + " KOTH.");
    }

}