package com.cynichcf.hcf.events.citadel.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.citadel.CitadelHandler;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CitadelSaveLoottableCommand {

    @Command(names={"citadel saveloottable"}, permission="op")
    public static void citadelSaveLoottable(Player sender) {
        HCF.getInstance().getCitadelHandler().getCitadelLoot().clear();

        for (ItemStack itemStack : sender.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                HCF.getInstance().getCitadelHandler().getCitadelLoot().add(itemStack);
            }
        }

        HCF.getInstance().getCitadelHandler().saveCitadelInfo();
        sender.sendMessage(CitadelHandler.PREFIX + " " + ChatColor.YELLOW + "Saved Citadel loot from your inventory.");
    }

}