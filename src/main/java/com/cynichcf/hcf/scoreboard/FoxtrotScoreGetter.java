package com.cynichcf.hcf.scoreboard;

import com.cynichcf.hcf.events.purge.commands.PurgeCommands;
import com.cynichcf.hcf.persist.maps.KillsMap;
import dev.rhco.abilities.cooldown.Cooldowns;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.commands.CustomTimerCreateCommand;
import com.cynichcf.hcf.commands.EOTWCommand;
import com.cynichcf.hcf.events.Event;
import com.cynichcf.hcf.events.EventType;
import com.cynichcf.hcf.events.conquest.game.ConquestGame;
import com.cynichcf.hcf.events.dtc.DTC;
import com.cynichcf.hcf.events.killtheking.KingEvent;
import com.cynichcf.hcf.events.koth.KOTH;
import com.cynichcf.hcf.listener.GoldenAppleListener;
import com.cynichcf.hcf.map.stats.StatsEntry;
import com.cynichcf.hcf.pvpclasses.PvPClass;
import com.cynichcf.hcf.pvpclasses.PvPClassHandler;
import com.cynichcf.hcf.pvpclasses.pvpclasses.ArcherClass;
import com.cynichcf.hcf.server.EnderpearlCooldownHandler;
import com.cynichcf.hcf.server.ServerHandler;
import com.cynichcf.hcf.server.SpawnTagHandler;
import com.cynichcf.hcf.team.Team;
import com.cynichcf.hcf.team.claims.LandBoard;
import com.cynichcf.hcf.team.commands.team.TeamStuckCommand;
import com.cynichcf.hcf.team.dtr.DTRBitmask;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.util.Logout;
import com.cynichcf.hcf.settings.Setting;
import com.cynichcf.hcf.tournaments.Tournament;
import com.cynichcf.hcf.tournaments.TournamentState;
import com.cynichcf.hcf.tournaments.TournamentType;
import com.cynichcf.hcf.tournaments.handler.TournamentHandler;
import me.activated.core.plugin.AquaCoreAPI;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.cynichcf.hcf.pvpclasses.pvpclasses.BardClass;
import rip.lazze.libraries.autoreboot.AutoRebootHandler;
import rip.lazze.libraries.framework.economy.FrozenEconomyHandler;
import rip.lazze.libraries.scoreboard.ScoreFunction;
import rip.lazze.libraries.scoreboard.ScoreGetter;
import rip.lazze.libraries.util.LinkedList;
import rip.lazze.libraries.util.TimeUtils;
import org.bson.types.ObjectId;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FoxtrotScoreGetter implements ScoreGetter {

    private String formatBasicTps(double tps) {
        return "" + Math.min(Math.round(tps * 10.0) / 10.0, 20.0);
    }

    public void getScores(LinkedList<String> scores, Player player) {
        Team team = HCF.getInstance().getTeamHandler().getTeam(player);
        String spawnTagScore = getSpawnTagScore(player);
        String enderpearlScore = getEnderpearlScore(player);
        String pvpTimerScore = getPvPTimerScore(player);
        String archerMarkScore = getArcherMarkScore(player);
        String bardEffectScore = getBardEffectScore(player);
        String bardEnergyScore = getBardEnergyScore(player);
        String fstuckScore = getFStuckScore(player);
        String logoutScore = getLogoutScore(player);
        String homeScore = getHomeScore(player);
        String appleScore = getAppleScore(player);
        TournamentHandler tournamentManager = HCF.getInstance().getTournamentHandler();
        Tournament tournament = HCF.getInstance().getTournamentHandler().getTournament();
        String mainColor = CC.translate(HCF.getInstance().getConfig().getString("scoreboard.mainColor"));
        String otherColor = CC.translate(HCF.getInstance().getConfig().getString("scoreboard.otherColor"));
        String deathbanScore = getDeathbanScore(player);

        if (Setting.SCOREBOARD_STAFF_BOARD.isEnabled(player) && AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isInStaffMode()) {
            double tps = Bukkit.spigot().getTPS()[1];
            ChatColor colour = (tps >= 18.0) ? ChatColor.GREEN : ((tps >= 13.0) ? ChatColor.YELLOW : ChatColor.RED);
            String chat = "&aPublic";
            if (AquaCoreAPI.INSTANCE.isStaffChat(player)) chat = "&bStaff Chat";

            scores.add(mainColor + "&lStaff Mode");
            //scores.add(" " + otherColor + " Vanish&7: " + (AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isVanished() ? "&aEnabled" : "&cDisabled"));
            //scores.add(" " + otherColor + " Chat&7: " + chat);
            scores.add(" " + otherColor + " Online&7: " + Bukkit.getOnlinePlayers().size() + "&7/" + Bukkit.getMaxPlayers());
            scores.add(" " + otherColor + " TPS&7: " + colour + formatBasicTps(tps));
        }
//        if (AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isVanished() && !AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isInStaffMode()) {
//            scores.add(mainColor + "&lVisibility Mode");
//            scores.add(" " + otherColor + " Vanish&7: " + (AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isVanished() ? "&aEnabled" : "&cDisabled"));
//        }
        String sectionColor = HCF.getInstance().getServerHandler().getSbSectionColor();
        String infoColor = HCF.getInstance().getServerHandler().getSbTimeColor();
        if (HCF.getInstance().getMapHandler().isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap()) {
            StatsEntry stats = HCF.getInstance().getMapHandler().getStatsHandler().getStats(player.getUniqueId());
            if (!AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isInStaffMode() && DTRBitmask.SAFE_ZONE.appliesAt(player.getLocation())) {
                scores.add(otherColor + "Kills&7: &f" + stats.getKills());
                scores.add(otherColor + "Deaths&7: &f" + stats.getDeaths());
                if (stats.getKillstreak() > 0) {
                    scores.add(otherColor + "Killstreak&7: &f" + stats.getKillstreak());
                }
                scores.add(otherColor + "Balance&7: &a" + "$" + FrozenEconomyHandler.getBalance(player.getUniqueId()));
//                scores.add(otherColor + "Charms&7: &d" + "✪&f" + HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()));

            }
        }
            if (DTRBitmask.SAFE_ZONE.appliesAt(player.getLocation()) && !HCF.getInstance().getMapHandler().isKitMap() && !AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId()).isInStaffMode()) {
                scores.add(otherColor + "Balance&7: &a" + "$" + FrozenEconomyHandler.getBalance(player.getUniqueId()));
//                scores.add(otherColor + "Charms&7: &d" + "✪&f" + HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()));

            }

        if (spawnTagScore != null) {
            scores.add("&c&lSpawn Tag&7: &c" + spawnTagScore);
        }

        if (homeScore != null) {
            scores.add("&9&lHome§7: &9" + homeScore);
        }

        if (appleScore != null) {
            scores.add(mainColor + "&lApple&7: **&c" + appleScore);
        }
        long cooldown = HCF.getInstance().getOppleMap().getCooldown(player.getUniqueId());
        if (cooldown > System.currentTimeMillis()) {
            long millisLeft = cooldown - System.currentTimeMillis();
            String msg = TimeUtils.formatLongIntoHHMMSS(TimeUnit.MILLISECONDS.toSeconds(millisLeft));
            scores.add("&6&lGopple&7: **&c" + msg);
        }
        if (enderpearlScore != null) {
            scores.add("&e&lEnderpearl&7: &c" + enderpearlScore);
        }




        if (KingEvent.isStarted(false)) {
            Player target = KingEvent.getFocusedPlayer();
            scores.add(mainColor + "&lKill The King");
            scores.add(otherColor + " King: " + target.getName());
            if (target != player) {
                scores.add(otherColor + " Location: &f" + target.getLocation().getBlockX() + ", " + target.getLocation().getBlockX() + ", " + target.getLocation().getBlockZ());
                if (target.getWorld() == player.getWorld()) {
                    int distance = (int) target.getLocation().distance(player.getLocation());
                    if (distance <= 3000) {
                        scores.add(otherColor + " Distance: &f" + distance + 'm');
                    }
                }
            }
            scores.add(otherColor + " Reward: &f" + KingEvent.getReward());
            scores.add(otherColor + " Time Left: &c" + DurationFormatUtils.formatDuration(KingEvent.getTime(), "mm:ss"));
        }

        if (tournamentManager.isInTournament(player) && !player.hasMetadata("frozen")) {
            int announceCountdown = tournament.getDesecrentAnn();
            scores.add(mainColor + "&l" + tournament.getType().getName() + " Event");
            if (tournament.getType() == TournamentType.SUMO) {
                scores.add(otherColor + " Players&7: &f" + tournament.getPlayers().size() + "/" + tournament.getSize());
                if (announceCountdown > 0) {
                    scores.add(otherColor + " Round Begins&7: &f" + announceCountdown + "s");
                }
                if (tournament.getTournamentState() == TournamentState.WAITING) {
                    scores.add(otherColor + " State&7: &fWaiting...");
                } else if (tournament.getTournamentState() == TournamentState.FIGHTING) {
                    scores.add(otherColor + " State&7: &fFighting...");
                } else {
                    scores.add(otherColor + " State&7: &fSelecting...");
                }
            } else if (tournament.getType() == TournamentType.DIAMOND ||
                    tournament.getType() == TournamentType.AXE ||
                    tournament.getType() == TournamentType.ARCHER ||
                    tournament.getType() == TournamentType.ROGUE) {
                scores.add(otherColor + " Players&7: &f" + tournament.getPlayers().size() + "/" + tournament.getSize());
                if (announceCountdown > 0) {
                    scores.add(otherColor + " FFA Begins&7: &f" + announceCountdown + "s");
                }
                if (tournament.getTournamentState() == TournamentState.WAITING) {
                    scores.add(otherColor + " State&7: &fWaiting...");
                } else if (tournament.isActiveProtection()) {
                    scores.add(otherColor + " State&7: &fImmune");
                    scores.add(otherColor + " Immunity&7: &f" + (tournament.getProtection() / 1000) + "s");
                } else {
                    scores.add(otherColor + " State&7: &fFighting...");
                    scores.add(otherColor + " Immunity&7: &fDeactivated");
                }
            }
        }

        if (deathbanScore != null) {
            scores.add("&c&lDeathban&7: &c" + deathbanScore);
        } else if (pvpTimerScore != null) {
            if (HCF.getInstance().getStartingPvPTimerMap().get(player.getUniqueId())) {
                scores.add("&a&lStarting Timer&7: &c" + pvpTimerScore);
            } else {
                scores.add("&a&lPvP Timer&7: &c" + pvpTimerScore);
            }
        }

        Iterator<Map.Entry<String, Long>> iterator = CustomTimerCreateCommand.getCustomTimers().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> timer = iterator.next();
            if (timer.getValue() < System.currentTimeMillis()) {
                iterator.remove();
                continue;
            }

            if (timer.getKey().equals("&a&lSOTW ends in")) {
                if (CustomTimerCreateCommand.hasSOTWEnabled(player.getUniqueId())) {
                    scores.add(ChatColor.translateAlternateColorCodes('&', "&a&l&mSOTW ends in&7: &c&m" + getTimerScore(timer)));
                } else {
                    scores.add(ChatColor.translateAlternateColorCodes('&', "&a&lSOTW ends in&7: &c" + getTimerScore(timer)));
                }
            } else if (timer.getKey().equals("&4&lSale")) {
                scores.add(ChatColor.translateAlternateColorCodes('&', "&4&lSale &4ends in &4&l" + getTimerScore(timer)));
            } else {
                scores.add(ChatColor.translateAlternateColorCodes('&', timer.getKey()) + "&7: &c" + getTimerScore(timer));
            }
        }

        for (Event event : HCF.getInstance().getEventHandler().getEvents()) {
            if (!event.isActive() || event.isHidden()) {
                continue;
            }
            Iterator<Map.Entry<String, Long>> purge = PurgeCommands.getCustomTimers().entrySet().iterator();
            while (purge.hasNext()) {
                Map.Entry<String, Long> timer = purge.next();
                if (timer.getValue() < System.currentTimeMillis()) {
                    purge.remove();
                    continue;
                }

                if (PurgeCommands.isPurgeTimer()) {
                    scores.add(ChatColor.translateAlternateColorCodes('&', "&9&lThe Purge Event&7: &c" + PurgeCommands.getCustomTimers()));
                }
            }
            String displayName;

            switch (event.getName()) {
                case "EOTW":
                    displayName = ChatColor.DARK_RED.toString() + ChatColor.BOLD + "EOTW";
                    break;
                case "Citadel":
                    displayName = ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Citadel";
                    break;
                case "Biohazard":
                    displayName = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Biohazard";
                    break;
                case "Mars":
                    displayName = ChatColor.GOLD.toString() + ChatColor.BOLD + "Mars";
                    break;
                case "End":
                    displayName = ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "End";
                    break;
                case "Nether":
                    displayName = ChatColor.RED.toString() + ChatColor.BOLD + "Nether";
                    break;
                default:
                    displayName = ChatColor.BLUE.toString() + ChatColor.BOLD + event.getName();
                    break;
            }

            if (event.getType() == EventType.DTC) {
                scores.add(displayName + "&7: &c" + ((DTC) event).getCurrentPoints());
            } else {
                scores.add(displayName + "&7: " + (event.getName() == "Mars" ? "&6" : "&c") + ScoreFunction.TIME_SIMPLE.apply((float) ((KOTH) event).getRemainingCapTime()));
            }
        }

        if (EOTWCommand.isFfaEnabled()) {
            long ffaEnabledAt = EOTWCommand.getFfaActiveAt();
            if (System.currentTimeMillis() < ffaEnabledAt) {
                long difference = ffaEnabledAt - System.currentTimeMillis();
                scores.add("&4&lFFA&7: &c" + ScoreFunction.TIME_SIMPLE.apply(difference / 1000F));
            }
        }
        PvPClass classs = PvPClassHandler.getPvPClass(player);
        if (classs != null) {
            scores.add(otherColor + "&lClass&7: &f" + classs.getName());
        }

        if (archerMarkScore != null) {
            scores.add(mainColor + "&lArcher Mark&7: &c" + archerMarkScore);
        }

        if (bardEffectScore != null && classs != null) {
            scores.add("&a&lBard Effect&7: &c" + bardEffectScore);
        }

        if (bardEnergyScore != null && classs != null) {
            scores.add("&b&lBard Energy&7: &c" + bardEnergyScore);
        }
        if (ArcherClass.getLastSpeedUsage().containsKey(player.getName()) && ArcherClass.getLastSpeedUsage().get(player.getName()) > System.currentTimeMillis() && classs != null) {
            long millisLeft = ArcherClass.getLastSpeedUsage().get(player.getName()) - System.currentTimeMillis();
            String msg = TimeUtils.formatIntoMMSS((int)millisLeft / 1000);
            scores.add("&a&lSpeed&7: &C" + msg);
        }
        if (ArcherClass.getLastJumpUsage().containsKey(player.getName()) && ArcherClass.getLastJumpUsage().get(player.getName()) > System.currentTimeMillis() && classs != null) {
            long millisLeft = ArcherClass.getLastJumpUsage().get(player.getName()) - System.currentTimeMillis();
            String msg = TimeUtils.formatIntoMMSS((int)millisLeft / 1000);
            scores.add("&b&lJump&7: &C" + msg);
        }

        if (fstuckScore != null) {
            scores.add("&4&lStuck&7: &c" + fstuckScore);
        }

        if (logoutScore != null) {
            scores.add("&4&lLogout&7: &c" + logoutScore);
        }
        if (Cooldowns.getAbilitycd().onCooldown(player)) {
            scores.add("&d&lPartner Item&7: &c" + Cooldowns.getAbilitycd().getRemaining(player));
        }

        ConquestGame conquest = HCF.getInstance().getConquestHandler().getGame();

        if (conquest != null) {
            if (scores.size() != 0) {
                scores.add("&c&7&m--------------------");
            }

            scores.add(mainColor + "&lConquest:");
            int displayed = 0;

            for (Map.Entry<ObjectId, Integer> entry : conquest.getTeamPoints().entrySet()) {
                Team resolved = HCF.getInstance().getTeamHandler().getTeam(entry.getKey());

                if (resolved != null) {
                    scores.add("  " + resolved.getName(player) + "&7: &f" + entry.getValue());
                    displayed++;
                }

                if (displayed == 3) {
                    break;
                }
            }

            if (displayed == 0) {
                scores.add("  &7No scores yet");
            }
        }

        if (AutoRebootHandler.isRebooting()) {
            scores.add("&c&lRebooting&7: &c" + TimeUtils.formatIntoMMSS(AutoRebootHandler.getRebootSecondsRemaining()));
        }
        if (HCF.getInstance().getConfig().getBoolean("scoreboard.Location")) {
            Location loc = player.getLocation();
            Team ownerTeam = LandBoard.getInstance().getTeam(loc);
            String location;
            if (ownerTeam != null) {
                location = ownerTeam.getName(player.getPlayer());
            } else if (!HCF.getInstance().getServerHandler().isWarzone(loc)) {
                location = ChatColor.GRAY + "Wilderness";
            } else if (LandBoard.getInstance().getTeam(loc) != null && LandBoard.getInstance().getTeam(loc).getName().equalsIgnoreCase("citadel")) {
                location = ChatColor.DARK_PURPLE + "Citadel";
            } else {
                location = ChatColor.RED + "Warzone";
            }
            if (!HCF.getInstance().getServerHandler().isUnclaimed(loc)) {
                scores.add(mainColor + "&lClaim&7: " + location);
            }
        }
        if (team != null){
        if (team.isRallyActive()) {
            Location rallyP = team.getRally().getLocation();
            scores.add(mainColor + "&lFaction Rally");
            scores.add(" &7» &f" + rallyP.getBlockX() + ", " + rallyP.getBlockY() + ", " + rallyP.getBlockZ());
        }
        String dtrColored;
        double dtr;
        if (team.getFactionFocused() != null) {
            scores.add(" ");
            Team focusedFaction = team.getFactionFocused();
            scores.add(mainColor + "&lTeam&7: &e" + focusedFaction.getName());

            dtr = Double.parseDouble((new DecimalFormat("#.##")).format(focusedFaction.getDTR()));
            if (dtr >= 1.01D) {
                dtrColored = ChatColor.GREEN + String.valueOf(dtr);
            } else if (dtr <= 0.0D) {
                dtrColored = ChatColor.RED + String.valueOf(dtr);
            } else {
                dtrColored = ChatColor.YELLOW + String.valueOf(dtr);
            }

            if (focusedFaction.getHQ() != null) {
                scores.add(mainColor + "&lHQ&7: &e" + focusedFaction.getHQ().getBlockX() + ", " + focusedFaction.getHQ().getBlockZ());
            } else {
                scores.add(mainColor + "&lHQ&7: &cNot set.");
            }
            scores.add(mainColor + "&lDTR&7: &e" + dtrColored + focusedFaction.getDTRSuffix());
            scores.add(mainColor + "&lOnline&7: &e" + focusedFaction.getOnlineMemberAmount());
        }
        }
        if (!scores.isEmpty()) {
            scores.addFirst("&a&7&m--------------------");
            if (HCF.getInstance().getConfig().getBoolean("scoreboard.footerboolean")) {    scores.add("&c&l");
                scores.add(CC.translate(HCF.getInstance().getConfig().getString("scoreboard.footer"))); }
            scores.add("&b&7&m--------------------");
        }
    }

    public String getDeathbanScore(Player player) {
        if (HCF.getInstance().getDeathbanMap().isDeathbanned(player.getUniqueId())) {
            float diff = HCF.getInstance().getDeathbanMap().getDeathban(player.getUniqueId()) - System.currentTimeMillis();

            if (diff >= 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return null;
    }

    public String getAppleScore(Player player) {
        if (GoldenAppleListener.getCrappleCooldown().containsKey(player.getUniqueId()) && GoldenAppleListener.getCrappleCooldown().get(player.getUniqueId()) >= System.currentTimeMillis()) {
            float diff = GoldenAppleListener.getCrappleCooldown().get(player.getUniqueId()) - System.currentTimeMillis();

            if (diff >= 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return (null);
    }

    public String getHomeScore(Player player) {
        if (ServerHandler.getHomeTimer().containsKey(player.getName()) && ServerHandler.getHomeTimer().get(player.getName()) >= System.currentTimeMillis()) {
            float diff = ServerHandler.getHomeTimer().get(player.getName()) - System.currentTimeMillis();

            if (diff >= 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return (null);
    }

    public String getFStuckScore(Player player) {
        if (TeamStuckCommand.getWarping().containsKey(player.getName())) {
            float diff = TeamStuckCommand.getWarping().get(player.getName()) - System.currentTimeMillis();

            if (diff >= 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return null;
    }

    public String getLogoutScore(Player player) {
        Logout logout = ServerHandler.getTasks().get(player.getName());

        if (logout != null) {
            float diff = logout.getLogoutTime() - System.currentTimeMillis();

            if (diff >= 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return null;
    }

    public String getSpawnTagScore(Player player) {
        if (SpawnTagHandler.isTagged(player)) {
            float diff = SpawnTagHandler.getTag(player);

            if (diff >= 0) {
                return (ScoreFunction.TIME_SIMPLE.apply(diff / 1000F));
            }
        }

        return (null);
    }

    public String getEnderpearlScore(Player player) {
        if (EnderpearlCooldownHandler.getEnderpearlCooldown().containsKey(player.getName()) && EnderpearlCooldownHandler.getEnderpearlCooldown().get(player.getName()) >= System.currentTimeMillis()) {
            float diff = EnderpearlCooldownHandler.getEnderpearlCooldown().get(player.getName()) - System.currentTimeMillis();

            if (diff >= 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return (null);
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

    public String getTimerScore(Map.Entry<String, Long> timer) {
        long diff = timer.getValue() - System.currentTimeMillis();

        if (diff > 0) {
            return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
        } else {
            return (null);
        }
    }

    public String getArcherMarkScore(Player player) {
        if (ArcherClass.isMarked(player)) {
            long diff = ArcherClass.getMarkedPlayers().get(player.getName()) - System.currentTimeMillis();

            if (diff > 0) {
                return (ScoreFunction.TIME_FANCY.apply(diff / 1000F));
            }
        }

        return (null);
    }

    public String getBardEffectScore(Player player) {
        if (BardClass.getLastEffectUsage().containsKey(player.getName()) && BardClass.getLastEffectUsage().get(player.getName()) >= System.currentTimeMillis()) {
            float diff = BardClass.getLastEffectUsage().get(player.getName()) - System.currentTimeMillis();

            if (diff > 0) {
                return (ScoreFunction.TIME_SIMPLE.apply(diff / 1000F));
            }
        }

        return (null);
    }

    public String getBardEnergyScore(Player player) {
        if (BardClass.getEnergy().containsKey(player.getName())) {
            float energy = BardClass.getEnergy().get(player.getName());

            if (energy > 0) {
                return (String.valueOf(BardClass.getEnergy().get(player.getName())));
            }
        }

        return (null);
    }

}