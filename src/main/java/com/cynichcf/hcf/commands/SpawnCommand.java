package com.cynichcf.hcf.commands;


import org.bukkit.entity.Player;
import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;

public class SpawnCommand {


    @Command(names={ "spawn" }, permission="foxtrot.spawn")
    public static void spawn(Player sender) {
        Player player = (Player) sender;
        if (sender.hasPermission("foxtrot.spawn")) {
            sender.teleport(HCF.getInstance().getServerHandler().getSpawnLocation());
        } else {
            HCF.getInstance().getServerHandler().startSpawnteleport(sender);
        }
    }
}
