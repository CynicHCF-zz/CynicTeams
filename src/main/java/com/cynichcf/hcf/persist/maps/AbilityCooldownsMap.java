package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class AbilityCooldownsMap extends PersistMap<Boolean> {

    public AbilityCooldownsMap() {
        super("AbilityCooldownsSB", "AbilityCooldownsSB");
    }

    
    public String getRedisValue(Boolean toggled){
        return (String.valueOf(toggled));
    }

    
    public Boolean getJavaObject(String str){
        return (Boolean.valueOf(str));
    }

    
    public Object getMongoValue(Boolean toggled) {
        return (toggled);
    }

    public void setCooldownsToggled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isCooldownsToggled(UUID check) {
        return (contains(check) ? getValue(check) : false);
    }

}