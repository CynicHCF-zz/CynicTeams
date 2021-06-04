/*
 * Copyright (c) 2020.
 * Created by YoloSanta
 * Created On 10/22/20, 1:23 AM
 */

package com.cynichcf.hcf.tournaments;



public enum TournamentType {

    SUMO("SUMO", 0, "Sumo"),
    DIAMOND("DIAMOND", 1, "Diamond"),
    ARCHER("ARCHER", 2, "Archer"),
    ROGUE("ROGUE", 3, "Rogue"),
    AXE("AXE", 4, "Axe");


    private final String name;

    TournamentType(String s, int n, String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
