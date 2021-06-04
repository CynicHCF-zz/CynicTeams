package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class StartingPvPTimerMap extends PersistMap<Boolean> {

    public StartingPvPTimerMap() {
        super("StartingTimer", "StartingTimer");
    }

    
    public String getRedisValue(Boolean value) {
        return value.toString();
    }

    
    public Object getMongoValue(Boolean value) {
        return value.toString();
    }

    
    public Boolean getJavaObject(String string) {
        return Boolean.valueOf(string);
    }

    public void set(UUID uuid, boolean value) {
        updateValueAsync(uuid, value);
    }

    public boolean get(UUID uuid) {
        return contains(uuid) && getValue(uuid);
    }

}
