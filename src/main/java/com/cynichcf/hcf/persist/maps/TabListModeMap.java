package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;
import com.cynichcf.hcf.tab.TabListMode;

import java.util.UUID;

public class TabListModeMap extends PersistMap<TabListMode> {

    public TabListModeMap() {
        super("TabListInfo", "TabListInfo", false);
    }

    
    public String getRedisValue(TabListMode toggled){
        return (toggled.name());
    }

    
    public TabListMode getJavaObject(String str){
        if (str.equals("VANILLA")) return TabListMode.DETAILED;
        return (TabListMode.valueOf(str));
    }

    
    public Object getMongoValue(TabListMode toggled) {
        return (toggled);
    }

    public void setTabListMode(UUID update, TabListMode mode) {
        updateValueAsync(update, mode);
    }

    public TabListMode getTabListMode(UUID check) {
        return (contains(check) ? getValue(check) : TabListMode.DETAILED);
    }

}