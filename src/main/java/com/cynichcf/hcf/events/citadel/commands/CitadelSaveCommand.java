package com.cynichcf.hcf.events.citadel.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.citadel.CitadelHandler;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CitadelSaveCommand {

    @Command(names={"citadel save"}, permission="op")
    public static void citadelSave(Player sender) {
        HCF.getInstance().getCitadelHandler().saveCitadelInfo();
        sender.sendMessage(CitadelHandler.PREFIX + " " + ChatColor.YELLOW + "Saved Citadel info to file.");
    }

}