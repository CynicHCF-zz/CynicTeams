package com.cynichcf.hcf.server.commands.betrayer;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.Betrayer;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.bukkit.ChatColor.*;
import static rip.lazze.libraries.util.UUIDUtils.name;

public class BetrayerInfoCommand {

    private static SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yy HH:mm:ss z");

    @Command(names = {"betrayer info"}, permission = "")
    public static void betrayerList(Player sender, @Param(name = "player") UUID player) {
        Betrayer betrayer = HCF.getInstance().getServerHandler().getBetrayer(player);

        if (betrayer != null) {
            sender.sendMessage(GOLD + "=====" + WHITE + " Betrayer Information " + GOLD + "=====");
            sender.sendMessage(GOLD + "Betrayer: " + LIGHT_PURPLE + name(betrayer.getUuid()) + GOLD + " Added by: " + LIGHT_PURPLE + name(betrayer.getAddedBy()));
            sender.sendMessage(GOLD + "When: " + LIGHT_PURPLE + sdf.format(new Date(betrayer.getTime())) + GOLD + " Why: " + LIGHT_PURPLE + betrayer.getReason());
        } else {
            sender.sendMessage(RED + "Could not find betrayer info for " + YELLOW + name(player) + RED + "!");
        }
    }
}
