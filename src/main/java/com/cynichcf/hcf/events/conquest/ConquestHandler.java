package com.cynichcf.hcf.events.conquest;

import lombok.Getter;
import lombok.Setter;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.conquest.game.ConquestGame;
import org.bukkit.ChatColor;

public class ConquestHandler {

    public static String PREFIX = ChatColor.YELLOW + "[Conquest]";

    public static int POINTS_DEATH_PENALTY = 20;
    public static String KOTH_NAME_PREFIX = "Conquest-";
    public static int TIME_TO_CAP = 30;

    @Getter @Setter private ConquestGame game;

    public static int getPointsToWin() {
        return HCF.getInstance().getConfig().getInt("conquestWinPoints", 250);
    }
}