package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.CC;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class TeleportAllCommand {

    @Command(names={ "tpall" }, permission="foxtrot.tpallcommand")
    public static void teleportall(Player sender) {
        if (sender.getGameMode() != GameMode.CREATIVE) {
            sender.sendMessage(ChatColor.RED + "This command must be ran in creative.");
            return;
            }
            for (Player onlinePlayer : HCF.getInstance().getServer().getOnlinePlayers()) {
                onlinePlayer.teleport(sender.getLocation());
                onlinePlayer.sendMessage(ChatColor.YELLOW + "All players been teleported to " + CC.translate(sender.getDisplayName()));
            }
    }
}
