package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.splitedroad.SplitedRoadProcessor;
import com.cynichcf.hcf.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RoadCommand {

    @Command(names = {"roadsplit"}, permission = "foxtrot.owner")
    public static void generator(Player sender) {
        new SplitedRoadProcessor(HCF.getInstance(), sender.getWorld().getSpawnLocation(), 15000, 1).run();
        sender.sendMessage(CC.translate("&aroads have begun to be generated."));
    }
}