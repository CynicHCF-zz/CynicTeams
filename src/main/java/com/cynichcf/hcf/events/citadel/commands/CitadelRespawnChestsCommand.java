package com.cynichcf.hcf.events.citadel.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.citadel.CitadelHandler;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CitadelRespawnChestsCommand {

    @Command(names={"citadel respawnchests"}, permission="op")
    public static void citadelRespawnChests(Player sender) {
        HCF.getInstance().getCitadelHandler().respawnCitadelChests();
        sender.sendMessage(CitadelHandler.PREFIX + " " + ChatColor.YELLOW + "Respawned all Citadel chests.");
    }

}