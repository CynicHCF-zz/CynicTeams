package com.cynichcf.hcf.events.koth.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.cynichcf.hcf.events.koth.KOTH;

@AllArgsConstructor
public class EventControlTickEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    @Getter private KOTH KOTH;

    public HandlerList getHandlers() {
        return (handlers);
    }

    public static HandlerList getHandlerList() {
        return (handlers);
    }

}