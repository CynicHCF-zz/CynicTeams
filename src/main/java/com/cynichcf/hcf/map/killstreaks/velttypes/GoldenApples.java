package com.cynichcf.hcf.map.killstreaks.velttypes;

import com.cynichcf.hcf.map.killstreaks.Killstreak;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GoldenApples extends Killstreak {

    
    public String getName() {
        return "5 Golden Apples";
    }

    
    public int[] getKills() {
        return new int[] {
                3
        };
    }

    
    public void apply(Player player) {
        give(player, new ItemStack(Material.GOLDEN_APPLE, 5));
    }

}