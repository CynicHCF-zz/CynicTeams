package com.cynichcf.hcf.deathmessage.listeners;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.map.killstreaks.Killstreak;
import com.cynichcf.hcf.map.killstreaks.PersistentKillstreak;
import com.cynichcf.hcf.map.stats.StatsEntry;
import com.cynichcf.hcf.util.Players;
import com.cynichcf.hcf.deathmessage.event.PlayerKilledEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Maps;

import com.cynichcf.hcf.deathmessage.DeathMessageHandler;
import com.cynichcf.hcf.deathmessage.event.CustomPlayerDamageEvent;
import com.cynichcf.hcf.deathmessage.objects.Damage;
import com.cynichcf.hcf.deathmessage.objects.PlayerDamage;
import com.cynichcf.hcf.deathmessage.util.UnknownDamage;
import com.cynichcf.hcf.team.Team;

public class DamageListener implements Listener {
    
    // kit-map only
    private Map<UUID, UUID> lastKilled = Maps.newHashMap();
    private Map<UUID, Integer> boosting = Maps.newHashMap();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            CustomPlayerDamageEvent customEvent = new CustomPlayerDamageEvent(event, new UnknownDamage(player.getName(), event.getDamage()));
            
            HCF.getInstance().getServer().getPluginManager().callEvent(customEvent);
            DeathMessageHandler.addDamage(player, customEvent.getTrackerDamage());
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        DeathMessageHandler.clearDamage(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<Damage> record = DeathMessageHandler.getDamage(event.getEntity());
        
        event.setDeathMessage(null);
        
        String deathMessage;
        
        if (record != null) {
            Damage deathCause = record.get(record.size() - 1);
            
            // Hacky NMS to change the player's killer
            // System.out.println("The milliseconds since death is: " +
            // deathCause.getTimeDifference() + " this should be less than " +
            // TimeUnit.MINUTES.toMillis(1) );
            if (deathCause instanceof PlayerDamage && deathCause.getTimeDifference() < TimeUnit.MINUTES.toMillis(1)) {
                // System.out.println("Its a playerdamage thing");
                String killerName = ((PlayerDamage) deathCause).getDamager();
                Player killer = HCF.getInstance().getServer().getPlayerExact(killerName);
                
                if (killer != null && !HCF.getInstance().getInDuelPredicate().test(event.getEntity())) {
                    if (HCF.getInstance().getMapHandler().isKitMap()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), HCF.getInstance().getConfig().getString("killReward").replace("%player%", killer.getName()));
                    }
                    ((CraftPlayer) event.getEntity()).getHandle().killer = ((CraftPlayer) killer).getHandle();
                    
                    // kit-map death handling
                    if (HCF.getInstance().getMapHandler().isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap()) {
                        Player victim = event.getEntity();

                        // Call event
                        PlayerKilledEvent killedEvent = new PlayerKilledEvent(killer, victim);
                        HCF.getInstance().getServer().getPluginManager().callEvent(killedEvent);

                        // Prevent kill boosting
                        // Check if the victim's UUID is the same as the killer's last victim UUID
                        // Check if the victim's IP matches the killer's IP
                        if (lastKilled.containsKey(killer.getUniqueId()) && lastKilled.get(killer.getUniqueId()) == victim.getUniqueId()) {
                            boosting.putIfAbsent(killer.getUniqueId(), 0);
                            boosting.put(killer.getUniqueId(), boosting.get(killer.getUniqueId()) + 1);
                        } else {
                            boosting.put(killer.getUniqueId(), 0);
                        }

                        if (killer.equals(victim) || Players.isNaked(victim)) {
                            StatsEntry victimStats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(victim);

                            victimStats.addDeath();
                        } else if (boosting.containsKey(killer.getUniqueId()) && boosting.get(killer.getUniqueId()) > 1) {

                            StatsEntry victimStats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(victim);
                            
                            victimStats.addDeath();
                        } else {
                            StatsEntry victimStats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(victim);
                            StatsEntry killerStats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(killer);
                            
                            victimStats.addDeath();
                            killerStats.addKill();
                            
                            lastKilled.put(killer.getUniqueId(), victim.getUniqueId());
                            
                            Killstreak killstreak = HCF.getInstance().getMapHandler().getKillstreakHandler().check(killerStats.getKillstreak());
                            
                            if (killstreak != null) {
                                killstreak.apply(killer);
                                
                                Bukkit.broadcastMessage(killer.getDisplayName() + ChatColor.YELLOW + " has gotten the " + ChatColor.RED + killstreak.getName() + ChatColor.YELLOW + " killstreak!");

                                List<PersistentKillstreak> persistent = HCF.getInstance().getMapHandler().getKillstreakHandler().getPersistentKillstreaks(killer, killerStats.getKillstreak());

                                for (PersistentKillstreak persistentStreak : persistent) {
                                    if (persistentStreak.matchesExactly(killerStats.getKillstreak())) {
                                        Bukkit.broadcastMessage(killer.getDisguisedName() + ChatColor.YELLOW + " has gotten the " + ChatColor.RED + killstreak.getName() + ChatColor.YELLOW + " killstreak!");
                                    }

                                    persistentStreak.apply(killer);
                                }
                            }
                            
                            HCF.getInstance().getKillsMap().setKills(killer.getUniqueId(), killerStats.getKills());
                            HCF.getInstance().getDeathsMap().setDeaths(victim.getUniqueId(), victimStats.getDeaths());
                        }
                    } else {
                        HCF.getInstance().getKillsMap().setKills(killer.getUniqueId(), HCF.getInstance().getKillsMap().getKills(killer.getUniqueId()) + 1);
                        HCF.getInstance().getDeathsMap().setDeaths(event.getEntity().getUniqueId(), HCF.getInstance().getDeathsMap().getDeaths(event.getEntity().getUniqueId()) + 1);
                        event.getDrops().add(HCF.getInstance().getServerHandler().generateDeathSkull(event.getEntity().getName(), killer.getName()));

                        if (HCF.getInstance().getServerHandler().isHardcore()) {
                            event.getDrops().add(HCF.getInstance().getServerHandler().generateDeathSign(event.getEntity().getName(), killer.getName()));
                        }
                    }
                }
            }
            
            deathMessage = deathCause.getDeathMessage();
        } else {
            deathMessage = new UnknownDamage(event.getEntity().getName(), 1).getDeathMessage();
        }
        
        Player killer = event.getEntity().getKiller();
        
        Team killerTeam = killer == null ? null : HCF.getInstance().getTeamHandler().getTeam(killer);
        Team deadTeam = HCF.getInstance().getTeamHandler().getTeam(event.getEntity());


        Bukkit.getScheduler().scheduleAsyncDelayedTask(HCF.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (HCF.getInstance().getToggleDeathMessageMap().areDeathMessagesEnabled(player.getUniqueId())) {
                    player.sendMessage(deathMessage);
                } else {
                    if (HCF.getInstance().getTeamHandler().getTeam(player) == null) {
                        continue;
                    }
                    
                    // send them the message if the player who died was on their team
                    if (HCF.getInstance().getTeamHandler().getTeam(event.getEntity()) != null && HCF.getInstance().getTeamHandler().getTeam(player).equals(HCF.getInstance().getTeamHandler().getTeam(event.getEntity()))) {
                        player.sendMessage(deathMessage);
                    }
                    
                    // send them the message if the killer was on their team
                    if (killer != null) {
                        if (HCF.getInstance().getTeamHandler().getTeam(killer) != null && HCF.getInstance().getTeamHandler().getTeam(player).equals(HCF.getInstance().getTeamHandler().getTeam(killer))) {
                            player.sendMessage(deathMessage);
                        }
                    }
                }
            }
        });
        
        // DeathTracker.logDeath(event.getEntity(), event.getEntity().getKiller());
        DeathMessageHandler.clearDamage(event.getEntity());
        HCF.getInstance().getDeathsMap().setDeaths(event.getEntity().getUniqueId(), HCF.getInstance().getDeathsMap().getDeaths(event.getEntity().getUniqueId()) + 1);
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (HCF.getInstance().getMapHandler().isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap()) {
            checkKillstreaks(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (HCF.getInstance().getMapHandler().isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap()) {
            checkKillstreaks(event.getPlayer());
        }
    }
    
    private void checkKillstreaks(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(HCF.getInstance(), () -> {
            int killstreak = HCF.getInstance().getMapHandler().getStatsHandler().getStats(player).getKillstreak();
            List<PersistentKillstreak> persistent = HCF.getInstance().getMapHandler().getKillstreakHandler().getPersistentKillstreaks(player, killstreak);
            
            for (PersistentKillstreak persistentStreak : persistent) {
                persistentStreak.apply(player);
            }
        });
    }
    
    @EventHandler(ignoreCancelled = false)
    public void onRightClick(PlayerInteractEvent event) {
        if (!event.getAction().name().startsWith("RIGHT_CLICK")) {
            return;
        }
        
        ItemStack inHand = event.getPlayer().getItemInHand();
        if (inHand == null) {
            return;
        }
        
        if (inHand.getType() != Material.NETHER_STAR) {
            return;
        }
        
        if (!inHand.hasItemMeta() || !inHand.getItemMeta().hasDisplayName() || !inHand.getItemMeta().getDisplayName().startsWith(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Potion Refill Token")) {
            return;
        }
        
        event.getPlayer().setItemInHand(null);
        
        ItemStack pot = new ItemStack(Material.POTION, 1, (short) 16421);
        while (event.getPlayer().getInventory().addItem(pot).isEmpty()) {}
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDeathKillIncrement(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player killed = event.getEntity();
        if (killer != null) {
            HCF.getInstance().getTeamHandler().getTeam(killer).addPoints(5);
            }
        }
}
