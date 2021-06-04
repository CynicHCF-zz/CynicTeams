package com.cynichcf.hcf.settings.menu;

import com.google.common.collect.Maps;
import com.cynichcf.hcf.settings.Setting;
import com.cynichcf.hcf.settings.menu.display.DisplayButton;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.entity.Player;

import java.util.Map;

public class SettingsMenu extends Menu {


    public String getTitle(Player player) {
        return "Options";
    }


    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        // I'm going to manually set the positions for now as we only have three options to make it pretty,
        // but once we add more we should probably use a for loop to add the buttons.

        buttons.put(1, Setting.AUTOMATICALLY_F_DISPLAY.toButton());
        buttons.put(3, Setting.LFF_MESSAGES.toButton());
        buttons.put(5, new DisplayButton());
        buttons.put(7, Setting.FACTION_INVITES.toButton());


        if(player.hasPermission("foxtrot.staff")) {
            buttons.put(12, Setting.SCOREBOARD_STAFF_BOARD.toButton());
        }


        return buttons;
    }

}