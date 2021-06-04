package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class CobblePickupMap extends PersistMap<Boolean> {

    public CobblePickupMap() {
        super("CobblePickup", "CobblePickup");
    }

    
    public String getRedisValue(Boolean toggled) {
        return String.valueOf(toggled);
    }

    
    public Object getMongoValue(Boolean toggled) {
        return String.valueOf(toggled);
    }

    
    public Boolean getJavaObject(String str) {
        return Boolean.valueOf(str);
    }

    public void setCobblePickup(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isCobblePickup(UUID check) {
        return (contains(check) ? getValue(check) : false);
    }

}
