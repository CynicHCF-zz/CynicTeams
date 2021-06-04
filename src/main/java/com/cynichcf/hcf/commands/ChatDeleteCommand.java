package com.cynichcf.hcf.commands;


import com.cynichcf.hcf.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;

public class ChatDeleteCommand {

    private static String[] CLEAR_MESSAGE = new String[101];

    @Command(names={ "chatdelete" }, permission="foxtrot.staff")
    public static void chatdelete(Player sender) {
        for(Player padre : Bukkit.getOnlinePlayers()){
        padre.sendMessage(CLEAR_MESSAGE);
        padre.sendMessage(CC.translate("&eYou have cleared chat by " + sender.getName()));
        }

    }

}
