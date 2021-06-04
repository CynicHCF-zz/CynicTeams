package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;
import org.bukkit.ChatColor;

import java.util.UUID;

public class TeamColorMap extends PersistMap<ChatColor> {

    public TeamColorMap() {
        super("TeamColor", "TeamColor");
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
        return (contains(check) ? getValue(check) : ChatColor.DARK_GREEN);
    }

    public void setColor(UUID update, ChatColor color) {
        updateValueSync(update, color);
    }


}