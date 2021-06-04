package com.cynichcf.hcf.map.killstreaks.arcanetypes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.map.killstreaks.Killstreak;
import rip.lazze.libraries.util.ItemBuilder;

public class Fire2Sword extends Killstreak {


    public String getName() {
        return "Fire II sword";
    }


    public int[] getKills() {
        return new int[] {
                100
        };
    }


    public void apply(Player player) {
        give(player, ItemBuilder.of(Material.DIAMOND_SWORD).enchant(Enchantment.FIRE_ASPECT, 1).name("&b&c100 Killstreak Sword").build());
    }

}
