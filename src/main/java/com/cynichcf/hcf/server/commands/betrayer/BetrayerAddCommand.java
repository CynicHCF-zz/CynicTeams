package com.cynichcf.hcf.server.commands.betrayer;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.Betrayer;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class BetrayerAddCommand {

    @Command(names = {"betrayer add"}, permission = "op")
    public static void betrayerAdd(Player sender, @Param(name = "player") UUID player, @Param(name = "reason", wildcard=true) String reason) {
        if (HCF.getInstance().getServerHandler().getBetrayer(player) == null) {
            Betrayer betrayer = new Betrayer(player, sender.getUniqueId(), reason);
            HCF.getInstance().getServerHandler().getBetrayers().add(betrayer);
            HCF.getInstance().getServerHandler().save();

            sender.sendMessage(GREEN + "Added " + RED + UUIDUtils.name(player) + GREEN + "'s betrayer tag for " + YELLOW +  reason + GREEN + ".");
        } else {
            sender.sendMessage(RED + UUIDUtils.name(player) + " is already a betrayer.");
        }
    }

}