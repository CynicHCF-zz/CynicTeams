package com.cynichcf.hcf.events.koth.commands.kothschedule;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KOTHReloadSchedule {

    @Command(names={ "KOTHSchedule Reload" }, permission = "foxtrot.koth.admin")
    public static void kothScheduleReload(Player sender) {
        HCF.getInstance().getEventHandler().loadSchedules();
        sender.sendMessage(ChatColor.GOLD + "[KingOfTheHill] " + ChatColor.YELLOW + "Reloaded the KOTH schedule.");
    }

}