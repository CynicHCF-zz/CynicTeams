package com.cynichcf.hcf.challenges.buttons;

import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.challenges.menu.TierOneMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import rip.lazze.libraries.menu.Button;

import java.util.Arrays;
import java.util.List;

public class TierOneButton extends Button {

    public String getName(Player player) {
        return CC.translate("&a&lTier 1 Challenges");
    }

    public List<String> getDescription(Player player) {
        return Arrays.asList(
                CC.translate("&7&m-------------------------------------"),
                CC.translate("&7&oEasiest challenges to complete"),
                "",
                CC.translate(" &eNo challenge cooldown"),
                CC.translate(" &eEasy challenges to complete"),
                CC.translate(" &eGrind hard for charms!"),
                "",
                CC.translate("&7Right-click to open menu"),
                CC.translate("&7&m-------------------------------------"));

    }

    public Material getMaterial(Player player) {
        return Material.WOOD_SWORD;
    }


    public void clicked(Player player, int slot, ClickType clickType) {
        new TierOneMenu().openMenu(player);
    }
}
