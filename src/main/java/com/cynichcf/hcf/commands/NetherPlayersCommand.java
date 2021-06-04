package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;

public class NetherPlayersCommand {

    @Command(names={ "netherplayers" }, permission="")
    public static void NetherPlayers(Player player) {
        player.sendMessage(CC.translate("&9&m-------------------------------------------------"));
        player.sendMessage(CC.translate("&cThere are currently %players% players in the nether world!".replace("%players%", Bukkit.getWorld("world_nether").getPlayers().size() + "")));
        player.sendMessage(CC.translate("&9&m-------------------------------------------------"));
    }
}

