package com.cynichcf.hcf.persist.maps;

import java.util.UUID;

import com.cynichcf.hcf.persist.PersistMap;

public class FriendLivesMap extends PersistMap<Integer> {

    public FriendLivesMap() {
        super("FriendLives", "Lives.Friend");
    }

    
    public String getRedisValue(Integer lives) {
        return (String.valueOf(lives));
    }

    
    public Integer getJavaObject(String str) {
        return (Integer.parseInt(str));
    }

    
    public Object getMongoValue(Integer lives) {
        return (lives);
    }

    public int getLives(UUID check) {
        return (contains(check) ? getValue(check) : 0);
    }

    public void setLives(UUID update, int lives) {
        updateValueSync(update, lives);
    }

    public void addLives(UUID update, int amount) {
        setLives(update, getLives(update) + amount);
    }

}