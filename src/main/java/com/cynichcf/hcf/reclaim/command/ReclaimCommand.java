package com.cynichcf.hcf.reclaim.command;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.reclaim.Group;
import com.cynichcf.hcf.util.CC;
import me.activated.core.plugin.AquaCoreAPI;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReclaimCommand {

    @Command(names = {"reclaim", "claim", "keys"}, permission = "")
    public static void reclaim(CommandSender sender){
        Player p = (Player) sender;
        String group = AquaCoreAPI.INSTANCE.getPlayerRank(p.getUniqueId()).getName();

        boolean needsReclaim = false;

        for (Group definedGroup : HCF.getInstance().getReclaimHandler().getGroups()){
            if (definedGroup.getName().equalsIgnoreCase(group)){
                needsReclaim = true;
            }
        }

        if (needsReclaim){

            if (HCF.getInstance().getReclaimHandler().hasReclaimed(p)){
                p.sendMessage(CC.translate(CC.prefix + "&c You have already reclaimed this map!"));
                return;
            }

            List<String> commands = new ArrayList<>();

            for (Group definedGroup : HCF.getInstance().getReclaimHandler().getGroups()){
                if (definedGroup.getName().equalsIgnoreCase(group)){
                    for (String command1 : definedGroup.getCommands()){
                        commands.add(command1);
                    }
                }
            }

            for (String c : commands){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", p.getName()));
            }

            HCF.getInstance().getReclaimHandler().setUsedReclaim(p, true);
        } else {
            p.sendMessage(CC.translate(CC.prefix + "&c You are not eligible to reclaim!"));
        }
    }

    @Command(names = {"reclaimreset", "claimreset"}, permission = "foxtrot.reclaim.reset")
    public static void reclaimReset(CommandSender sender, @Param(name = "player") Player target){
        Player player = (Player) sender;
        sender.sendMessage(CC.translate(CC.prefix + " " + player.getDisplayName() + "&f reclaim has been reset and now can use their reclaim."));
        HCF.getInstance().getReclaimHandler().setUsedReclaim(target, false);
    }
}
