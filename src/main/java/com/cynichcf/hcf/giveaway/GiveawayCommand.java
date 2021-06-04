package com.cynichcf.hcf.giveaway;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import rip.lazze.libraries.Library;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class GiveawayCommand {

    @Command(names = { "giveaway create", "giveaway start number" }, permission = "giveaway.command", async = true)
    public static void giveaway(CommandSender sender, @Param(name = "number")int number) {
        int random = Library.RANDOM.nextInt(number
                - 1);


        HCF.getInstance().getGiveawayHandler().setActive(true);
        HCF.getInstance().getGiveawayHandler().setNumber(random);

        Bukkit.broadcastMessage(CC.translate("&7"));
        Bukkit.broadcastMessage(CC.translate("&7"));
        Bukkit.broadcastMessage(CC.translate(" &6» &eA random number between &f0-" + number + " &ehas been chosen."));
        Bukkit.broadcastMessage(CC.translate(" &6» &eThe first person to guess the number will win a prize."));
        Bukkit.broadcastMessage(CC.translate(" &6» &eThe chat will be unmuted in &f3 seconds&e."));
        Bukkit.broadcastMessage(CC.translate("&7"));
        Bukkit.broadcastMessage(CC.translate("&7"));

        sender.sendMessage("Number: " + random);
        System.out.println("NUMBER: " + random);
        System.out.println("NUMBER: " + random);
        System.out.println("NUMBER: " + random);
        System.out.println("NUMBER: " + random);
        System.out.println("NUMBER: " + random);
        System.out.println("NUMBER: " + random);
        System.out.println("NUMBER: " + random);

        Bukkit.getScheduler().runTaskLater(HCF.getInstance(), () -> Bukkit.broadcastMessage(CC.translate(" &6» &eThe chat is &fno longer &emuted. You can now start guessing.")), 58L);
    }

    @Command(names = { "giveaway create", "giveaway start raffle" }, permission = "giveaway.command", async = true)
    public static void giveaway(CommandSender sender, @Param(name = "word")String word) {

        HCF.getInstance().getGiveawayHandler().setActive(true);
        HCF.getInstance().getGiveawayHandler().setWord(word);

        Bukkit.broadcastMessage(CC.translate("&7"));
        Bukkit.broadcastMessage(CC.translate("&7"));
        Bukkit.broadcastMessage(CC.translate(" &6» &eA word has been selected for the raffle."));
        Bukkit.broadcastMessage(CC.translate(" &6» &eYou must type &f" + word + "&e to enter the giveaway."));
        Bukkit.broadcastMessage(CC.translate(" &6» &eThe chat will be unmuted in &f3 seconds&e."));
        Bukkit.broadcastMessage(CC.translate("&7"));
        Bukkit.broadcastMessage(CC.translate("&7"));

        Bukkit.getScheduler().runTaskLater(HCF.getInstance(), () -> Bukkit.broadcastMessage(CC.translate(" &6» &eThe chat is &fno longer &emuted. You can now enter.")), 58L);
    }

    @Command(names = { "giveaway cancel", "giveaway draw" }, permission = "giveaway.command", async = true)
    public static void giveawaycancel(CommandSender sender) {
        if (HCF.getInstance().getGiveawayHandler().getWord() == null) {
            HCF.getInstance().getGiveawayHandler().setActive(false);
        } else {
            Bukkit.broadcastMessage(CC.translate("&7"));
            Bukkit.broadcastMessage(CC.translate(" &6» &eThe raffle was ended. The winner is " + sender.getName() + "."));
            Bukkit.broadcastMessage(CC.translate("&7"));


            HCF.getInstance().getGiveawayHandler().setActive(false);
            HCF.getInstance().getGiveawayHandler().setWord(null);
            HCF.getInstance().getGiveawayHandler().getEntered().clear();
        }
    }
}
