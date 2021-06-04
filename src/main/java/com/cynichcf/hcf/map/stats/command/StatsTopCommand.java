package com.cynichcf.hcf.map.stats.command;

import com.cynichcf.hcf.map.stats.StatsEntry;
import lombok.Getter;
import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class StatsTopCommand {

    @Command(names = {"statstop", "leaderboards"}, permission = "")
    public static void statstop(CommandSender sender, @Param(name = "objective", defaultValue = "kills") StatsObjective objective) {
        sender.sendMessage(ChatColor.RED.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 53));
        sender.sendMessage(ChatColor.YELLOW + "Leaderboards for: " + ChatColor.RED + objective.getName());
        sender.sendMessage(ChatColor.RED.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 53));

        int index = 0;
        for (Map.Entry<StatsEntry, String> entry : HCF.getInstance().getMapHandler().getStatsHandler().getLeaderboards(objective, 10).entrySet()) {
            index++;
            sender.sendMessage((index == 1 ? ChatColor.RED + "1 " : ChatColor.YELLOW.toString() + index + " ") + ChatColor.YELLOW.toString() + (objective == StatsObjective.TOP_FACTION ? entry.getKey().getFaction() : UUIDUtils.name(entry.getKey().getOwner())) + ": " + ChatColor.RED + entry.getValue());
        }

        sender.sendMessage(ChatColor.RED.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 53));
    }

    @Getter
    public enum StatsObjective {

        KILLS("Kills", "k"),
        DEATHS("Deaths", "d"),
        KD("KD", "kdr"),
        TOP_FACTION("Top Factions", "topfaction", "tf"),
        HIGHEST_KILLSTREAK("Highest Killstreak", "killstreak", "highestkillstreak", "ks", "highestks", "hks");

        private String name;
        private String[] aliases;

        StatsObjective(String name, String... aliases) {
            this.name = name;
            this.aliases = aliases;
        }

    }

}
