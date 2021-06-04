package com.cynichcf.hcf.map.deathban.purgatory;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.util.ItemUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PurgatoryCommands {

    @Command(names = {"purgatory"}, permission = "")
    public static void purgatoryCommand(Player player) {
        if (!HCF.getInstance().getMapHandler().isPurgatory()) {
            player.sendMessage("§cThis command can only be used if Purgatory is enabled.");
            return;
        }

        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are not deathbanned!");
            return;
        }

        player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 35));
        player.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Purgatory Price Map");
        player.sendMessage("");
        for (Map.Entry<Material, Integer> entry : HCF.getInstance().getMapHandler().getPurgatoryHandler().getPriceMap().entrySet()) {
            player.sendMessage(ChatColor.RED + ItemUtils.getName(new ItemStack(entry.getKey())) + ChatColor.YELLOW + " is worth " + ChatColor.RED + entry.getValue() + " second" + (entry.getValue() > 1 ? "s" : ""));
        }
        player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 35));
    }

    @Command(names = {"setpurgatoryspawn"}, permission = "op")
    public static void setPurgatorySpawn(Player player) {
        if (!HCF.getInstance().getMapHandler().isPurgatory()) {
            player.sendMessage("§cThis command can only be used if Purgatory is enabled.");
            return;
        }

        HCF.getInstance().getMapHandler().getPurgatoryHandler().setPurgatoryLocation(player.getLocation());
        HCF.getInstance().getMapHandler().getPurgatoryHandler().save();

        player.sendMessage(ChatColor.GREEN + "Purgatory spawn updated.");
    }

}