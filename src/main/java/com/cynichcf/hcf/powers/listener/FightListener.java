package com.cynichcf.hcf.powers.listener;

import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.powers.events.FightEndEvent;
import com.cynichcf.hcf.powers.events.FightStartEvent;
import com.cynichcf.hcf.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FightListener implements Listener {

    @EventHandler
    public void onStart(FightStartEvent event) {
        Bukkit.broadcastMessage(CC.translate("&6[TeamFights] &9" + event.getTeam1().getName() + " and " + event.getTeam2().getName() + " have entered a fight."));

        new BukkitRunnable() {

            
            public void run() {
                HCF.getInstance().getFightHandler().removeFight(event.getId());
            }
        }.runTaskLaterAsynchronously(HCF.getInstance(), 20 * 60); // 30m
    }

    @EventHandler
    public void onEnd(FightEndEvent event) {
        Bukkit.broadcastMessage(CC.translate("&6[TeamFights] &9" + event.getTeam1().getName() + " and " + event.getTeam2().getName() + " have exited a fight."));
        if (HCF.getInstance().getFightHandler().getFight(event.getId()).getDeathsduring() != null) {
            if (HCF.getInstance().getFightHandler().getFight(event.getId()).getDeathsduring().get(event.getTeam1()) > HCF.getInstance().getFightHandler().getFight(event.getId()).getDeathsduring().get(event.getTeam2())) {
                Bukkit.broadcastMessage(CC.translate("&6[TeamFights] &9" + event.getTeam1().getName() + " has won the fight!"));
                event.getTeam1().setTeamfightsWon(event.getTeam1().getTeamfightsWon() + 1);
            } else {
                Bukkit.broadcastMessage(CC.translate("&6[TeamFights] &9" + event.getTeam2().getName() + " has won the fight!"));
                event.getTeam2().setTeamfightsWon(event.getTeam2().getTeamfightsWon() + 1);
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Team team = HCF.getInstance().getTeamHandler().getTeam(event.getEntity());
        if (team == null) return;

        if (HCF.getInstance().getFightHandler().getFights().contains(team.getName())) {
            if (HCF.getInstance().getFightHandler().getFight(team.getName()).getDeathsduring().get(team) != null) {
                HCF.getInstance().getFightHandler().getFight(team.getName()).getDeathsduring().put(team, HCF.getInstance().getFightHandler().getFight(team.getName()).getDeathsduring().get(team) + 1);
            } else {
                HCF.getInstance().getFightHandler().getFight(team.getName()).getDeathsduring().put(team, 1);
            }

        }

    }
}
