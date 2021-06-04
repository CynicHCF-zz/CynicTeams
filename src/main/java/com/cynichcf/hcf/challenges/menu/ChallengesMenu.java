package com.cynichcf.hcf.challenges.menu;


import com.cynichcf.hcf.challenges.buttons.TierOneButton;
import com.cynichcf.hcf.challenges.buttons.TierThreeButton;
import com.cynichcf.hcf.challenges.buttons.TierTwoButton;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import java.util.HashMap;
import java.util.Map;

public class ChallengesMenu extends Menu {

    {
        setPlaceholder(true);
        setAutoUpdate(true);
    }

    
    public String getTitle(Player player) {
        return ChatColor.GOLD + "Challenges";
    }

    
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(2, new TierOneButton());
        buttons.put(4, new TierTwoButton());
        buttons.put(6, new TierThreeButton());

        return buttons;
    }
}
