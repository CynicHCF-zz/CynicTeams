package com.cynichcf.hcf.persist.maps;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.persist.PersistMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeathbanMap extends PersistMap<Long> {

    public DeathbanMap() {
        super("Deathbans", "Deathban");
    }

    
    public String getRedisValue(Long time) {
        return (String.valueOf(time));
    }

    
    public Long getJavaObject(String str) {
        return (Long.parseLong(str));
    }

    
    public Object getMongoValue(Long time) {
        return Long.toString(time);
    }

    public boolean isDeathbanned(UUID check) {
        if (getValue(check) != null) {
            return (getValue(check) > System.currentTimeMillis());
        }

        return (false);
    }

    public void deathban(UUID update, long seconds) {
        updateValueAsync(update, System.currentTimeMillis() + (seconds * 1000));
    }

    public void reduce(UUID uuid, long seconds) {
        if (!isDeathbanned(uuid)) return;
        updateValueAsync(uuid, getDeathban(uuid) - (seconds * 1000));
    }

    public void revive(UUID update) {
        updateValueAsync(update, 0L);

        if (!HCF.getInstance().getMapHandler().isPurgatory()) return;

        Player player = Bukkit.getPlayer(update);
        if (player != null && player.isOnline())
            HCF.getInstance().getMapHandler().getPurgatoryHandler().withdrawFromPurgatory(player, true);
    }

    public long getDeathban(UUID check) {
        return (contains(check) ? getValue(check) : 0L);
    }

    public void wipeDeathbans() {
        wipeValues();
    }

    public Collection<UUID> getDeathbannedPlayers() {
        Collection<UUID> deathbannedPlayers = new HashSet<>();

        for (Map.Entry<UUID, Long> entry : wrappedMap.entrySet()) {
            if (isDeathbanned(entry.getKey())) {
                deathbannedPlayers.add(entry.getKey());
            }
        }

        return (deathbannedPlayers);
    }
}