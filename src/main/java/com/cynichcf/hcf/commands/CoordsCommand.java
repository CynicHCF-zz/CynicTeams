package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;

public class CoordsCommand {
    @Command(names = {"coords"})
    public static void Coords(Player player) {
        player.sendMessage(CC.translate("&7&m-----&7&m------------&7&m-------------------\n" +
                "    - &4&lCynicHCF &7- &c [Map 1] \n" +
                "    - &7&m-------------------------------------------------- \n" +
                "    - &6&lMountain&7:\n" +
                "    - &7* &eGlowstone Mountain&7: &fNether, 76, -731 \n" +
                "    - &6&lKoths&7:\n" +
                "    - &7* &cRuins Koth &7- &f500, 500 \n" +
                "    - &7* &cDojo Koth &7-  &f500, -500 \n" +
                "    - &7* &cTemple Koth &7- &f-500, 500 \n" +
                "    - &7* &cCitadel Koth &7- &f-500,-500 \n" +
                "    - &7* &cEnd Koth &7- &fEnd, -6, -69 \n" +
                "    - &7*  &cHell Koth &7- &fNether, 14, -500 \n" +
                "    - &c&lEnd Exit &7- &f250, 250 \n" +
                "    - &c&lEndportals &71000,1000 Each Quadrant \n" +
                "    - &7&m-----&7&m------------&7&m--------------------"));
    }
}
