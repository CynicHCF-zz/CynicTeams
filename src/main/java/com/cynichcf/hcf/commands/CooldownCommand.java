package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.listener.GoldenAppleListener;
import com.cynichcf.hcf.server.EnderpearlCooldownHandler;
import com.cynichcf.hcf.server.SpawnTagHandler;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;


public class CooldownCommand {

    @Command(names = {"cooldown", "timer"}, permission = "foxtrot.cooldown")
    public static void cooldownHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 35));
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Timer Commands");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW + "Types:");
        sender.sendMessage("ENDERPEARL, COMBAT, GAPPLE");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW + "/timer set <player> <type> <seconds>");
        sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 35));
    }

    @Command(names = {"cooldown set", "timer set"}, permission = "foxtrot.cooldown.set")
    public static void cooldownSet(CommandSender sender, @Param(name = "player", defaultValue = "self") Player target, @Param(name = "type") String type, @Param(name = "seconds") int seconds) {
        switch (type.toUpperCase().replace("_"," ")) {
            case "ENDERPEARL": {
                if (seconds <= 0)
                    EnderpearlCooldownHandler.getEnderpearlCooldown().remove(target.getName());
                else
                    EnderpearlCooldownHandler.getEnderpearlCooldown().put(target.getName(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));

                sender.sendMessage(ChatColor.YELLOW + "Set" + ChatColor.BLUE + " Enderpearl " + ChatColor.YELLOW + "cooldown of " + target.getDisplayName() + ChatColor.YELLOW + " to " + ChatColor.GOLD + seconds + " second" + (seconds > 1 ? "s" : ""));
                break;
            }

            case "COMBAT": {
                if (seconds <= 0)
                    SpawnTagHandler.removeTag(target);
                else
                    SpawnTagHandler.addOffensiveSeconds(target, seconds);

                sender.sendMessage(ChatColor.YELLOW + "Set" + ChatColor.DARK_RED + " Spawn Tag " + ChatColor.YELLOW + "cooldown of " + target.getDisplayName() + ChatColor.YELLOW + " to " + ChatColor.GOLD + seconds + " second" + (seconds > 1 ? "s" : ""));
                break;
            }
            case "GAPPLE": {
                if (seconds <= 0)
                    GoldenAppleListener.getCrappleCooldown().remove(target.getName());
                else
                    GoldenAppleListener.getCrappleCooldown().put(target.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));

                sender.sendMessage(ChatColor.YELLOW + "Set" + ChatColor.BLUE + " Golden Apple " + ChatColor.YELLOW + "cooldown of " + target.getDisplayName() + ChatColor.YELLOW + " to " + ChatColor.GOLD + seconds + " second" + (seconds > 1 ? "s" : ""));
                break;
            }
        }
    }
}