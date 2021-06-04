package com.cynichcf.hcf.server.commands.betrayer;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.Betrayer;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BetrayerRemoveCommand {

    @Command(names = {"betrayer remove"}, permission = "op")
    public static void betrayerRemove(Player sender, @Param(name = "player") UUID player) {
        Betrayer betrayer = HCF.getInstance().getServerHandler().getBetrayer(player);
        if (betrayer != null) {
            HCF.getInstance().getServerHandler().getBetrayers().remove(betrayer);
            HCF.getInstance().getServerHandler().save();

            sender.sendMessage(ChatColor.GREEN + "Removed " + UUIDUtils.name(player) + "'s betrayer tag.");
        } else {
            sender.sendMessage(ChatColor.RED + UUIDUtils.name(player) + " isn't a betrayer.");
        }
    }

}