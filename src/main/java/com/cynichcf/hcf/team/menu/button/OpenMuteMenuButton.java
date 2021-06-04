package com.cynichcf.hcf.team.menu.button;

import com.cynichcf.hcf.commands.TeamManageCommand;
import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OpenMuteMenuButton extends Button {

    private Team team;

    
    public void clicked(Player player, int i, ClickType clickType) {
        TeamManageCommand.muteTeam(player, team);
    }

    
    public String getName(Player player) {
        return "§7Mute Team";
    }

    
    public List<String> getDescription(Player player) {
        return new ArrayList<>();
    }

    
    public byte getDamageValue(Player player) {
        return 0;
    }

    
    public Material getMaterial(Player player) {
        return Material.CHEST;
    }
}
