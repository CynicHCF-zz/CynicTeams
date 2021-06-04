package com.cynichcf.hcf.tasks;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RallyExpireTask extends BukkitRunnable {

    
    public void run() {
        for (Team team : HCF.getInstance().getTeamHandler().getTeams()) {
            if(team.isRallyActive()) return;
            else if(!team.isRallyActive() && team.getRally() != null) {
                for (Player onlineMember : team.getOnlineMembers()) {
                    CheatBreakerAPI.getInstance().removeWaypoint(onlineMember, team.getRally().getCbWaypoint());
                }
                team.setRally(null);
            }
        }
    }
}
