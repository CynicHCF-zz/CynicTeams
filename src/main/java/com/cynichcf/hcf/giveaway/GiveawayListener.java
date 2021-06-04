package com.cynichcf.hcf.giveaway;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GiveawayListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (HCF.getInstance().getGiveawayHandler().isActive()) {
            if (HCF.getInstance().getGiveawayHandler().getWord() == null) {
                try {
                    int i = Integer.parseInt(event.getMessage());

                    if (i == HCF.getInstance().getGiveawayHandler().getNumber()) {
                        event.setCancelled(true);
                        Bukkit.broadcastMessage(CC.translate("&7"));
                        Bukkit.broadcastMessage(CC.translate(" &6» &eThe game was won by " + event.getPlayer().getName() + "&e."));
                        Bukkit.broadcastMessage(CC.translate(" &6» &eThe number was &f" + HCF.getInstance().getGiveawayHandler().getNumber() + "&e."));
                        Bukkit.broadcastMessage(CC.translate("&7"));


                        HCF.getInstance().getGiveawayHandler().setActive(false);
                        HCF.getInstance().getGiveawayHandler().setNumber(0);
                    }
                } catch (NumberFormatException e) {}
            } else {
                if (event.getMessage().equalsIgnoreCase(HCF.getInstance().getGiveawayHandler().getWord()) && !HCF.getInstance().getGiveawayHandler().getEntered().contains(event.getPlayer().getUniqueId())) {
                    Bukkit.broadcastMessage(CC.translate(" &6» " +event.getPlayer().getName()+ " &ehas entered the raffle by typing &f" + HCF.getInstance().getGiveawayHandler().getWord() + "."));
                    HCF.getInstance().getGiveawayHandler().getEntered().add(event.getPlayer().getUniqueId());
                    event.setCancelled(true);
                }
            }
        }
    }
}
