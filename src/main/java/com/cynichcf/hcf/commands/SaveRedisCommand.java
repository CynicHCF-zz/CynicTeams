package com.cynichcf.hcf.commands;

import org.bukkit.command.CommandSender;

import com.cynichcf.hcf.persist.RedisSaveTask;
import rip.lazze.libraries.command.Command;

public class SaveRedisCommand {

    @Command(names = {"SaveRedis", "Save"}, permission = "op")
    public static void saveRedis(CommandSender sender) {
        RedisSaveTask.save(sender, false);
    }

    @Command(names = {"SaveRedis ForceAll", "Save ForceAll"}, permission = "op")
    public static void saveRedisForceAll(CommandSender sender) {
        RedisSaveTask.save(sender, true);
    }

}