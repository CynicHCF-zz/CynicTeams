package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ToggleLFFMessageMap extends PersistMap<Boolean> {

    public ToggleLFFMessageMap() {
        super("LFFMessageToggles", "LFFMessageEnabled");
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

    public void setEnabled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isEnabled(UUID check) {
        return (contains(check) ? getValue(check) : true);
    }

}
