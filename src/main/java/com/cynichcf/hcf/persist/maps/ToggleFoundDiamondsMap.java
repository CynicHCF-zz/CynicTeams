package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ToggleFoundDiamondsMap extends PersistMap<Boolean> {

    public ToggleFoundDiamondsMap() {
        super("FoundDiamondToggles", "FoundDiamondEnabled");
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

    public void setFoundDiamondToggled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isFoundDiamondToggled(UUID check) {
        return (contains(check) ? getValue(check) : true);
    }

}