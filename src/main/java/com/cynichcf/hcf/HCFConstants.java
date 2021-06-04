package com.cynichcf.hcf;

import com.cynichcf.hcf.team.commands.team.TeamTopCommand;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.team.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HCFConstants {
    public static String teamChatFormat(Player player, String message) {
        return ChatColor.DARK_AQUA + "(Team) " + player.getName() + ": " + ChatColor.YELLOW + message;
    }

    public static String officerChatFormat(Player player, String message) {
        return ChatColor.LIGHT_PURPLE + "(Officer) " + player.getName() + ": " + ChatColor.YELLOW + message;
    }

    public static String teamChatSpyFormat(Team team, Player player, String message) {
        return ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "TC: " + ChatColor.YELLOW + team.getName() + ChatColor.GOLD + "]" + ChatColor.DARK_AQUA + player.getName() + ": " + message;
    }

    public static String allyChatFormat(Player player, String message) {
        return Team.ALLY_COLOR + "(Ally) " + player.getName() + ": " + ChatColor.YELLOW + message;
    }

    public static String allyChatSpyFormat(Team team, Player player, String message) {
        return ChatColor.GOLD + "[" + Team.ALLY_COLOR + "AC: " + ChatColor.YELLOW + team.getName() + ChatColor.GOLD + "]" + Team.ALLY_COLOR + player.getName() + ": " + message;
    }

    public static String publicChatFormat(Team team, String rankPrefix, String customPrefixString) {
        String starting = CC.translate("&6[&c*&6] ");
        //CC.translate(Foxtrot.getInstance().getConfig().getString("nonefaction"));
        if (team != null) {
            starting = ChatColor.GOLD + "[" + HCF.getInstance().getServerHandler().getDefaultRelationColor() + team.getName() + ChatColor.GOLD + "] ";
                    //CC.translate(Foxtrot.getInstance().getConfig().getString("factionprefix").replace("%fac%",Foxtrot.getInstance().getServerHandler().getDefaultRelationColor() + team.getName()));

        }
        return starting + customPrefixString + rankPrefix + ChatColor.WHITE + "%s" + ChatColor.WHITE + ": %s";
    }

    public static String publicChatFormatTwoPointOhBaby(Team team, String rankPrefix, String customPrefixString) {
        String starting = "";
        if (team != null) {
            if (((TeamTopCommand.getSortedTeams().entrySet().iterator().next()).getKey()).equals(team)) {
                starting = ChatColor.YELLOW + "[" + ChatColor.GOLD + team.getName() + ChatColor.YELLOW + "]";
                return starting + customPrefixString + rankPrefix + ChatColor.WHITE + "%s" + ChatColor.WHITE + ": %s";
            }
            starting = ChatColor.GOLD + "[" + HCF.getInstance().getServerHandler().getDefaultRelationColor() + team.getName() + ChatColor.GOLD + "]";
        }
        return starting + customPrefixString + rankPrefix + ChatColor.WHITE + "%s" + ChatColor.WHITE + ": %s";
    }
}
