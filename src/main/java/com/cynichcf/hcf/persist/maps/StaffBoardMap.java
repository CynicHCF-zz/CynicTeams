package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class StaffBoardMap extends PersistMap<Boolean> {

    public StaffBoardMap() {
        super("StaffSB", "StaffSB");
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

    public void setBoardToggled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean isBoardToggled(UUID check) {
        return (contains(check) ? getValue(check) : true);
    }

}