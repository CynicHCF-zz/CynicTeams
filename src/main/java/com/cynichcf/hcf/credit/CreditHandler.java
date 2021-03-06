//package com.cynichcf.hcf.credit;
//
//import com.google.common.collect.Maps;
//import lombok.Getter;
//import com.cynichcf.hcf.HCF;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//public class CreditHandler {
//
//    @Getter
//    private static final Map<UUID, Long> pendingCredits = Maps.newConcurrentMap();
//
//    public CreditHandler() {
//        Bukkit.getScheduler().runTaskTimerAsynchronously(HCF.getInstance(), () -> {
//            List<UUID> toRemove = new ArrayList<>();
//            List<UUID> toUpdate = new ArrayList<>();
//
//            for (Player player : Bukkit.getOnlinePlayers()) {
//                if (!(pendingCredits.containsKey(player.getUniqueId()))) {
//                    pendingCredits.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
//                }
//            }
//
//            for (UUID uuid : pendingCredits.keySet()) {
//                long time = pendingCredits.get(uuid);
//
//                if (Bukkit.getPlayer(uuid) == null) {
//                    toRemove.add(uuid);
//                    continue;
//                }
//
//                if (System.currentTimeMillis() >= time) {
//                    toUpdate.add(uuid);
//                }
//            }
//
//            for (UUID uuid : toRemove) {
//                pendingCredits.remove(uuid);
//            }
//
//            for (UUID uuid : toUpdate) {
//                Player player = Bukkit.getPlayer(uuid);
//
//                if (player != null) {
//                    player.sendMessage(ChatColor.WHITE + "You've received " + ChatColor.GOLD + "1 charm" + ChatColor.WHITE + " for actively playing!");
//                    player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "To use your credits type '" + ChatColor.WHITE + "/charms shop" + ChatColor.GRAY.toString() + ChatColor.ITALIC + "'.");
//                    HCF.getInstance().getCreditsMap().setCredits(uuid, HCF.getInstance().getCreditsMap().getCredits(uuid) + 1);
//                    pendingCredits.put(uuid, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
//                }
//            }
//        }, 0, 20L);
//    }
//
//    public static Map<UUID, Long> getPendingCredits() {
//        return pendingCredits;
//    }
//
//    public static int getPlayerCredits(Player player) {
//        return HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId());
//    }
//
//    public static int getWinCredits() {
//        return 1;
//    }
//}