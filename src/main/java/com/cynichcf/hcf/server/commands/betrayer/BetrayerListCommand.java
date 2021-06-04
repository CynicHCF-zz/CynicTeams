package com.cynichcf.hcf.server.commands.betrayer;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.Betrayer;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BetrayerListCommand {

    @Command(names = {"betrayer list", "betrayers"}, permission = "")
    public static void betrayerList(Player sender) {
        StringBuilder betrayers = new StringBuilder();

        for (Betrayer betrayer : HCF.getInstance().getServerHandler().getBetrayers()) {
            betrayers.append(ChatColor.GRAY).append(UUIDUtils.name(betrayer.getUuid())).append(ChatColor.GOLD).append(", ");
        }

        if (betrayers.length() > 2) {
            betrayers.setLength(betrayers.length() - 2);
        }

        sender.sendMessage(ChatColor.GOLD + "Betrayers: " + betrayers.toString());
    }

}