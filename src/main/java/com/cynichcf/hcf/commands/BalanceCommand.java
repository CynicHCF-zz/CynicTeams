package com.cynichcf.hcf.commands;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.framework.economy.FrozenEconomyHandler;
import rip.lazze.libraries.uuid.FrozenUUIDCache;

public class BalanceCommand {

    @Command(names={ "Balance", "Econ", "Bal", "$" }, permission="")
    public static void balance(Player sender, @Param(name="player", defaultValue="self") UUID player) {
        if (sender.getUniqueId().equals(player)) {
            sender.sendMessage(ChatColor.GOLD + "Balance: " + ChatColor.WHITE + NumberFormat.getNumberInstance(Locale.US).format(FrozenEconomyHandler.getBalance(sender.getUniqueId())));
        } else {
            sender.sendMessage(ChatColor.GOLD + "Balance of " + FrozenUUIDCache.name(player) + ": " + ChatColor.WHITE + NumberFormat.getNumberInstance(Locale.US).format(FrozenEconomyHandler.getBalance(player)));
        }
    }

}