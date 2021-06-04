package com.cynichcf.hcf.map.killstreaks.arcanetypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cynichcf.hcf.map.killstreaks.Killstreak;

public class Cobwebs extends Killstreak {

    
    public String getName() {
        return "Cobwebs";
    }

    
    public int[] getKills() {
        return new int[] {
                20
        };
    }

    
    public void apply(Player player) {
        give(player, new ItemStack(Material.WEB, 1));
    }

}
