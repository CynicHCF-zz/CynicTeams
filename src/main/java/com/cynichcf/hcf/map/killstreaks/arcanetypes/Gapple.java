package com.cynichcf.hcf.map.killstreaks.arcanetypes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cynichcf.hcf.map.killstreaks.Killstreak;

public class Gapple extends Killstreak {


    public String getName() {
        return "OP Apple";
    }


    public int[] getKills() {
        return new int[] {
                40
        };
    }


    public void apply(Player player) {
        give(player, new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1));
    }

}
