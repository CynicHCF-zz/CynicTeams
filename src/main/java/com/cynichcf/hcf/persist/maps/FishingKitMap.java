package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class FishingKitMap extends PersistMap<Integer> {

    public FishingKitMap() {
        super("FishingKitUses", "FishingKitUses");
    }

    
    public String getRedisValue(Integer uses) {
        return (String.valueOf(uses));
    }

    
    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }

    
    public Object getMongoValue(Integer uses) {
        return (uses);
    }

    public int getUses(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setUses(UUID update, int uses) {
        updateValueAsync(update, uses);
    }

}