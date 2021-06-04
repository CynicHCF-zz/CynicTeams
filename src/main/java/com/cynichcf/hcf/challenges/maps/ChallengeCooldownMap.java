package com.cynichcf.hcf.challenges.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.UUID;

public class ChallengeCooldownMap extends PersistMap<Long> {

    public ChallengeCooldownMap(String challengeName) {
        super("ChallengesCooldown", "ChallengesCooldown." + challengeName);
    }


    public String getRedisValue(Long aLong) {
        return String.valueOf(aLong);
    }


    public Object getMongoValue(Long aLong) {
        return (aLong);
    }


    public Long getJavaObject(String str) {
        return Long.parseLong(str);
    }

    public void setCredits(int i) {

    }

    public long getCooldown(UUID check) {
        return (contains(check) ? getValue(check) : 0L);
    }

    public void setCooldown(UUID update, long amount) {
        updateValueAsync(update, (System.currentTimeMillis() + amount));
    }

    public boolean isOnCooldown(UUID uuid) {
        long now = System.currentTimeMillis();
        long til = getCooldown(uuid);
        return (now < til);
    }
}
