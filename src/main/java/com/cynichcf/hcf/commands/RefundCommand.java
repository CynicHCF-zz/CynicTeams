package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.Library;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.uuid.FrozenUUIDCache;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RefundCommand {

    @Command(names = {"refund", "invrestore"}, permission = "foxtrot.staff")
    public static void refundCommand(CommandSender sender, @Param(name = "player") Player target, @Param(name = "reason", wildcard = true) String reason){
        if (reason.equals(".")){
            sender.sendMessage(ChatColor.RED + "Invalid Reason.");
            return;
        }

        HCF.getInstance().getServer().getScheduler().runTaskAsynchronously(HCF.getInstance(), () -> {
            Library.getInstance().runRedisCommand((redis) -> {
               if (!redis.exists("lastInv:contents:" + target.getUniqueId())){
                   sender.sendMessage(ChatColor.RED + "No last inventory recorded for " + FrozenUUIDCache.name(target.getUniqueId()));
                   return null;
               }

                ItemStack[] contents = Library.PLAIN_GSON.fromJson(redis.get("lastInv:contents:" + target.getUniqueId()), ItemStack[].class);
                ItemStack[] armor = Library.PLAIN_GSON.fromJson(redis.get("lastInv:armorContents:" + target.getUniqueId()), ItemStack[].class);

                cleanLoot(contents);
                cleanLoot(armor);
                HCF.getInstance().getServer().getScheduler().runTaskAsynchronously(HCF.getInstance(), () -> {
                    target.getInventory().setContents(contents);
                    target.getInventory().setArmorContents(armor);
                    target.updateInventory();

                    sender.sendMessage(ChatColor.GREEN + "Loaded " + FrozenUUIDCache.name(target.getUniqueId()) + "'s last inventory.");
                    HCF.getInstance().getDiscordLogger().logRefund(target.getName(), sender.getName(), reason);
                });

                return null;
            });
        });
    }

    public static void cleanLoot(ItemStack[] stack) {
        for (ItemStack item : stack) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                ItemMeta meta = item.getItemMeta();

                List<String> lore = item.getItemMeta().getLore();
                lore.remove(ChatColor.DARK_GRAY + "PVP Loot");
                meta.setLore(lore);

                item.setItemMeta(meta);
            }
        }
    }
}
