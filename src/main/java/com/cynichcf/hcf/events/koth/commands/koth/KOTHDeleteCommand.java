package com.cynichcf.hcf.events.koth.commands.koth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.Event;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class KOTHDeleteCommand {

    @Command(names={ "KOTH Delete", "events delete", "event delete" }, permission="foxtrot.koth.admin")
    public static void kothDelete(Player sender, @Param(name="koth") Event koth) {
        HCF.getInstance().getEventHandler().getEvents().remove(koth);
        HCF.getInstance().getEventHandler().saveEvents();
        sender.sendMessage(ChatColor.GRAY + "Deleted event " + koth.getName() + ".");
    }

}