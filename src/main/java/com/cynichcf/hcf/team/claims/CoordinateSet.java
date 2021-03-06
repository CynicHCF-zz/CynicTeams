package com.cynichcf.hcf.team.claims;

public class CoordinateSet {

    public static int BITS = 6;
    private int x;
    private int z;

    public CoordinateSet(int x, int z) {
        this.x = x >> BITS;
        this.z = z >> BITS;
    }

    
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return (false);
        }

        CoordinateSet other = (CoordinateSet) obj;

        return (other.x == x) && (other.z == z);
    }

    
    public int hashCode() {
        int hash = 5;

        hash = 37 * hash + x;
        hash = 37 * hash + z;

        return (hash);
    }

}