package com.cynichcf.hcf.team.menu;

import com.cynichcf.hcf.team.menu.button.DisbandTeamButton;
import com.cynichcf.hcf.team.menu.button.OpenKickMenuButton;
import com.cynichcf.hcf.team.menu.button.OpenMuteMenuButton;
import com.cynichcf.hcf.team.menu.button.RenameButton;
import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class TeamManageMenu extends Menu {

    private Team team;


    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            if (i == 1) {
                buttons.put(i, new RenameButton(team));

            } else if (i == 3) {
                buttons.put(i, new OpenMuteMenuButton(team));

            } else if (i == 5) {
                buttons.put(i, new OpenKickMenuButton(team));

            } else if (i == 7) {
                buttons.put(i, new DisbandTeamButton(team));

            } else {
                buttons.put(i, Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 14));

            }
        }

        return buttons;
    }


    public String getTitle(Player player) {
        return "Manage " + team.getName();
    }
}
