package com.cynichcf.hcf.listener;

import com.google.common.collect.*;
import com.cynichcf.hcf.server.EnderpearlCooldownHandler;
import com.cynichcf.hcf.HCF;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class PearlGlitchListener implements Listener
{
    private final HCF plugin;
    private ImmutableSet<Material> blockedPearlTypes;
    
    public PearlGlitchListener(HCF plugin) {
        this.blockedPearlTypes = Sets.immutableEnumSet(Material.THIN_GLASS, new Material[] { Material.IRON_FENCE, Material.FENCE, Material.NETHER_FENCE, Material.FENCE_GATE,});
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPearlClip(final PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            final Location to = event.getTo();
            if (this.blockedPearlTypes.contains(to.getBlock().getType())) {
                final Player player = event.getPlayer();
                player.sendMessage(ChatColor.YELLOW + "Pearl glitching detected, enderpearl refunded.");
                EnderpearlCooldownHandler.resetEnderpearlTimer(player);
                event.setCancelled(true);
                return;
            }
            to.setX(to.getBlockX() + 0.5);
            to.setZ(to.getBlockZ() + 0.5);
            event.setTo(to);
        }
    }
}
