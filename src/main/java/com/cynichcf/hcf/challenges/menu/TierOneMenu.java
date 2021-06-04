package com.cynichcf.hcf.challenges.menu;

import com.cynichcf.hcf.challenges.ChallengeTypes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;

import java.util.HashMap;
import java.util.Map;

public class TierOneMenu extends Menu {

    {
        setPlaceholder(true);
        setAutoUpdate(true);
    }


    public String getTitle(Player player) {
        return ChatColor.GREEN + "Tier 1 Challenges";
    }


    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for (ChallengeTypes challengeType : ChallengeTypes.values()) {
            if (challengeType.getChallengeTier() == 1) {
                buttons.put(buttons.size(), Button.fromItem(challengeType.getCusomItem(player)));
            }
        }
        return buttons;
    }
}
