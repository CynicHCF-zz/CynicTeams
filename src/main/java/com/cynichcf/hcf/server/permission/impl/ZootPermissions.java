package com.cynichcf.hcf.server.permission.impl;

import com.cynichcf.hcf.server.permission.IServerPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public class ZootPermissions implements IServerPermissions {

    
    public Supplier<Boolean> isAvailable() {
        return () -> Bukkit.getPluginManager().isPluginEnabled("foxtrot") || Bukkit.getPluginManager().isPluginEnabled("foxtrot");
    }

    
    public void init() {

    }

    
    public String getPlayerRank(Player player) {
       // Profile profile = Profile.getByUuid(player.getUniqueId());

     //   if (profile.getActiveRank() == null)
            return "Default";
       // return profile.getActiveRank().getDisplayName();
    }
}