package com.cynichcf.hcf.team.claims;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Coordinate {

    @Getter @Setter int x;
    @Getter @Setter int z;


    public String toString() {
        return (x + ", " + z);
    }

}