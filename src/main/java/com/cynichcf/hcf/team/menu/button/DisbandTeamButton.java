package com.cynichcf.hcf.team.menu.button;

import com.cynichcf.hcf.team.commands.ForceDisbandCommand;
import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.menu.ConfirmMenu;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DisbandTeamButton extends Button {

    private Team team;


    public void clicked(Player player, int i, ClickType clickType) {
        new ConfirmMenu("Disband?", (b) -> {
            if (b) {
                ForceDisbandCommand.forceDisband(player, team);
            }
        }).openMenu(player);
    }


    public String getName(Player player) {
        return "§c§lDisband Team";
    }


    public List<String> getDescription(Player player) {
        return new ArrayList<>();
    }


    public byte getDamageValue(Player player) {
        return 0;
    }


    public Material getMaterial(Player player) {
        return Material.TNT;
    }
}
