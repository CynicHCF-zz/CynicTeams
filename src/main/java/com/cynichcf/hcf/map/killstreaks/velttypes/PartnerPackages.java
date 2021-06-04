package com.cynichcf.hcf.map.killstreaks.velttypes;

import com.cynichcf.hcf.map.killstreaks.Killstreak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartnerPackages extends Killstreak {


    public String getName() {
        return "3 Partner Packages";
    }


    public int[] getKills() {
        return new int[] {
                25
        };
    }


    public void apply(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "package give " + player.getName() + "3");
    }

}
