package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ToggleGlobalChatMap extends PersistMap<Boolean> {

    public ToggleGlobalChatMap() {
        super("GlobalChatToggles", "GlobalChat");
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

    public void setGlobalChatToggled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isGlobalChatToggled(UUID check) {
        return (contains(check) ? getValue(check) : true);
    }

}