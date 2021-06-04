package com.cynichcf.hcf.team.menu.button;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;

@AllArgsConstructor
public class DTRButton extends Button {
    
    Team team;
    boolean increase;

    
    public void clicked(Player player, int i, ClickType clickType) {
        if (!increase && (team.getDTR() - 1) <= 0 && !player.hasPermission("foxtrot.dtr.setraidable")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to set teams as raidable. This has been logged.");
            return;
        }

        if (increase && team.getMaxDTR() <= team.getDTR() + 1) {
            player.sendMessage(ChatColor.RED + "This would put the team above their maximum DTR. This has been logged.");
            return;
        }

        if (increase) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20f, 0.1f);
            team.setDTR(team.getDTR() + 1, player);
        } else {
            team.setDTR(team.getDTR() - 1, player);
            player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20f, 0.1F);
        }
        player.closeInventory();
        
    }

    
    public String getName(Player player) {
        return increase ? "§aIncrease by 1.0" : "§cDecrease by 1.0";
    }

    
    public List<String> getDescription(Player player) {
        return new ArrayList<>();
    }

    
    public byte getDamageValue(Player player) {
        return increase ? (byte) 5 : (byte) 14;
    }

    
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }
}