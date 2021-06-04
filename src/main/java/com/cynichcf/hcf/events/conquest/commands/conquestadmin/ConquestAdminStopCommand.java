package com.cynichcf.hcf.events.conquest.commands.conquestadmin;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.conquest.game.ConquestGame;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ConquestAdminStopCommand {

    @Command(names={ "conquestadmin stop" }, permission="op")
    public static void conquestAdminStop(CommandSender sender) {
        ConquestGame game = HCF.getInstance().getConquestHandler().getGame();

        if (game == null) {
            sender.sendMessage(ChatColor.RED + "Conquest is not active.");
            return;
        }

        game.endGame(null);
    }

}