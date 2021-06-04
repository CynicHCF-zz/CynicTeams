package com.cynichcf.hcf.map.killstreaks.velttypes;

import com.cynichcf.hcf.map.killstreaks.Killstreak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BountyKeys extends Killstreak {


    public String getName() {
        return "3 Bounty Keys";
    }


    public int[] getKills() {
        return new int[] {
                40
        };
    }


    public void apply(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr givekey " + player.getName() + " bounty 3");
    }

}
