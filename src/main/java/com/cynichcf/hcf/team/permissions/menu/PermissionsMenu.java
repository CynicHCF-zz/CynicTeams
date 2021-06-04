package com.cynichcf.hcf.team.permissions.menu;

import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.permissions.menu.type.PermissionTypeMenu;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionsMenu extends Menu {

    private Team team;

    public PermissionsMenu(Team team) {
        super("Team Permission nodes");
        this.team = team;
    }


    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttonMap = new HashMap<>();

        buttonMap.put(0, new Button() {

            public String getName(Player player) {
                return ChatColor.GREEN + "Sign Subclaim";
            }


            public List<String> getDescription(Player player) {
                return Collections.singletonList(ChatColor.WHITE + "Who can open ANY sign subclaim?");
            }


            public Material getMaterial(Player player) {
                return Material.CHEST;
            }


            public void clicked(Player player, int slot, ClickType clickType) {
                player.closeInventory();
                new PermissionTypeMenu(team, "Sign Subclaim").openMenu(player);
            }
        });

        buttonMap.put(1, new Button() {

            public String getName(Player player) {
                return ChatColor.GREEN + "Rally";
            }


            public List<String> getDescription(Player player) {
                return Collections.singletonList(ChatColor.WHITE + "Who can set the faction rally?");
            }


            public Material getMaterial(Player player) {
                return Material.BEACON;
            }


            public void clicked(Player player, int slot, ClickType clickType) {
                player.closeInventory();
                new PermissionTypeMenu(team, "Rally").openMenu(player);
            }
        });

        buttonMap.put(2, new Button() {

            public String getName(Player player) {
                return ChatColor.GREEN + "Display";
            }


            public List<String> getDescription(Player player) {
                return Collections.singletonList(ChatColor.WHITE + "Who can set faction displays?");
            }


            public Material getMaterial(Player player) {
                return Material.FIREBALL;
            }


            public void clicked(Player player, int slot, ClickType clickType) {
                player.closeInventory();
                new PermissionTypeMenu(team, "Display").openMenu(player);
            }
        });

        return buttonMap;
    }
}
