package com.cynichcf.hcf.team.menu;

import com.cynichcf.hcf.team.menu.button.BooleanButton;
import lombok.AllArgsConstructor;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import rip.lazze.libraries.util.Callback;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ConfirmMenu extends Menu {
    private String title;
    private Callback<Boolean> response;


    
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            if (i == 3) {
                buttons.put(i, new BooleanButton(true, response));

            } else if (i == 5) {
                buttons.put(i, new BooleanButton(false, response));

            } else {
                buttons.put(i, Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 14));

            }
        }

        return buttons;
    }

    
    public String getTitle(Player player) {
        return title;
    }
}
