package com.cynichcf.hcf.events.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import lombok.Getter;
import lombok.Setter;
import com.cynichcf.hcf.events.Event;

public class EventCapturedEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlers = new HandlerList();

    @Getter private Event event;
    @Getter @Setter private boolean cancelled;

    public EventCapturedEvent(Event event, Player capper) {
        super(capper);

        this.event = event;
    }

    public HandlerList getHandlers() {
        return (handlers);
    }

    public static HandlerList getHandlerList() {
        return (handlers);
    }

}