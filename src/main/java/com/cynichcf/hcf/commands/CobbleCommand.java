package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;

public class CobbleCommand {

    @Command(names = {"cobble", "cobblestone"}, permission = "")
    public static void cobble(Player sender) {
        boolean val = !HCF.getInstance().getCobblePickupMap().isCobblePickup(sender.getUniqueId());

        sender.sendMessage(ChatColor.YELLOW + "You are now " + (!val ? ChatColor.RED + "unable" : ChatColor.GREEN + "able") + ChatColor.YELLOW + " to pick up cobblestone while in Miner class!");
        HCF.getInstance().getCobblePickupMap().setCobblePickup(sender.getUniqueId(), val);
    }

}
