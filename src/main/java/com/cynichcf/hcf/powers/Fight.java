package com.cynichcf.hcf.powers;

import lombok.Getter;
import lombok.Setter;
import com.cynichcf.hcf.team.Team;

import java.util.HashMap;
import java.util.Map;

public class Fight {

    @Getter private String id;

    @Getter private Team team2;

    @Getter private Team team1;

    @Setter
    @Getter private Long started;

    @Getter private Map<Team, Integer> hits;

    @Getter private Map<Team, Integer> deathsduring;

    public Fight(String id, Team team1, Team team2, Long started, int deathsduring) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.started = started;
        this.hits = new HashMap<>();
        this.deathsduring = new HashMap<>();
    }

}
