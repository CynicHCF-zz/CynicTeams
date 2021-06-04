package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;
import com.cynichcf.hcf.team.Team;
import org.bukkit.ChatColor;

import java.util.UUID;

public class AllyColorMap extends PersistMap<ChatColor> {

    public AllyColorMap() {
        super("AllyColor", "AllyColor");
    }

    
    public String getRedisValue(ChatColor color) {
        return color.name();
    }

    
    public ChatColor getJavaObject(String str) {
        return ChatColor.valueOf(str);
    }

    
    public Object getMongoValue(ChatColor color) {
        return (color.name());
    }

    public ChatColor getColor(UUID check) {
        return (contains(check) ? getValue(check) : Team.ALLY_COLOR);
    }

    public void setColor(UUID update, ChatColor color) {
        updateValueSync(update, color);
    }


}