package com.cynichcf.hcf.settings.commands;

import com.cynichcf.hcf.settings.menu.SettingsMenu;
import rip.lazze.libraries.command.Command;
import org.bukkit.entity.Player;

public class SettingsCommand {

    @Command(names = {"settings", "options"}, permission = "")
    public static void settings(Player sender) {
        new SettingsMenu().openMenu(sender);
    }

}
