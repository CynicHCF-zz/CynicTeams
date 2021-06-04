package com.cynichcf.hcf.nametag;

import com.cynichcf.hcf.HCF;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class NametagListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (NametagManager.isInitiated()) {
            event.getPlayer().setMetadata("Nametag-LoggedIn", (MetadataValue)new FixedMetadataValue((Plugin) HCF.getInstance(), true));
            NametagManager.initiatePlayer(event.getPlayer());
            NametagManager.reloadPlayer(event.getPlayer());
            NametagManager.reloadOthersFor(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().removeMetadata("Nametag-LoggedIn", (Plugin) HCF.getInstance());
        NametagManager.getTeamMap().remove(event.getPlayer().getName());
    }
}
