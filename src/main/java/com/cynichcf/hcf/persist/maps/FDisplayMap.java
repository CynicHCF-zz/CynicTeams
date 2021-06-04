package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class FDisplayMap extends PersistMap<Boolean> {

    public FDisplayMap() {
        super("FDisplay", "FDisplay");
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

    public void setToggled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isToggled(UUID check) {
        return (contains(check) ? getValue(check) : true);
    }

}