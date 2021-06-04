package com.cynichcf.hcf.powers;

import com.cynichcf.hcf.powers.listener.FightListener;
import lombok.Getter;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.powers.events.FightEndEvent;
import com.cynichcf.hcf.powers.events.FightStartEvent;
import com.cynichcf.hcf.powers.listener.FightDamageListener;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.util.TimeUtils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FightHandler {

    public FightHandler() {
        Bukkit.getPluginManager().registerEvents(new FightDamageListener(), HCF.getInstance());
        Bukkit.getPluginManager().registerEvents(new FightListener(), HCF.getInstance());

    }

    @Getter private ConcurrentHashMap<String, Fight> fightMap = new ConcurrentHashMap<>();

    public List<Fight> getFights() {
        return (new ArrayList<>(fightMap.values()));
    }

    public Fight getFight(String id) {
        return fightMap.get(id);
    }

    public Team getTeamOne(String id) {
        return fightMap.get(id).getTeam1();
    }

    public Team getTeamTwo(String id) {
        return fightMap.get(id).getTeam2();
    }

    public String getDuration(String id) {
        return TimeUtils.formatLongIntoHHMMSS(System.currentTimeMillis() - fightMap.get(id).getStarted());
    }

    public int getHits(String id, Team team) {
        return fightMap.get(id).getHits().get(team);
    }

    public void createFight(Fight fight) {
        fightMap.put(fight.getId(), fight);
        Bukkit.getPluginManager().callEvent(new FightStartEvent(fight.getId(), fight.getTeam1(), fight.getTeam2()));
    }

    public void removeFight(String id) {
        Fight fight = HCF.getInstance().getFightHandler().getFight(id);
        fightMap.remove(id);
        Bukkit.getPluginManager().callEvent(new FightEndEvent(fight.getId(), fight.getTeam1(), fight.getTeam2()));
    }
}
