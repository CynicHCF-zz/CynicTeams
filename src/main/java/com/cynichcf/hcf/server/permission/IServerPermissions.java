package com.cynichcf.hcf.server.permission;

import org.bukkit.entity.Player;

import java.util.function.Supplier;

public interface IServerPermissions {

    Supplier<Boolean> isAvailable();
    void init();

    String getPlayerRank(Player player);

}