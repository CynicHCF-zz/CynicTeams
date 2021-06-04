package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class KillsMap extends PersistMap<Integer> {

    public KillsMap() {
        super("Kills", "Kills");
    }


    public String getRedisValue(Integer kills) {
        return (String.valueOf(kills));
    }


    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }


    public Object getMongoValue(Integer kills) {
        return (kills);
    }

    public int getKills(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setKills(UUID update, int kills) {
        updateValueAsync(update, kills);
        HCF.getInstance().getKdrMap().updateKDR(update);
    }

}