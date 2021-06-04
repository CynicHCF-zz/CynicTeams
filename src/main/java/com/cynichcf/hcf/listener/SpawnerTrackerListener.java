package com.cynichcf.hcf.listener;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.claims.Claim;
import com.cynichcf.hcf.team.claims.LandBoard;
import com.cynichcf.hcf.server.event.CrowbarSpawnerBreakEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerTrackerListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		if (event.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
			Team team = HCF.getInstance().getTeamHandler().getTeam(event.getPlayer());

			if (team != null) {
				Claim claim = LandBoard.getInstance().getClaim(event.getBlockPlaced().getLocation());

				if (claim != null && team.getClaims().contains(claim)) {
					team.addSpawnersInClaim(1);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.MOB_SPAWNER) {
			Team team = HCF.getInstance().getTeamHandler().getTeam(event.getPlayer());

			if (team != null) {
				Claim claim = LandBoard.getInstance().getClaim(event.getBlock().getLocation());

				if (claim != null && team.getClaims().contains(claim)) {
					team.removeSpawnersInClaim(1);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSpawnerBreakEvent(CrowbarSpawnerBreakEvent event) {
		Team team = HCF.getInstance().getTeamHandler().getTeam(event.getPlayer());

		if (team != null) {
			Claim claim = LandBoard.getInstance().getClaim(event.getBlock().getLocation());

			if (claim != null && team.getClaims().contains(claim)) {
				team.removeSpawnersInClaim(1);
			}
		}
	}

}
