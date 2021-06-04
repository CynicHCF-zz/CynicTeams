//package com.cynichcf.hcf.credit.commands;
//
//import com.cynichcf.hcf.credit.CreditHandler;
//import com.cynichcf.hcf.credit.menu.CreditMenu;
//import com.cynichcf.hcf.HCF;
//import org.bukkit.ChatColor;
//import org.bukkit.OfflinePlayer;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import rip.lazze.libraries.command.Command;
//import rip.lazze.libraries.command.Param;
//
//public class CreditsCommand {
//
//    @Command(names = {"charm", "charms"}, permission = "")
//    public static void credits(Player sender) {
//      //  int nextCreditsSeconds = (int) ((CreditHandler.getPendingCredits().get(sender.getUniqueId()) - System.currentTimeMillis()) / 1_000L);
//        sender.sendMessage(ChatColor.WHITE + "You have " + ChatColor.GOLD + HCF.getInstance().getCreditsMap().getCredits(sender.getUniqueId()) + ChatColor.WHITE + " charms..");
//       // sender.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "You will receive another playtime charm in " + ChatColor.GOLD + TimeUtils.formatIntoDetailedString(nextCreditsSeconds) + ChatColor.YELLOW + ".");
//    }
//
//    @Command(names = {"charm check", "charms check"}, permission = "")
//    public static void creditsCheck(CommandSender sender, @Param(name = "player") Player player) {
//        sender.sendMessage(ChatColor.WHITE + player.getName() + " " + ChatColor.GOLD + "has " + ChatColor.WHITE + HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()) + ChatColor.GOLD + " charms.");
//    }
//
//    @Command(names = {"charm check", "charms check"}, permission = "")
//    public static void creditsCheckOffline(CommandSender sender, @Param(name = "player") OfflinePlayer player) {
//        sender.sendMessage(ChatColor.WHITE + player.getName() + " " + ChatColor.GOLD + "has " + ChatColor.WHITE + HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()) + ChatColor.GOLD + " charms.");
//    }
//
//    @Command(names = {"charms pay", "charm pay"}, permission = "")
//    public static void creditsPay(CommandSender sender, @Param(name = "player") Player player, @Param(name = "amount") int amount) {
//        if (amount < 0) {
//            sender.sendMessage(ChatColor.RED + "Please enter a valid number.");
//            return;
//        }
//        if (amount > CreditHandler.getPlayerCredits(sender.getServer().getPlayer(sender.getName()))) {
//            sender.sendMessage(ChatColor.RED + "You do not have enough charms.");
//            return;
//        }
//        Player payer = sender.getServer().getPlayer(sender.getName());
//
//        HCF.getInstance().getCreditsMap().setCredits(player.getUniqueId(), CreditHandler.getPlayerCredits(player) + amount);
//        HCF.getInstance().getCreditsMap().setCredits(payer.getUniqueId(), CreditHandler.getPlayerCredits(payer) - amount);
//        player.sendMessage(ChatColor.GOLD + "You have been given " + ChatColor.WHITE + amount + " charms" + ChatColor.GOLD + " from " + sender.getName());
//
//        sender.sendMessage(ChatColor.GOLD + "You have given " + ChatColor.WHITE + amount + " charms" + ChatColor.GOLD + " to " + player.getName());
//    }
//
//    @Command(names = {"charms set"}, permission = "foxtrot.charms")
//    public static void creditsSet(CommandSender sender, @Param(name = "player") Player player, @Param(name = "amount") int amount) {
//        if (amount < 0) {
//            sender.sendMessage(ChatColor.RED + "Please enter a valid number.");
//            return;
//        }
//
//        HCF.getInstance().getCreditsMap().setCredits(player.getUniqueId(), amount);
//        sender.sendMessage(ChatColor.YELLOW + "You have updated " + player.getDisplayName() + ChatColor.YELLOW + " " + amount + " charms!");
//    }
//
//    @Command(names = {"charms set"}, permission = "foxtrot.charms")
//    public static void creditsSetOffline(CommandSender sender, @Param(name = "player") OfflinePlayer player, @Param(name = "amount") int amount) {
//        if (amount < 0) {
//            sender.sendMessage(ChatColor.RED + "Please enter a valid number.");
//            return;
//        }
//        HCF.getInstance().getCreditsMap().setCredits(player.getUniqueId(), amount);
//        sender.sendMessage(ChatColor.YELLOW + "You have updated " + player.getName() + ChatColor.YELLOW + " " + amount + " charms!");
//    }
//
//    @Command(names = {"charms give"}, permission = "foxtrot.charms")
//    public static void creditsGive(CommandSender sender, @Param(name = "player") Player player, @Param(name = "amount") int amount) {
//        if (amount < 0) {
//            sender.sendMessage(ChatColor.RED + "Please enter a valid number.");
//            return;
//        }
//
//        HCF.getInstance().getCreditsMap().setCredits(player.getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()) + amount);
//        player.sendMessage(ChatColor.GOLD + "You have been given " + ChatColor.WHITE + amount + " charms" + ChatColor.GOLD + ".");
//    }
//
//
//    @Command(names = {"charms shop", "charm shop"}, permission = "")
//    public static void creditsShop(Player sender) {
//        new CreditMenu().openMenu(sender);
//    }
//}