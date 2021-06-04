package com.cynichcf.hcf.map.killstreaks.arcanetypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cynichcf.hcf.map.killstreaks.Killstreak;

public class GoldenApples extends Killstreak {

    
    public String getName() {
        return "3 Golden Apples";
    }

    
    public int[] getKills() {
        return new int[] {
                3
        };
    }

    
    public void apply(Player player) {
        give(player, new ItemStack(Material.GOLDEN_APPLE, 3));
    }

}