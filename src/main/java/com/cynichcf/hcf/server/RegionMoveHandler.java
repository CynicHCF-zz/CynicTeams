package com.cynichcf.hcf.server;

import com.cynichcf.hcf.HCF;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerMoveEvent;

public interface RegionMoveHandler {

    public static RegionMoveHandler ALWAYS_TRUE = new RegionMoveHandler() {

        
        public boolean handleMove(PlayerMoveEvent event) {
            return (true);
        }

    };

    public static RegionMoveHandler PVP_TIMER = new RegionMoveHandler() {

        
        public boolean handleMove(PlayerMoveEvent event) {
            if (HCF.getInstance().getPvPTimerMap().hasTimer(event.getPlayer().getUniqueId()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot do this while your PVP Timer is active!");
                event.getPlayer().sendMessage(ChatColor.RED + "Type '" + ChatColor.YELLOW + "/pvp enable" + ChatColor.RED + "' to remove your timer.");
                event.setTo(event.getFrom());
                return (false);
            }

            return (true);
        }

    };

    public boolean handleMove(PlayerMoveEvent event);

}