package com.cynichcf.hcf.map.deathban.purgatory;

import com.cynichcf.hcf.commands.LastInvCommand;
import com.cynichcf.hcf.server.EnderpearlCooldownHandler;
import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PurgatoryListener implements Listener {

    private List<String> reviveConfirmation = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || !(event.getClickedBlock().getState() instanceof Sign)) {
            return;
        }

        Player player = event.getPlayer();

        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getPlayer())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
            return;
        }

        Sign sign = (Sign) event.getClickedBlock().getState();

        if (!sign.getLine(2).toLowerCase().contains("use a life") || !sign.getLine(1).toLowerCase().contains("use a life")) {
            return;
        }

        int soulbound = HCF.getInstance().getSoulboundLivesMap().getLives(player.getUniqueId());

        if (soulbound < 1) {
            player.sendMessage(ChatColor.RED + "You do not have any lives!");
            return;
        }

        if (!reviveConfirmation.contains(player.getName())) {
            player.sendMessage(ChatColor.YELLOW + "Are you sure you want to use a life? If yes click the sign again.");
            player.sendMessage(ChatColor.YELLOW + "You will have " + (soulbound - 1) + " lives remaining.");
            reviveConfirmation.add(player.getName());
            return;
        }
        reviveConfirmation.remove(player.getName());

        HCF.getInstance().getSoulboundLivesMap().setLives(player.getUniqueId(), soulbound - 1);
        HCF.getInstance().getDeathbanMap().revive(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "You have used a life to revive yourself. You now have " + (soulbound - 1) + " lives remaining.");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(HCF.getInstance().getDeathbanMap().isDeathbanned(event.getEntity().getUniqueId())) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        LastInvCommand.recordInventory(event.getEntity());

        EnderpearlCooldownHandler.getEnderpearlCooldown().remove(event.getEntity().getName());

        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getEntity())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        int seconds = (int) HCF.getInstance().getServerHandler().getDeathban(event.getEntity());
        HCF.getInstance().getDeathbanMap().deathban(event.getEntity().getUniqueId(), seconds);

        String time = TimeUtils.formatIntoDetailedString(seconds);

        if (HCF.getInstance().getServerHandler().isPreEOTW()) {
            event.getEntity().kickPlayer(ChatColor.RED + "Come back tomorrow for SOTW!");
            return;
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(HCF.getInstance(), () -> event.getEntity().spigot().respawn(), 5);

        boolean shouldBypass = event.getEntity().isOp();

        if (!shouldBypass)
            shouldBypass = event.getEntity().hasPermission("foxtrot.staff");

        if (shouldBypass) {
            HCF.getInstance().getDeathbanMap().revive(event.getEntity().getUniqueId());
        } else {
            event.getEntity().sendMessage(ChatColor.RED + "You have been deathbanned for " + ChatColor.BOLD + time + ChatColor.RED + "!");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getPlayer())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        PurgatoryHandler purgatoryHandler = HCF.getInstance().getMapHandler().getPurgatoryHandler();

        int price = purgatoryHandler.getPriceMap().getOrDefault(event.getBlock().getType(), 0);
        if (price > 0) {
            Material material = event.getBlock().getType();
            Vector location = event.getBlock().getLocation().toVector();

            if (purgatoryHandler.getMineMap().containsKey(location)) {
                event.getBlock().setType(Material.COBBLESTONE);
                return;
            }

            purgatoryHandler.getMineMap().put(location, material);
            event.getBlock().setType(Material.COBBLESTONE);

            new BukkitRunnable() {
                
                public void run() {
                    event.getBlock().setType(purgatoryHandler.getMineMap().remove(location));
                }
            }.runTaskLater(HCF.getInstance(), 20L * 5L);

            HCF.getInstance().getDeathbanMap().reduce(player.getUniqueId(), price);

            if (!HCF.getInstance().getDeathbanMap().isDeathbanned(player.getUniqueId()))
                HCF.getInstance().getMapHandler().getPurgatoryHandler().withdrawFromPurgatory(player, false);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getPlayer())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
            return;
        }

        boolean shouldBypass = event.getPlayer().isOp();

        if (!shouldBypass)
            shouldBypass = event.getPlayer().hasPermission("foxtrot.staff");

        if (shouldBypass) {
            event.setRespawnLocation(HCF.getInstance().getServerHandler().getSpawnLocation());
            event.getPlayer().sendMessage(ChatColor.YELLOW + "You would have been deathbanned, but you have permissions to bypass.");
            return;
        }

        event.setRespawnLocation(HCF.getInstance().getMapHandler().getPurgatoryHandler().getPurgatoryLocation());

        new BukkitRunnable() {
            
            public void run() {
                if (!event.getPlayer().isOnline())
                    return;

                event.getPlayer().teleport(HCF.getInstance().getMapHandler().getPurgatoryHandler().getPurgatoryLocation());
                HCF.getInstance().getMapHandler().getPurgatoryHandler().addToPurgatory(event.getPlayer());
            }
        }.runTaskLater(HCF.getInstance(), 5L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        //Foxtrot.getInstance().getMapHandler().getPurgatoryHandler().getBanCache().remove(event.getPlayer().getName());
        reviveConfirmation.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getPlayer())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().updateInventory();
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (HCF.getInstance().getMapHandler().isKitMap()) {
            return;
        }

        if (HCF.getInstance().getInDuelPredicate().test(event.getPlayer())) {
            return;
        }

        if (HCF.getInstance().getServerHandler().isVeltKitMap()) {
            return;
        }

        if (!HCF.getInstance().getMapHandler().isPurgatory()) {
            return;
        }

        if (event.getItem().getItemStack().isSimilar(HCF.getInstance().getMapHandler().getPurgatoryHandler().getPickaxe())) {
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
            if (HCF.getInstance().getMapHandler().getPurgatoryHandler().getBanCache().getOrDefault(event.getPlayer().getName(), false)) {
                HCF.getInstance().getMapHandler().getPurgatoryHandler().withdrawFromPurgatory(event.getPlayer(), false);
            }
            return;
        }

        Player player = event.getPlayer();
        boolean shouldBypass = player.isOp() || player.hasPermission("foxtrot.staff");

        if (shouldBypass) {
            HCF.getInstance().getDeathbanMap().revive(event.getPlayer().getUniqueId());
        } else {
            HCF.getInstance().getMapHandler().getPurgatoryHandler().addToPurgatory(event.getPlayer());
        }
    }

}