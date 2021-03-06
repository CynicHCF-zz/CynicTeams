package com.cynichcf.hcf.team;

import com.cheatbreaker.api.object.CBWaypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@AllArgsConstructor @Getter
public class RallyPoint {

    private Location location;
    private CBWaypoint cbWaypoint;
    private Long timeCreated;

}
