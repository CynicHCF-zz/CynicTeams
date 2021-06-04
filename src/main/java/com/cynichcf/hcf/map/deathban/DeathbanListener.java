package com.cynichcf.hcf.map.deathban;

import com.cynichcf.hcf.commands.LastInvCommand;
import com.cynichcf.hcf.server.EnderpearlCooldownHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.util.TimeUtils;

public class DeathbanListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        LastInvCommand.recordInventory(event.getEntity());

        EnderpearlCooldownHandler.getEnderpearlCooldown().remove(event.getEntity().getName()); // cancel enderpearls

        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getEntity())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        long seconds = HCF.getInstance().getServerHandler().getDeathban(event.getEntity());
        HCF.getInstance().getDeathbanMap().deathban(event.getEntity().getUniqueId(), seconds);

        String time = TimeUtils.formatLongIntoDetailedString(seconds);

        new BukkitRunnable() {
            public void run() {
                if (!event.getEntity().isOnline()) {
                    return;
                }

                if (HCF.getInstance().getServerHandler().isPreEOTW()) {
                    event.getEntity().kickPlayer(ChatColor.YELLOW + "Come back tomorrow for SOTW!");
                } else {
                    event.getEntity().kickPlayer(ChatColor.YELLOW + "Come back in " + time + "!");
                }
            }

        }.runTaskLater(HCF.getInstance(), 10 * 20L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
            return;
        }

        Player player = event.getPlayer();
        boolean shouldBypass = player.isOp() || player.hasPermission("foxtrot.staff");
        boolean isPowers = HCF.getInstance().getConfig().getBoolean("powers");

        if (shouldBypass) {
            HCF.getInstance().getDeathbanMap().revive(player.getUniqueId());
            return;
        }

        if (isPowers) {
            if (!HCF.getInstance().getServerHandler().isPreEOTW() || !HCF.getInstance().getServerHandler().isEOTW()) return;
        }

        long unbannedOn = HCF.getInstance().getDeathbanMap().getDeathban(event.getPlayer().getUniqueId());
        long left = unbannedOn - System.currentTimeMillis();
        String time = TimeUtils.formatLongIntoDetailedString(left / 1000);

        if (HCF.getInstance().getServerHandler().isPreEOTW()) {
            player.sendMessage(ChatColor.RED + "Come back tomorrow for SOTW.");
            return;
        }

        int soulbound = HCF.getInstance().getSoulboundLivesMap().getLives(player.getUniqueId());

        if (soulbound < 1) {
            player.kickPlayer(ChatColor.RED + "You are currently deathbanned, check back in " + ChatColor.BOLD + time + ChatColor.RED + "!");
            return;
        }

        HCF.getInstance().getSoulboundLivesMap().setLives(player.getUniqueId(), soulbound - 1);
        HCF.getInstance().getDeathbanMap().revive(player.getUniqueId());

        player.spigot().respawn();
        player.sendMessage(ChatColor.GREEN + "You have used a life to revive yourself. You now have " + (soulbound - 1) + " lives remaining.");
    }

}