package com.cynichcf.hcf.listener;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.object.CBWaypoint;
import com.cheatbreaker.api.object.MinimapStatus;
import com.cheatbreaker.nethandler.obj.ServerRule;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.nametag.FoxtrotNametagProvider;
import com.cynichcf.hcf.team.Team;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rip.lazze.libraries.scoreboard.ScoreFunction;


public class ClientListener implements Listener {

    public ClientListener() {
        Bukkit.getScheduler().runTaskTimer(HCF.getInstance(), () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Bukkit.getOnlinePlayers().forEach(player -> CheatBreakerAPI.getInstance().overrideNametag(onlinePlayer, fetchNametag(onlinePlayer, player), player));
            }
        }, 0, 60);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Team team = HCF.getInstance().getTeamHandler().getTeam(player);
        //CheatBreakerAPI.getInstance().changeServerRule(player, ServerRule.SERVER_HANDLES_WAYPOINTS, true);
        CheatBreakerAPI.getInstance().setMinimapStatus(player, MinimapStatus.FORCED_OFF);
        CheatBreakerAPI.getInstance().sendWaypoint(player, new CBWaypoint("Spawn", player.getWorld().getSpawnLocation(), Color.green.getRGB(), true, true));
    }
    public Comparator<Team> compareFactionPoints = Comparator.comparingLong(Team::getPoints);

    public List<String> fetchNametag(Player target, Player viewer) {
        String nameTag = (AquaCoreAPI.INSTANCE.getPlayerData(target.getUniqueId()).isVanished() ? ChatColor.GRAY + "*" : "") + new FoxtrotNametagProvider().fetchNametag(target, viewer).getPrefix() + target.getName();
            List<String> tag = new ArrayList<>();
            String pvpTimerScore = getPvPTimerScore(target);
            Team team = HCF.getInstance().getTeamHandler().getTeam(target);
            List<Team> Teams = HCF.getInstance().getTeamHandler().getTeams().stream().filter(x -> x instanceof Team).map(x -> (Team) x).filter(x -> x.getPoints() > 0).distinct().sorted(compareFactionPoints).collect(Collectors.toList());
            Collections.reverse(Teams);
            if(team != null) {
                tag.add(ChatColor.GOLD + "[" + HCF.getInstance().getServerHandler().getDefaultRelationColor().toString() + team.getName(viewer) + CC.translate("&7 ") + team.getDTRWithColor() + team.getDTRSuffix() + ChatColor.GOLD + "]");
            }
            if(AquaCoreAPI.INSTANCE.getPlayerData(target.getUniqueId()).isVanished() && team == null) {
                tag.add(CC.translate("&7[Mod Mode]"));
            } else if (AquaCoreAPI.INSTANCE.getPlayerData(target.getUniqueId()).isVanished() && team != null) {
                tag.add(CC.translate("&7[Mod Mode]"));
            }
            if(pvpTimerScore != null) {
                tag.add(CC.translate(CC.translate("&a⚔") + CC.translate("&7 ") + ChatColor.RED + pvpTimerScore + CC.translate("&7 ") + CC.translate("&a⚔")));
            }
            //if(Profile.getProfiles().get(target.getUniqueId()).isFrozen() && team == null) {
              //  tag.add(CC.translate("&4&l[Frozen]"));
           // } else if (Profile.getProfiles().get(target.getUniqueId()).isFrozen() && team != null) {
             //   tag.add(CC.translate("&4&l[Frozen]"));
          //  }
            tag.add(nameTag);
            return tag;
        }
    public String getPvPTimerScore(Player player) {
        if (HCF.getInstance().getPvPTimerMap().hasTimer(player.getUniqueId())) {
            int secondsRemaining = HCF.getInstance().getPvPTimerMap().getSecondsRemaining(player.getUniqueId());

            if (secondsRemaining >= 0) {
                return (ScoreFunction.TIME_SIMPLE.apply((float) secondsRemaining));
            }
        }

        return (null);
    }
    }
