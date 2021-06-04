package com.cynichcf.hcf.commands;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cynichcf.hcf.util.CC;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;

public class StaffModules {

    @Command(names = {"staffmodules", "staffmods", "sm"}, permission = "foxtrot.staff")
    public static void staffmodules(Player sender) {
        sender.sendMessage(CC.translate("&cCorrect usage: /staffmodules <true/false>"));
    }
    @Command(names = {"staffmodules true", "staffmods true", "sm true"}, permission = "foxtrot.staff")
    public static void staffmodulestrue(Player sender) {
        CheatBreakerAPI.getInstance().giveAllStaffModules(sender);
        sender.sendMessage(CC.translate("&aStaff Modules has been enabled!"));
        }

    @Command(names = {"staffmodules false", "staffmods true", "sm true"}, permission = "foxtrot.staff")
    public static void staffmodulesfalse(Player sender) {
        CheatBreakerAPI.getInstance().disableAllStaffModules(sender);
        sender.sendMessage(CC.translate("&cStaff Modules has been disabled!"));
    }
}


