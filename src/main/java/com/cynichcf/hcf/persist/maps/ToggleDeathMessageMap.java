package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.persist.PersistMap;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.Bukkit;

import java.util.UUID;

public class ToggleDeathMessageMap extends PersistMap<Boolean> {

    public ToggleDeathMessageMap() {
        super("DeathMessageToggles", "DeathMessageEnabled");
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

    public void setDeathMessagesEnabled(UUID update, boolean toggled) {
        updateValueAsync(update, toggled);
    }

    public boolean areDeathMessagesEnabled(UUID check) {
        return (contains(check) ? getValue(check) : (!HCF.getInstance().getMapHandler().isKitMap() && !HCF.getInstance().getServerHandler().isVeltKitMap()));
    }
}
