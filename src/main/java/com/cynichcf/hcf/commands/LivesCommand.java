package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;


public class LivesCommand {

    @Command(names={ "lives" }, permission="")
    public static void lives(CommandSender commandSender) {
        
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Bad console.");
            return;
        }

        Player sender = (Player) commandSender;
        
        int shared = HCF.getInstance().getFriendLivesMap().getLives(sender.getUniqueId());
        int soulbound = HCF.getInstance().getSoulboundLivesMap().getLives(sender.getUniqueId());
        sender.sendMessage(ChatColor.YELLOW + "Lives are used to revive you instantly upon death. You can purchase more lives at: " + ChatColor.RED + "http://" + HCF.getInstance().getServerHandler().getNetworkWebsite() + "/store");
        sender.sendMessage(ChatColor.YELLOW + "Friend Lives: " + ChatColor.RED + shared);
        sender.sendMessage(ChatColor.YELLOW + "Soulbound Lives: " + ChatColor.RED + soulbound);
        sender.sendMessage(ChatColor.RED + "You cannot revive other players with soulbound lives.");
    }
}
