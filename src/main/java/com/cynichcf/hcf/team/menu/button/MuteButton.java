package com.cynichcf.hcf.team.menu.button;

import com.cynichcf.hcf.team.commands.team.TeamShadowMuteCommand;
import lombok.AllArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MuteButton extends Button {

    private int minutes;
    private Team team;

    
    public void clicked(Player player, int i, ClickType clickType) {
        TeamShadowMuteCommand.teamShadowMute(player, team, minutes);
    }

    
    public String getName(Player player) {
        return "§e" + minutes +"m mute";
    }

    
    public List<String> getDescription(Player player) {
        return new ArrayList<>();
    }

    
    public byte getDamageValue(Player player) {
        return (byte) 0;
    }

    
    public ItemStack getButtonItem(Player player) {
        ItemStack it = new ItemStack(getMaterial(player));
        ItemMeta im = it.getItemMeta();

        im.setLore(getDescription(player));
        im.setDisplayName(getName(player));
        it.setItemMeta(im);
        it.setAmount(minutes);

        return it;
    }

    
    public Material getMaterial(Player player) {
        return Material.CHEST;
    }
}
