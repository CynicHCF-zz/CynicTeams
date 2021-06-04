package com.cynichcf.hcf.commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.framework.economy.FrozenEconomyHandler;
import rip.lazze.libraries.util.UUIDUtils;

public class EcoCheckCommand {

    @Command(names={ "ecocheck" }, permission="op")
    public static void ecoCheck(Player sender) {
        if (sender.getGameMode() != GameMode.CREATIVE) {
            sender.sendMessage(ChatColor.RED + "This command must be ran in creative.");
            return;
        }

        for (Team team : HCF.getInstance().getTeamHandler().getTeams()) {
            if (isBad(team.getBalance())) {
                sender.sendMessage(ChatColor.YELLOW + "Team: " + ChatColor.WHITE + team.getName());
            }
        }

        try {
            Map<UUID, Double> balances = FrozenEconomyHandler.getBalances();

            for (Map.Entry<UUID, Double> balanceEntry  : balances.entrySet()) {
                if (isBad(balanceEntry.getValue())) {
                    sender.sendMessage(ChatColor.YELLOW + "Player: " + ChatColor.WHITE + UUIDUtils.name(balanceEntry.getKey()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isBad(double bal) {
        return (Double.isNaN(bal) || Double.isInfinite(bal) || bal > 1_000_000D);
    }

}