package com.cynichcf.hcf.persist.maps;

import com.cynichcf.hcf.persist.PersistMap;

import java.util.Date;
import java.util.UUID;

public class LastJoinMap extends PersistMap<Long> {

    public LastJoinMap() {
        super("LastJoin", "LastJoined");
    }

    
    public String getRedisValue(Long time) {
        return (String.valueOf(time));
    }

    
    public Long getJavaObject(String str) {
        return (Long.parseLong(str));
    }

    
    public Object getMongoValue(Long time) {
        return (new Date(time));
    }

    public void setLastJoin(UUID update) {
        updateValueAsync(update, System.currentTimeMillis());
    }

    public long getLastJoin(UUID check) {
        return (contains(check) ? getValue(check) : 0L);
    }

}