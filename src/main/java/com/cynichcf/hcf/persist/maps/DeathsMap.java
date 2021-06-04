package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class DeathsMap extends PersistMap<Integer> {

    public DeathsMap() {
        super("Deaths", "Deaths");
    }


    public String getRedisValue(Integer deaths) {
        return (String.valueOf(deaths));
    }


    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }


    public Object getMongoValue(Integer deaths) {
        return (deaths);
    }

    public int getDeaths(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setDeaths(UUID update, int kills) {
        updateValueAsync(update, kills);
        HCF.getInstance().getKdrMap().updateKDR(update);
    }

}
