package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class RedstoneMinedMap extends PersistMap<Integer> {

    public RedstoneMinedMap() {
        super("RedstoneMined", "MiningStats.Redstone");
    }

    
    public String getRedisValue(Integer kills) {
        return (String.valueOf(kills));
    }

    
    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }

    
    public Object getMongoValue(Integer mined) {
        return (mined);
    }

    public int getMined(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setMined(UUID update, int mined) {
        updateValueAsync(update, mined);
    }

}