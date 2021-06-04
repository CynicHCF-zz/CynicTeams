package com.cynichcf.hcf.commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class GoppleResetCommand {

    @Command(names={ "GoppleReset" }, permission="foxtrot.gopplereset")
    public static void goppleReset(Player sender, @Param(name="player") UUID player) {
        HCF.getInstance().getOppleMap().resetCooldown(player);
        sender.sendMessage(ChatColor.RED + "Cooldown reset!");
    }

}