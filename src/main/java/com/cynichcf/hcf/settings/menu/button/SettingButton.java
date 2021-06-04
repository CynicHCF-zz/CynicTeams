package com.cynichcf.hcf.settings.menu.button;

import com.google.common.collect.Lists;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.tab.TabListMode;
import lombok.AllArgsConstructor;
import com.cynichcf.hcf.settings.Setting;
import rip.lazze.libraries.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

@AllArgsConstructor
public class SettingButton extends Button {

    private Setting setting;


    public String getName(Player player) {
        return setting.getName();
    }


    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();

        description.add("");
        description.addAll(setting.getDescription());
        description.add("");

        if (setting != Setting.TAB_LIST) {
            if (setting.isEnabled(player)) {
                description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + setting.getEnabledText());
                description.add("    " + setting.getDisabledText());
            } else {
                description.add("    " + setting.getEnabledText());
                description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + setting.getDisabledText());
            }
        } else {
            TabListMode current = HCF.getInstance().getTabListModeMap().getTabListMode(player.getUniqueId());

            for (TabListMode mode : TabListMode.values()) {
                if (mode != current) {
                    description.add("    " + ChatColor.GRAY + mode.getName());
                } else {
                    description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.GREEN + mode.getName());
                }
            }
        }

        return description;
    }


    public Material getMaterial(Player player) {
        return setting.getIcon();
    }


    public byte getDamageValue(Player player) {
        return 0;
    }


    public void clicked(Player player, int i, ClickType clickType) {
        setting.toggle(player);
    }

    public static TabListMode next(TabListMode current) {
        switch (current) {
            case DETAILED:
                return TabListMode.VANILLA;
            case DETAILED_WITH_FACTION_INFO:
                return TabListMode.DETAILED;
            case VANILLA:
                return TabListMode.DETAILED_WITH_FACTION_INFO;
            default:
                return TabListMode.DETAILED;
        }
    }

}
