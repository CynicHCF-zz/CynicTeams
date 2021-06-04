package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class IronMinedMap extends PersistMap<Integer> {

    public IronMinedMap() {
        super("IronMined", "MiningStats.Iron");
    }

    
    public String getRedisValue(Integer mined) {
        return (String.valueOf(mined));
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