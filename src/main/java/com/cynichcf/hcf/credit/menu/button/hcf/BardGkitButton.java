//package com.cynichcf.hcf.credit.menu.button.hcf;
//
//import com.google.common.collect.Lists;
////import com.cynichcf.hcf.credit.CreditHandler;
//import lombok.AllArgsConstructor;
//import com.cynichcf.hcf.HCF;
//import org.apache.commons.lang.StringUtils;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.inventory.ClickType;
//import rip.lazze.libraries.menu.Button;
//
//import java.util.List;
//
//
//
//@AllArgsConstructor
//public class BardGkitButton extends Button {
//
//    private static final int PRICE = 40;
//
//
//    public String getName(Player player) {
//        return ChatColor.GOLD.toString() + "One Time Use Bard Gkit";
//    }
//
//
//    public List<String> getDescription(Player player) {
//        List<String> lore = Lists.newArrayList();
//        lore.add(0, ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 30));
//        boolean afford = CreditHandler.getPlayerCredits(player) >= PRICE;
//        lore.add(ChatColor.GOLD + "Price: " + (afford ? ChatColor.GREEN + "" + PRICE + " Charms" : ChatColor.RED + "" + PRICE + " Charms"));
//        lore.add("");
//        lore.add(ChatColor.GRAY + "Click here to purchase this item!");
//        lore.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 30));
//        return lore;
//    }
//
//
//    public Material getMaterial(Player player) {
//        return Material.GOLD_CHESTPLATE;
//    }
//
//
//    public void clicked(Player player, int i, ClickType clickType) {
//        if (clickType.isRightClick() || clickType.isLeftClick()) {
//            if (CreditHandler.getPlayerCredits(player) < PRICE) {
//                player.sendMessage(ChatColor.RED + "You do not have enough charms to purchase this item");
//                return;
//            }
//
//            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gkit Bard " + player.getName());
//            player.sendMessage(ChatColor.GOLD + "You have have lost " + ChatColor.WHITE + PRICE + ChatColor.GOLD + " charms");
//            HCF.getInstance().getCreditsMap().setCredits(player.getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()) - PRICE);
//            player.closeInventory();
//        }
//    }
//}