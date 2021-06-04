package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ELOMap extends PersistMap<Integer> {

    public ELOMap() {
        super("ELO", "ELO");
    }


    public String getRedisValue(Integer elo) {
        return (String.valueOf(elo));
    }


    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }


    public Object getMongoValue(Integer elo) {
        return (elo);
    }

    public int getELO(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setELO(UUID update, int elo) {
        updateValueAsync(update, elo);
    }
}
