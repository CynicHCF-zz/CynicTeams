package com.cynichcf.hcf.persist.maps;


import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;


public class CreditsMap extends PersistMap<Integer> {

    public CreditsMap() {
        super("Credits", "Credits");
    }


    public String getRedisValue(Integer elo) {
        return (String.valueOf(elo));
    }


    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }

    public void setCredits(int i) {

    }


    public Object getMongoValue(Integer elo) {
        return (elo);
    }

    public int getCredits(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setCredits(UUID update, int credits) {
        updateValueAsync(update, credits);
    }
}