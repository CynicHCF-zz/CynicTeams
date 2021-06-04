package com.cynichcf.hcf.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysql.jdbc.StringUtils;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.ParameterType;

public class EventParameterType implements ParameterType<Event> {

    public Event transform(CommandSender sender, String source) {
        if (source.equals("active")) {
            for (Event event : HCF.getInstance().getEventHandler().getEvents()) {
                if (event.isActive() && !event.isHidden()) {
                    return event;
                }
            }

            sender.sendMessage(ChatColor.RED + "There is no active Event at the moment.");

            return null;
        }

        Event event = HCF.getInstance().getEventHandler().getEvent(source);

        if (event == null) {
            sender.sendMessage(ChatColor.RED + "No Event with the name " + source + " found.");
            return (null);
        }

        return (event);
    }

    public List<String> tabComplete(Player sender, Set<String> flags, String source) {
        List<String> completions = new ArrayList<>();

        for (Event event : HCF.getInstance().getEventHandler().getEvents()) {
            if (StringUtils.startsWithIgnoreCase(event.getName(), source)) {
                completions.add(event.getName());
            }
        }

        return (completions);
    }

}