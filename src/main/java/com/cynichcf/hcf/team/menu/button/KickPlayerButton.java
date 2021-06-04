package com.cynichcf.hcf.team.menu.button;

import com.cynichcf.hcf.team.commands.ForceKickCommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class KickPlayerButton extends Button {

    @NonNull private UUID uuid;
    @NonNull private Team team;

    
    public String getName(Player player) {
        return "§cKick §e" + UUIDUtils.name(uuid);
    }

    
    public List<String> getDescription(Player player) {
        ArrayList<String> lore = new ArrayList<>();

        if (team.isOwner(uuid)) {
            lore.add("§e§lLeader");
        } else if (team.isCoLeader(uuid)) {
            lore.add("§e§lCo-Leader");
        } else if (team.isCaptain(uuid)) {
            lore.add("§aCaptain");
        } else {
            lore.add("§7Member");
        }

        lore.add("");
        lore.add("§eClick to kick §b" + UUIDUtils.name(uuid) + "§e from team.");

        return lore;
    }

    
    public byte getDamageValue(Player player) {
        return (byte) 3;
    }

    
    public Material getMaterial(Player player) {
        return Material.SKULL_ITEM;
    }

    
    public void clicked(Player player, int i, ClickType clickType) {
        ForceKickCommand.forceKick(player, uuid);
    }


}
