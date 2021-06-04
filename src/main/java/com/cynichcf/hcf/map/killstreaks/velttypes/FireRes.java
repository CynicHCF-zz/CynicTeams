package com.cynichcf.hcf.map.killstreaks.velttypes;

import com.cynichcf.hcf.map.killstreaks.PersistentKillstreak;
import rip.lazze.libraries.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FireRes extends PersistentKillstreak {

    public FireRes() {
        super("Fire Resistance", 6);
    }

    public void apply(Player player) {
        player.getInventory().addItem(ItemBuilder.of(Material.POTION).data((short) 8227).build());
    }
    
}
