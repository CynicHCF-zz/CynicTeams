package com.cynichcf.hcf.challenges.buttons;

import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.challenges.menu.TierTwoMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import rip.lazze.libraries.menu.Button;

import java.util.Arrays;
import java.util.List;

public class TierTwoButton extends Button {

    public String getName(Player player) {
        return CC.translate("&e&lTier 2 Challenges");
    }

    public List<String> getDescription(Player player) {
        return Arrays.asList(
                CC.translate("&7&m-------------------------------------"),
                CC.translate("&7&oSemi-Hard challenges to complete"),
                "",
                CC.translate(" &e12 hour cooldown"),
                CC.translate(" &eSemi-Hard challenges to complete"),
                CC.translate(" &eGrind hard for charms!"),
                "",
                CC.translate("&7Right-click to open menu"),
                CC.translate("&7&m-------------------------------------"));
    }

    public Material getMaterial(Player player) {
        return Material.STONE_SWORD;
    }

    public void clicked(Player player, int slot, ClickType clickType) {
        new TierTwoMenu().openMenu(player);
    }
}
