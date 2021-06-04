package com.cynichcf.hcf.team.upgrades.menu;

import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.enums.Upgrades;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamUpgradesMenu extends Menu {

    private Upgrades upgrades;
    private Team team;

    public TeamUpgradesMenu(Team team) {
        super("Team Upgrades");
        this.team = team;
    }


    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttonMap = new HashMap<>();
        int i = 0;
        for (Upgrades upgrades : Upgrades.values()) {
            buttonMap.put(i++, new Button() {

                public String getName(Player player) {
                    return upgrades.getColor() + ChatColor.BOLD.toString() + upgrades.getName();
                }


                public List<String> getDescription(Player player) {
                    return upgrades.getDescription();
                }


                public Material getMaterial(Player player) {
                    return upgrades.getMaterial();
                }


                public void clicked(Player player, int slot, ClickType clickType) {
                    if (team.getPoints() < upgrades.getPtsrequired()) {
                        player.sendMessage(CC.translate("&eYou don't have enough points for the &l" + upgrades.getName() + "&e upgrade."));
                        return;
                    }
                    if (upgrades.isEnabled() == true) {
                        player.sendMessage(CC.translate("&eYou already have the " + upgrades.getName() + " upgrade."));
                        return;
                    }
                    upgrades.setEnabled(true);

                }
            });
        }

        return buttonMap;
    }
}
