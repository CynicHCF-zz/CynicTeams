package com.cynichcf.hcf.events.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class EventDeactivatedEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    @Getter private com.cynichcf.hcf.events.Event event;

    public HandlerList getHandlers() {
        return (handlers);
    }

    public static HandlerList getHandlerList() {
        return (handlers);
    }

}