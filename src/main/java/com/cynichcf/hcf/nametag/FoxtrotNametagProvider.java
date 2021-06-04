package com.cynichcf.hcf.nametag;


import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.pvpclasses.pvpclasses.ArcherClass;
import com.cynichcf.hcf.team.Team;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class FoxtrotNametagProvider extends NametagProvider {

    public FoxtrotNametagProvider() {
        super("Foxtrot Provider", 5);
    }


    public NametagInfo fetchNametag(Player toRefresh, Player refreshFor) {
        Team viewerTeam =  HCF.getInstance().getTeamHandler().getTeam(refreshFor);
        NametagInfo nametagInfo = null;

        if (viewerTeam != null) {
            if (viewerTeam.isMember(toRefresh.getUniqueId())) {
                nametagInfo = FoxtrotNametagProvider.createNametag(ChatColor.GREEN.toString(), "");
            }
            else if (viewerTeam.isAlly(toRefresh.getUniqueId())) {
                nametagInfo = FoxtrotNametagProvider.createNametag(ChatColor.AQUA.toString(), "");
            }
            else if (viewerTeam != null && viewerTeam.getFocused() != null && viewerTeam.getFocused().equals(toRefresh.getUniqueId())) {
                nametagInfo = FoxtrotNametagProvider.createNametag(ChatColor.LIGHT_PURPLE.toString(), "");
            }
        }
        if (nametagInfo == null && AquaCoreAPI.INSTANCE.getPlayerData(toRefresh.getUniqueId()).isInStaffMode()) {
            nametagInfo = FoxtrotNametagProvider.createNametag(ChatColor.GRAY.toString(), "");
        }
        if (nametagInfo == null && ArcherClass.isMarked(toRefresh)) {
            nametagInfo = FoxtrotNametagProvider.createNametag(ChatColor.YELLOW.toString(), "");
        }
        if (refreshFor == toRefresh) {
            nametagInfo = FoxtrotNametagProvider.createNametag(ChatColor.GREEN.toString(), "");
        }
        return (nametagInfo == null) ? FoxtrotNametagProvider.createNametag(ChatColor.RED.toString(), "") : nametagInfo;
    }
}