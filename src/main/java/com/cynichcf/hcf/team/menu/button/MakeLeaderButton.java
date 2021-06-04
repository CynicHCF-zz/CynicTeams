package com.cynichcf.hcf.team.menu.button;

import com.cynichcf.hcf.team.commands.ForceLeaderCommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.cynichcf.hcf.team.menu.ConfirmMenu;
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

@RequiredArgsConstructor
public class MakeLeaderButton extends Button {

    @NonNull private UUID uuid;
    @NonNull private Team team;

    
    public String getName(Player player) {
        return (team.isOwner(uuid) ? "§a§l" : "§7") + UUIDUtils.name(uuid);
    }

    
    public List<String> getDescription(Player player) {
        ArrayList<String> lore = new ArrayList<>();

        if (team.isOwner(uuid)) {
            lore.add("§aThis player is already the leader!");
        } else {
            lore.add("§eClick to change §b" + team.getName() + "§b's§e leader");
            lore.add("§eto §6" + UUIDUtils.name(uuid));
        }

        return lore;
    }

    
    public byte getDamageValue(Player player) {
        return (byte) 3;
    }

    
    public Material getMaterial(Player player) {
        return Material.SKULL_ITEM;
    }

    
    public void clicked(Player player, int i, ClickType clickType) {
        if (team.isOwner(uuid)) {
            player.sendMessage(ChatColor.RED + "That player is already the leader!");
            return;
        }

        new ConfirmMenu("Make " + UUIDUtils.name(uuid) + " leader?", (b) -> {
            if (b) {
                ForceLeaderCommand.forceLeader(player, uuid);

            }
        }).openMenu(player);


    }


}
