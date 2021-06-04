package com.cynichcf.hcf.events.koth.commands.kothschedule;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;

public class KOTHDisableSchedule {

    @Command(names = "KOTHSchedule Disable", permission = "foxtrot.koth.admin")
    public static void kothScheduleDisable(CommandSender sender) {
        HCF.getInstance().getEventHandler().setScheduleEnabled(false);

        sender.sendMessage(ChatColor.YELLOW + "The KOTH schedule has been " + ChatColor.RED + "disabled" + ChatColor.YELLOW + ".");
    }

}
