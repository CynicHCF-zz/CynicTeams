package com.cynichcf.hcf.giveaway;

import com.cynichcf.hcf.HCF;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.lazze.libraries.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class GiveawayHandler {

    private boolean active;

    private String word;
    private int number;

    private List<UUID> entered = new ArrayList<>();

    public GiveawayHandler() {
        Bukkit.getServer().getPluginManager().registerEvents(new GiveawayListener(), HCF.getInstance());
    }


    public Player pickPlayer() {
        int r = Library.RANDOM.nextInt(entered.size());

        if (Bukkit.getPlayer(entered.get(r)) == null) {
            pickPlayer();
        }

        return Bukkit.getPlayer(entered.get(r));
    }

}