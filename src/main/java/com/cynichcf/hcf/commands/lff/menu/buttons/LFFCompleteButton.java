package com.cynichcf.hcf.commands.lff.menu.buttons;

import lombok.AllArgsConstructor;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.commands.lff.LFFCommand;
import com.cynichcf.hcf.commands.lff.menu.LFFMenu;
import rip.lazze.libraries.menu.Button;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class LFFCompleteButton extends Button {

    private LFFMenu lffMenu;


    public String getName(Player player) {
        return ChatColor.GREEN + "Complete";
    }


    public List<String> getDescription(Player player) {
        List<String> lore = new ArrayList<>(Collections.singletonList(""));
        if(lffMenu.getSelected().isEmpty()) lore.add(ChatColor.RED + "You don't have classes selected!");
        else {
            lore.add(ChatColor.RED + "You have selected:");
            lffMenu.getSelected().forEach(lffKitButton -> lore.add(ChatColor.RED.toString() + ChatColor.BOLD + "  ► " + ChatColor.RED + lffKitButton));
        }
        return lore;
    }


    public Material getMaterial(Player player) {
        return Material.EMERALD_BLOCK;
    }


    public void clicked(Player player, int slot, ClickType clickType) {
        if(lffMenu.getSelected().isEmpty()) {
            player.sendMessage(ChatColor.RED + "You do not have any classes selected!");
            return;
        }

        player.closeInventory();
        LFFCommand.applyCooldown(player.getUniqueId());

        Bukkit.getOnlinePlayers().stream().filter(aPlayer -> HCF.getInstance().getToggleLFFMessageMap().isEnabled(aPlayer.getUniqueId())).forEach(aPlayer -> {
            aPlayer.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 43));
            aPlayer.sendMessage(player.getDisplayName() + ChatColor.WHITE + " is " + ChatColor.RED + "looking for a faction" + ChatColor.WHITE + "!");
            aPlayer.sendMessage(ChatColor.GRAY + " » " + ChatColor.DARK_RED + "Classes: " + ChatColor.WHITE + StringUtils.join(lffMenu.getSelected(), ChatColor.WHITE + ", "));
            aPlayer.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 43));
        });


    }
}
