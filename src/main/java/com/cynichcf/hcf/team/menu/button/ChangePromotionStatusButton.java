package com.cynichcf.hcf.team.menu.button;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.cynichcf.hcf.team.menu.ConfirmMenu;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ChangePromotionStatusButton extends Button {

    @NonNull private UUID uuid;
    @NonNull private Team team;
    @NonNull private boolean promote;


    public String getName(Player player) {
        return promote ? "§aPromote §e" + UUIDUtils.name(uuid) : "§cDemote §e" + UUIDUtils.name(uuid);
    }


    public List<String> getDescription(Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(promote ? "§eClick to promote §b" + UUIDUtils.name(uuid) + "§e to captain" : "§eClick to demote §b" + UUIDUtils.name(uuid) + "§e to member");
        return lore;
    }


    public byte getDamageValue(Player player) {
        return (byte) 3;
    }


    public Material getMaterial(Player player) {
        return Material.SKULL_ITEM;
    }


    public void clicked(Player player, int i, ClickType clickType) {
        if (promote) {
            String newRank;
            if(team.isCaptain(uuid)) {
                newRank = "co-leader";
            } else {
                newRank = "captain";
            }
            new ConfirmMenu("Make " + UUIDUtils.name(uuid) + " " + newRank + "?", (b) -> {
                if (b) {
                    if(team.isCaptain(uuid)) {
                        team.removeCaptain(uuid);
                        team.addCoLeader(uuid);
                    } else {
                        team.addCaptain(uuid);
                    }
                    Player bukkitPlayer= Bukkit.getPlayer(uuid);

                    if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
                        bukkitPlayer.sendMessage(ChatColor.YELLOW + "A staff member has made you a §a" + newRank + " §eof your team.");
                    }

                    player.sendMessage(ChatColor.YELLOW + UUIDUtils.name(uuid) + " has been made a " + newRank + " of " + team.getName() + ".");
                }
            }).openMenu(player);
        } else {
            new ConfirmMenu("Make " + UUIDUtils.name(uuid) + " member?", (b) -> {
                if (b) {
                    team.removeCaptain(uuid);
                    team.removeCoLeader(uuid);

                    Player bukkitPlayer= Bukkit.getPlayer(uuid);

                    if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
                        bukkitPlayer.sendMessage(ChatColor.YELLOW + "A staff member has made you a §bmember §eof your team.");
                    }

                    player.sendMessage(ChatColor.YELLOW + UUIDUtils.name(uuid) + " has been made a member of " + team.getName() + ".");
                }
            }).openMenu(player);
        }
    }
}