package com.cynichcf.hcf.team.menu;

import com.cynichcf.hcf.team.menu.button.MuteButton;
import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class MuteMenu extends Menu {
    private Team team;



    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            if (i == 1) {
                buttons.put(i, new MuteButton(5, team));

            } else if (i == 3) {
                buttons.put(i, new MuteButton(15, team));

            } else if (i == 5) {
                buttons.put(i, new MuteButton(30, team));

            } else if (i == 7) {
                buttons.put(i, new MuteButton(60, team));

            } else {
                buttons.put(i, Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 14));

            }
        }

        return buttons;
    }


    public String getTitle(Player player) {
        return "Mute " + team.getName();
    }
}
