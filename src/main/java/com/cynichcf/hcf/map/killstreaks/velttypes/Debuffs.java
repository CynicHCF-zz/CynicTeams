package com.cynichcf.hcf.map.killstreaks.velttypes;

import com.cynichcf.hcf.map.killstreaks.Killstreak;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class Debuffs extends Killstreak {

    
    public String getName() {
        return "Debuffs";
    }

    
    public int[] getKills() {
        return new int[] {
                9
        };
    }

    
    public void apply(Player player) {
        Potion poison = new Potion(PotionType.POISON);
        poison.setSplash(true);

        Potion slowness = new Potion(PotionType.SLOWNESS);
        slowness.setSplash(true);

        give(player, poison.toItemStack(1));
        give(player, slowness.toItemStack(1));
    }

}
