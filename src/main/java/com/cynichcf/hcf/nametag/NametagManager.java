package com.cynichcf.hcf.nametag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cynichcf.hcf.HCF;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.minecraft.util.com.google.common.base.Preconditions;
import net.minecraft.util.com.google.common.primitives.Ints;
import rip.lazze.libraries.packet.ScoreboardTeamPacketMod;

public class NametagManager
{
    private static Map<String, Map<String, NametagInfo>> teamMap;
    private static List<NametagInfo> registeredTeams;
    private static int teamCreateIndex;
    private static List<NametagProvider> providers;
    private static boolean nametagRestrictionEnabled;
    private static String nametagRestrictBypass;
    private static boolean initiated;
    private static boolean async;
    private static int updateInterval;
    
    public static void init() {
        Preconditions.checkState(!NametagManager.initiated);
        NametagManager.initiated = true;
        NametagManager.nametagRestrictionEnabled = false;
        NametagManager.nametagRestrictBypass = "&a".replace("&", "§");
        new NametagThread().start();
        Bukkit.getPluginManager().registerEvents(new NametagListener(), (Plugin) HCF.getInstance());
        registerProvider(new NametagProvider.DefaultNametagProvider());
    }
    
    public static void registerProvider(NametagProvider newProvider) {
        NametagManager.providers.add(newProvider);
        Collections.sort(NametagManager.providers, new Comparator<NametagProvider>() {
            @Override
            public int compare(NametagProvider a, NametagProvider b) {
                return Ints.compare(b.getWeight(), a.getWeight());
            }
        });
    }
    
    public static void reloadPlayer(Player toRefresh) {
        NametagUpdate update = new NametagUpdate(toRefresh);
        if (NametagManager.async) {
            NametagThread.getPendingUpdates().put(update, true);
        }
        else {
            applyUpdate(update);
        }
    }
    
    public static void reloadOthersFor(Player refreshFor) {
        for (Player toRefresh : Bukkit.getOnlinePlayers()) {
            if (refreshFor == toRefresh) {
                continue;
            }
            reloadPlayer(toRefresh, refreshFor);
        }
    }
    
    public static void reloadPlayer(Player toRefresh, Player refreshFor) {
        NametagUpdate update = new NametagUpdate(toRefresh, refreshFor);
        if (NametagManager.async) {
            NametagThread.getPendingUpdates().put(update, true);
        }
        else {
            applyUpdate(update);
        }
    }
    
    protected static void applyUpdate(NametagUpdate nametagUpdate) {
        Player toRefreshPlayer = Bukkit.getPlayerExact(nametagUpdate.getToRefresh());
        if (toRefreshPlayer == null) {
            return;
        }
        if (nametagUpdate.getRefreshFor() == null) {
            for (Player refreshFor : Bukkit.getOnlinePlayers()) {
                reloadPlayerInternal(toRefreshPlayer, refreshFor);
            }
        }
        else {
            Player refreshForPlayer = Bukkit.getPlayerExact(nametagUpdate.getRefreshFor());
            if (refreshForPlayer != null) {
                reloadPlayerInternal(toRefreshPlayer, refreshForPlayer);
            }
        }
    }
    
    protected static void reloadPlayerInternal(Player toRefresh, Player refreshFor) {
        if (!refreshFor.hasMetadata("Nametag-LoggedIn")) {
            return;
        }
        NametagInfo provided = null;
        for (int providerIndex = 0; provided == null; provided = NametagManager.providers.get(providerIndex++).fetchNametag(toRefresh, refreshFor)) {}
        Map<String, NametagInfo> teamInfoMap = new HashMap<String, NametagInfo>();
        if (NametagManager.teamMap.containsKey(refreshFor.getName())) {
            teamInfoMap = NametagManager.teamMap.get(refreshFor.getName());
        }
        new ScoreboardTeamPacketMod(provided.getName(), Arrays.asList(toRefresh.getName()), 3).sendToPlayer(refreshFor);
        teamInfoMap.put(toRefresh.getName(), provided);
        NametagManager.teamMap.put(refreshFor.getName(), teamInfoMap);
    }
    
    protected static void initiatePlayer(Player player) {
        for (NametagInfo teamInfo : NametagManager.registeredTeams) {
            teamInfo.getTeamAddPacket().sendToPlayer(player);
        }
    }
    
    protected static NametagInfo getOrCreate(String prefix, String suffix) {
        for (NametagInfo teamInfo : NametagManager.registeredTeams) {
            if (teamInfo.getPrefix().equals(prefix) && teamInfo.getSuffix().equals(suffix)) {
                return teamInfo;
            }
        }
        NametagInfo newTeam = new NametagInfo(String.valueOf(NametagManager.teamCreateIndex++), prefix, suffix);
        NametagManager.registeredTeams.add(newTeam);
        ScoreboardTeamPacketMod addPacket = newTeam.getTeamAddPacket();
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPacket.sendToPlayer(player);
        }
        return newTeam;
    }
    
    protected static Map<String, Map<String, NametagInfo>> getTeamMap() {
        return NametagManager.teamMap;
    }
    
    public static boolean isNametagRestrictionEnabled() {
        return NametagManager.nametagRestrictionEnabled;
    }
    
    public static void setNametagRestrictionEnabled(boolean nametagRestrictionEnabled) {
        NametagManager.nametagRestrictionEnabled = nametagRestrictionEnabled;
    }
    
    public static String getNametagRestrictBypass() {
        return NametagManager.nametagRestrictBypass;
    }
    
    public static void setNametagRestrictBypass(String nametagRestrictBypass) {
        NametagManager.nametagRestrictBypass = nametagRestrictBypass;
    }
    
    public static boolean isInitiated() {
        return NametagManager.initiated;
    }
    
    public static boolean isAsync() {
        return NametagManager.async;
    }
    
    public static void setAsync(boolean async) {
        NametagManager.async = async;
    }
    
    public static int getUpdateInterval() {
        return NametagManager.updateInterval;
    }
    
    public static void setUpdateInterval(int updateInterval) {
        NametagManager.updateInterval = updateInterval;
    }
    
    static {
        NametagManager.teamMap = new ConcurrentHashMap<String, Map<String, NametagInfo>>();
        NametagManager.registeredTeams = Collections.synchronizedList(new ArrayList<NametagInfo>());
        NametagManager.teamCreateIndex = 1;
        NametagManager.providers = new ArrayList<NametagProvider>();
        NametagManager.nametagRestrictionEnabled = false;
        NametagManager.nametagRestrictBypass = "";
        NametagManager.initiated = false;
        NametagManager.async = true;
        NametagManager.updateInterval = 2;
    }
}
