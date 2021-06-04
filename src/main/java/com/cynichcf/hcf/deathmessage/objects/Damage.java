package com.cynichcf.hcf.deathmessage.objects;

import com.cynichcf.hcf.HCF;
import lombok.Getter;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.ChatColor;

public abstract class Damage {

    @Getter private String damaged;
    @Getter private double damage;
    @Getter private long time;

    public Damage(String damaged, double damage) {
        this.damaged = damaged;
        this.damage = damage;
        this.time = System.currentTimeMillis();
    }

    public abstract String getDeathMessage();

    public String wrapName(String player) {
        int kills = HCF.getInstance().getMapHandler().isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap() ? HCF.getInstance().getMapHandler().getStatsHandler().getStats(player).getKills() : HCF.getInstance().getKillsMap().getKills(UUIDUtils.uuid(player));

        return (ChatColor.RED + player + ChatColor.DARK_RED + "[" + kills + "]" + ChatColor.YELLOW);
    }

    public long getTimeDifference() {
        return System.currentTimeMillis() - time;
    }

}