package com.cynichcf.hcf.server.conditional.staff;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class StaffCommands {

    @Command(names = {"hidestaff", "showstaff"}, permission = "foxtrot.staff")
    public static void hidestaff(Player sender) {
        if (sender.hasMetadata("hidestaff")){
            sender.sendMessage(ChatColor.GREEN + "Successfully shown staff!");
            sender.removeMetadata("hidestaff", HCF.getInstance());
            for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()){
                // cant stack
                if (otherPlayer != sender){
                    if (otherPlayer.hasMetadata("modmode")){
                        sender.showPlayer(otherPlayer);
                    }
                }
            }
        } else {
            sender.setMetadata("hidestaff", new FixedMetadataValue(HCF.getInstance(), true));
            sender.sendMessage(ChatColor.GREEN + "Successfully hidden staff!");
            for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()){
                // cant stack them
                if (otherPlayer != sender){
                    if (otherPlayer.hasMetadata("modmode")){
                        sender.hidePlayer(otherPlayer);
                    }
                }
            }
        }
    }

}