package com.cynichcf.hcf.team.commands.pvp;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class PvPLivesCommand {

    @Command(names={ "pvptimer lives", "pvp lives" }, permission="")
    public static void pvpLives(CommandSender sender, @Param(name="player", defaultValue="self") UUID player) {
        String name = UUIDUtils.name(player);

        sender.sendMessage(ChatColor.GOLD + name + "'s Soulbound Lives: " + ChatColor.WHITE + HCF.getInstance().getSoulboundLivesMap().getLives(player));
        sender.sendMessage(ChatColor.GOLD + name + "'s Friend Lives: " + ChatColor.WHITE + HCF.getInstance().getFriendLivesMap().getLives(player));
    }

}