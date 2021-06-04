package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;

public class EndPlayersCommand {

    @Command(names={ "endplayers" }, permission="")
    public static void EndPlayers(Player player) {
        player.sendMessage(CC.translate("&9&m-------------------------------------------------"));
        player.sendMessage(CC.translate("&cThere are currently %players% players in the end world!".replace("%players%", Bukkit.getWorld("world_end").getPlayers().size() + "")));
        player.sendMessage(CC.translate("&9&m-------------------------------------------------"));

    }
}

