package com.cynichcf.hcf.server;

import com.mongodb.BasicDBObject;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Deathban {

    private static Map<String, Integer> deathban = new LinkedHashMap<>();
    private static int defaultMinutes = 120;

    static {
        deathban.put("DEFAULT", 120);
        deathban.put("IRON", 120);
        deathban.put("GOLD", 120);
        deathban.put("DIAMOND", 120);
        deathban.put("PLATINUM", 120);
        deathban.put("YOUTUBER", 120);
        deathban.put("CYNIC", 120);
        deathban.put("FAMOUS", 120);
        deathban.put("PARTNER", 120);
    }

    public static void load(BasicDBObject object) {
        deathban.clear();

        for (String key : object.keySet()) {
            if (key.equals("DEFAULT"))  {
                defaultMinutes = object.getInt(key);
            } else {
                deathban.put(key, object.getInt(key));
            }
        }
    }

    public static int getDeathbanSeconds(Player player) {
        int minutes = defaultMinutes;

        for (Map.Entry<String, Integer> entry : deathban.entrySet()) {
            if (player.hasPermission("inherit." + entry.getKey().toLowerCase()) && entry.getValue() < minutes) {
                minutes = entry.getValue();
            }
        }

        return (int) TimeUnit.MINUTES.toSeconds(minutes);
    }

}
