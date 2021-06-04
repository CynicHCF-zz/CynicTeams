package com.cynichcf.hcf.persist.maps;

import java.util.UUID;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.persist.PersistMap;

public class KDRMap extends PersistMap<Double> {

    public KDRMap() {
        super("KDR", "KDR");
    }

    
    public String getRedisValue(Double kdr) {
        return (String.valueOf(kdr));
    }

    
    public Double getJavaObject(String str) {
        return (Double.parseDouble(str));
    }

    
    public Object getMongoValue(Double kdr) {
        return (kdr);
    }

    public void setKDR(UUID update, double kdr) {
        updateValueAsync(update, kdr);
    }

    public void updateKDR(UUID update) {
        setKDR(update, Math.max(((double) HCF.getInstance().getKillsMap().getKills(update)) / Math.max(HCF.getInstance().getDeathsMap().getDeaths(update), 1), 0));
    }
}
