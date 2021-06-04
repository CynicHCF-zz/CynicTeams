package com.cynichcf.hcf.challenges.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ChallengeMap extends PersistMap<Integer> {

    public ChallengeMap(String challengeName) {
        super("Challenges", "Challenges." + challengeName);
    }

    public String getRedisValue(Integer amount) {
        return (String.valueOf(amount));
    }

    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }

    public void setCredits(int i) {

    }

    public Object getMongoValue(Integer amount) {
        return (amount);
    }

    public int getAmount(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setAmount(UUID update, int amount) {
        updateValueAsync(update, amount);
    }
}
