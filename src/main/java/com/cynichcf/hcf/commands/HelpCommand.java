package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand {

    @Command(names={ "Help" }, permission="")
    public static void help(Player sender) {

        String networkName = ChatColor.translateAlternateColorCodes('&', HCF.getInstance().getServerHandler().getNetworkName());
        String serverName = HCF.getInstance().getServerHandler().getServerName();
        String serverWebsite = HCF.getInstance().getServerHandler().getNetworkWebsite();

        String sectionColor = ChatColor.translateAlternateColorCodes('&', HCF.getInstance().getServerHandler().getTabSectionColor());
        String infoColor = ChatColor.translateAlternateColorCodes('&', HCF.getInstance().getServerHandler().getTabInfoColor());

        if (sectionColor.equalsIgnoreCase(infoColor))
            infoColor = "§r";

        sender.sendMessage(new String[] {

                "§7§m-----------------------------------------------------",
                networkName + " §7❘ §r" + serverName,
                "",

                sectionColor + "Map Kit: " + infoColor + "Prot 1, Sharp 1",
                sectionColor + "Teams: " + infoColor + HCF.getInstance().getMapHandler().getTeamSize() + " §7(" + HCF.getInstance().getMapHandler().getAllyLimit() + " allies)",
                "",

                sectionColor + "Helpful Commands:",
                infoColor + "§7/report <player> <reason> - " + infoColor + "Report Players",
                infoColor + "§7/request <message> - " + infoColor + "Request Staff Assistance",
                infoColor + "§7/tgc - " + infoColor + "Toggle Chat Visibility",
                "",

                sectionColor + "§7TeamSpeak - " + infoColor + HCF.getInstance().getConfig().getString("teamspeak"),
                sectionColor + "§7Discord - " + infoColor + HCF.getInstance().getConfig().getString("discord"),
                sectionColor + "§7Store - " + infoColor + HCF.getInstance().getConfig().getString("store"),
                sectionColor + "§c§lCheatBreaker: " + "discord.oldcheatbreaker.com",
                "§7§m-----------------------------------------------------",

        });
    }

}
