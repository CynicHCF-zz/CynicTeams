//package com.cynichcf.hcf.credit.listener;
//
//import com.cynichcf.hcf.util.CC;
//import com.cynichcf.hcf.HCF;
//import com.cynichcf.hcf.credit.CreditHandler;
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.PlayerDeathEvent;
//
//
//public class CreditListener implements Listener {
//
//    @EventHandler
//    public void onPlayerDeath(PlayerDeathEvent event) {
//        Player player = event.getEntity();
//
//        if (event.getEntity() != null && event.getEntity().getKiller() != null) {
//            Player killer = event.getEntity().getKiller();
//            HCF.getInstance().getCreditsMap().setCredits(killer.getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(killer.getUniqueId()) + CreditHandler.getWinCredits());
//            killer.sendMessage(CC.translate(" &6» &fYou have received &e+1 charm &ffor killing " + ChatColor.RED + player.getDisplayName()));
//        }
//    }
//}