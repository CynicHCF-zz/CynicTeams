package com.cynichcf.hcf.server.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.potion.Potion;

public class DisallowedPotionDrinkEvent extends PlayerEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    @Getter private Potion potion;
    @Getter @Setter private boolean allowed = false;

    public DisallowedPotionDrinkEvent(Player who, Potion potion) {
        super(who);
        this.potion = potion;
    }

    
    public HandlerList getHandlers() {
        return handlerList;
    }

}