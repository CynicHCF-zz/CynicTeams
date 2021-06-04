package com.cynichcf.hcf.team.commands.pvp;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PvPAddLivesCommand {

    @Command(names = {"pvp addlives", "addlives"}, permission = "foxtrot.addlives")
    public static void pvpSetLives(CommandSender sender, @Param(name = "player") UUID player, @Param(name = "life type") String lifeType, @Param(name = "amount") int amount) {
        lifeType = lifeType.toLowerCase();

        if (!lifeType.equalsIgnoreCase("soulbound")
            && !lifeType.equalsIgnoreCase("friend")) {
            sender.sendMessage(ChatColor.RED + "Invalid life type '" + lifeType + "'. Valid types: soulbound, friend");
            return;
        }

        Player target = Bukkit.getPlayer(player);

        switch (lifeType) {
            case "soulbound": {
                HCF.getInstance().getSoulboundLivesMap().addLives(player, amount);
                break;
            }

            case "friend": {
                HCF.getInstance().getFriendLivesMap().addLives(player, amount);
                break;
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.YELLOW + UUIDUtils.name(player) + ChatColor.GREEN + " " + amount + " " + lifeType.toLowerCase() + " lives.");

        String suffix = sender instanceof Player ? " from " + sender.getName() : "";
        if (target != null)
            target.sendMessage(ChatColor.GREEN + "You have received " + amount + " " + lifeType.toLowerCase() + " lives" + suffix);
    }

}
