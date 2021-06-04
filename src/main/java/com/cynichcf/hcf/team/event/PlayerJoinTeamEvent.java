package com.cynichcf.hcf.team.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.cynichcf.hcf.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
@Getter
public class PlayerJoinTeamEvent extends Event {

	@Getter private static HandlerList handlerList = new HandlerList();

	private Player player;
	private Team team;

	
	public HandlerList getHandlers() {
		return handlerList;
	}

}
