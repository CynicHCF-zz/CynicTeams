package com.cynichcf.hcf.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

import lombok.Getter;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.TimeUtils;

public class CustomTimerCreateCommand {

    @Getter private static Map<String, Long> customTimers = new HashMap<>();
    private static Set<UUID> sotwEnabled = Sets.newHashSet();

    @Command(names={ "customtimer create" }, permission="foxtrot.customtimer")
    public static void customTimerCreate(CommandSender sender, @Param(name="time") int time, @Param(name="title", wildcard=true) String title) {
        if (time == 0) {
            customTimers.remove(title);
        } else {
            customTimers.put(title, System.currentTimeMillis() + (time * 1000));
        }
    }

    @Command(names ={ "customtimer stop"}, permission="foxtrot.customtimer")
    public static void customTimerStop(CommandSender sender, @Param(name="title", wildcard=true) String title) {
       Long removed = customTimers.remove(title);
        if (removed != null && System.currentTimeMillis() < removed){
            sender.sendMessage(ChatColor.GREEN + "Stopped the Customtimer");
            return;
        }

    }

    @Command(names = {"sale start"}, permission = "op")
    public static void startSale(Player sender, @Param(name = "time") String time){
        int seconds = TimeUtils.parseTime(time);
        if (seconds < 0){
            sender.sendMessage(ChatColor.RED + "Invalid time!");
            return;
        }

        customTimers.put("&4&lSale", System.currentTimeMillis() + (seconds * 1000));
        sender.sendMessage(ChatColor.GREEN + "Started the Sale timer for " + time);

    }

    @Command(names = {"sale stop"}, permission = "op")
    public static void stopSale(CommandSender sender){
        Long removed = customTimers.remove("&4&lSale");
        if (removed != null && System.currentTimeMillis() < removed){
            sender.sendMessage(ChatColor.GREEN + "Stopped the sale timer");
            return;
        }

        sender.sendMessage(ChatColor.RED + "Sale is not active");
    }

    @Command(names = {"sotw enable"}, permission = "")
    public static void sotwEnable(Player sender) {
        if (!isSOTWTimer()) {
            sender.sendMessage(ChatColor.RED + "You can't /sotw enable when there is no SOTW timer...");
            return;
        }

        if (sotwEnabled.add(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.GREEN + "Successfully disabled your SOTW timer.");
        } else {
            sender.sendMessage(ChatColor.RED + "Your SOTW timer was already disabled...");
        }
    }

    @Command(names = { "sotw cancel" }, permission = "foxtrot.sotw")
    public static void sotwCancel(CommandSender sender) {
        Long removed = customTimers.remove("&a&lSOTW ends in");
        if (removed != null && System.currentTimeMillis() < removed) {
            sender.sendMessage(ChatColor.GREEN + "Deactivated the SOTW timer.");
            return;
        }

        sender.sendMessage(ChatColor.RED + "SOTW timer is not active.");
    }

    @Command(names = "sotw start", permission = "foxtrot.sotw")
    public static void sotwStart(CommandSender sender, @Param(name = "time") String time) {
        int seconds = TimeUtils.parseTime(time);
        if (seconds < 0) {
            sender.sendMessage(ChatColor.RED + "Invalid time!");
            return;
        }

        customTimers.put("&a&lSOTW ends in", System.currentTimeMillis() + (seconds * 1000));
        sender.sendMessage(ChatColor.GREEN + "Started the SOTW timer for " + time);
    }

    @Command(names = "sotw extend", permission = "foxtrot.sotw")
    public static void sotwExtend(CommandSender sender, @Param(name = "time") String time) {
        int seconds;
        try {
            seconds = TimeUtils.parseTime(time);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Invalid time!");
            return;
        }

        if (seconds < 0) {
            sender.sendMessage(ChatColor.RED + "Invalid time!");
            return;
        }

        if (!customTimers.containsKey("&a&lSOTW ends in")) {
            sender.sendMessage(ChatColor.RED + "There is currently no active SOTW timer.");
            return;
        }

        customTimers.put("&a&lSOTW ends in", customTimers.get("&a&lSOTW ends in") + (seconds * 1000));
        sender.sendMessage(ChatColor.GREEN + "Extended the SOTW timer by " + time);
    }

    public static boolean isSOTWTimer() {
        return customTimers.containsKey("&a&lSOTW ends in");
    }

    public static boolean hasSOTWEnabled(UUID uuid) {
        return sotwEnabled.contains(uuid);
    }
}