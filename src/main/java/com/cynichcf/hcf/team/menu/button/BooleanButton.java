package com.cynichcf.hcf.team.menu.button;

import lombok.AllArgsConstructor;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.util.Callback;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BooleanButton extends Button {

    private boolean accept;
    private Callback<Boolean> callback;


    public void clicked(Player player, int i, ClickType clickType) {
        if (accept) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20f, 0.1f);
        } else {
            player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20f, 0.1F);
        }
        player.closeInventory();

        callback.callback(accept);
    }


    public String getName(Player player) {
        return accept ? "§aConfirm" : "§cCancel";
    }


    public List<String> getDescription(Player player) {
        return new ArrayList<>();
    }


    public byte getDamageValue(Player player) {
        return accept ? (byte) 5 : (byte) 14;
    }


    public Material getMaterial(Player player) {
        return Material.WOOL;
    }
}
