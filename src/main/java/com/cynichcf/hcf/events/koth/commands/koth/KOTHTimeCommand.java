package com.cynichcf.hcf.events.koth.commands.koth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.events.Event;
import com.cynichcf.hcf.events.EventType;
import com.cynichcf.hcf.events.koth.KOTH;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class KOTHTimeCommand {

    @Command(names={ "KOTH Time" }, permission="foxtrot.koth.admin")
    public static void kothTime(Player sender, @Param(name="koth") Event koth, @Param(name="time") float time) {
        if (time > 20F) {
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "This command was changed! The time parameter is now in minutes, not seconds. For example, to set a KOTH's capture time to 20 minutes 30 seconds, use /koth time 20.5");
        }

        if (koth.getType() != EventType.KOTH) {
            sender.sendMessage(ChatColor.RED + "Unable to modify cap time for a non-KOTH event.");
        } else {
            ((KOTH) koth).setCapTime((int) (time * 60F));
            sender.sendMessage(ChatColor.GRAY + "Set cap time for the " + koth.getName() + " KOTH.");
        }
    }

}