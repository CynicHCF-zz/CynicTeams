package com.cynichcf.hcf.listener;


import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.settings.menu.SettingsMenu;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SettingsSignListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ((HCF.getInstance().getMapHandler().isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap()) && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase(ChatColor.DARK_GREEN + "Settings Menu") && sign.getLine(2).equalsIgnoreCase(ChatColor.GREEN + "click to open")){
               new SettingsMenu().openMenu(player);
                }
            }
        }
}
