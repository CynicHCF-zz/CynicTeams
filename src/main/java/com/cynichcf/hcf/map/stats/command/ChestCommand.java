package com.cynichcf.hcf.map.stats.command;

import lombok.Getter;
import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChestCommand {

    @Getter private static Set<UUID> BYPASS = new HashSet<>();

    @Command(names = {"chest", "enderchest", "ec"}, permission = "foxtrot.enderchest")
    public static void chest(Player sender) {
        BYPASS.add(sender.getUniqueId());
        sender.openInventory(sender.getEnderChest());
        BYPASS.remove(sender.getUniqueId());
    }

}
