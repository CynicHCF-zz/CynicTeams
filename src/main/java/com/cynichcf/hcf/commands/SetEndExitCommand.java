package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.util.Utils;
import org.bukkit.entity.Player;

import rip.lazze.libraries.command.Command;

public class SetEndExitCommand {

    @Command(names = {"setendexit"}, permission = "op")
    public static void setendexit(Player sender) {
        HCF.getInstance().getConfig().set("Locations.end_exit", Utils.stringifyLocation(sender.getLocation()));
        HCF.getInstance().saveConfig();
        sender.sendMessage(CC.translate("&aSuccessfully created endexit."));
    }
}

