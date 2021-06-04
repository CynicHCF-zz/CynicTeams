package com.cynichcf.hcf.team.permissions.menu.type.buttons;

import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PermissionPlayerButton extends Button {

    private Team team;
    private boolean isRally;
    private boolean isDisplay;
    private UUID member;


    public String getName(Player player) {
        return ChatColor.LIGHT_PURPLE + UUIDUtils.name(member);
    }


    public List<String> getDescription(Player player) {
        List<String> description = new ArrayList<>();
        if (isRally) {
            if (team.hasRallyPermission(member)) {
                description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.YELLOW + "Allowed");
                description.add("    " + ChatColor.YELLOW + "Disallowed");
            } else {
                description.add("    " + ChatColor.YELLOW + "Allowed");
                description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.YELLOW + "Disallowed");
            }
        } else if (isDisplay) {
                if (team.hasDisplayPermission(member)) {
                    description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.YELLOW + "Allowed");
                    description.add("    " + ChatColor.YELLOW + "Disallowed");
                } else {
                    description.add("    " + ChatColor.YELLOW + "Allowed");
                    description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.YELLOW + "Disallowed");
                }
            }else {
            if(team.hasSubclaimPermission(member)) {
                description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.YELLOW + "Allowed");
                description.add("    " + ChatColor.YELLOW + "Disallowed");
            } else {
                description.add("    " + ChatColor.YELLOW + "Allowed");
                description.add(ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► " + ChatColor.YELLOW + "Disallowed");
            }
        }

        return description;
    }


    public Material getMaterial(Player player) {
        return Material.SKULL_ITEM;
    }


    public byte getDamageValue(Player player) {
        return 3;
    }


    public void clicked(Player player, int slot, ClickType clickType) {
        boolean value;
        if(isRally) {
            value = !team.hasRallyPermission(member);
            team.setRallyPermission(player.getUniqueId(), member, value);
        }if(isDisplay) {
            value = !team.hasDisplayPermission(member);
            team.setDisplayPermission(player.getUniqueId(), member, value);
        }
        else {
            value = !team.hasSubclaimPermission(member);
            team.setSubclaimPermission(player.getUniqueId(), member, value);
        }
        team.flagForSave();
    }
}
