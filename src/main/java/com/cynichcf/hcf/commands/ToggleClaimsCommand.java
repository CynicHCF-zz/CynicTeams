package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.team.claims.LandBoard;
import rip.lazze.libraries.command.Command;

public class ToggleClaimsCommand {

    @Command(names={ "ToggleClaims" }, permission="op")
    public static void toggleClaims(Player sender) {
        LandBoard.getInstance().setClaimsEnabled(!LandBoard.getInstance().isClaimsEnabled());
        sender.sendMessage(ChatColor.YELLOW + "Claims enabled? " + ChatColor.LIGHT_PURPLE + (LandBoard.getInstance().isClaimsEnabled() ? "Yes" : "No"));
    }

}