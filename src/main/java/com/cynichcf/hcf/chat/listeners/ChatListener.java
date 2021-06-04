package com.cynichcf.hcf.chat.listeners;

import java.util.Map;
import java.util.UUID;

import com.cynichcf.hcf.HCFConstants;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.chat.enums.ChatMode;
import com.cynichcf.hcf.team.commands.team.TeamMuteCommand;
import com.cynichcf.hcf.team.commands.team.TeamShadowMuteCommand;
import com.cynichcf.hcf.team.track.TeamActionTracker;
import com.cynichcf.hcf.team.track.TeamActionType;
import com.cynichcf.hcf.util.CC;
import me.activated.core.plugin.AquaCoreAPI;
import org.bson.types.ObjectId;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;
import com.google.common.collect.ImmutableMap;
import com.cynichcf.hcf.chat.ChatHandler;
import com.cynichcf.hcf.team.Team;
import org.bukkit.potion.PotionType;

public class ChatListener implements Listener {

    private String getCustomPrefix(UUID uuid) {
        Map<Integer, UUID> placesMap = HCF.getInstance().getMapHandler().getStatsHandler() != null ? HCF.getInstance().getMapHandler().getStatsHandler().getTopKills() : null;
        if (placesMap == null) {
            return HCF.getInstance().getChatHandler().getCustomPrefix(uuid);
        }
        Integer place = placesMap.size() == 3 ? placesMap.get(1).equals(uuid) ? 1 : placesMap.get(2).equals(uuid) ? 2 : placesMap.get(3).equals(uuid) ? 3 : 99 : 99;
        if (place == 99) {
            return HCF.getInstance().getChatHandler().getCustomPrefix(uuid);
        }
        return ChatColor.translateAlternateColorCodes('&', place == 1 ? "&8[&6#1&8] " : place == 2 ? "&8[&7#2&8] " : "&8[&f#3&8] ");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChatEarly(AsyncPlayerChatEvent event) {
        ChatMode playerChatMode = HCF.getInstance().getChatModeMap().getChatMode(event.getPlayer().getUniqueId());
        ChatMode forcedChatMode = ChatMode.findFromForcedPrefix(event.getMessage().charAt(0));
        ChatMode finalChatMode;

        if (forcedChatMode != null) {
            finalChatMode = forcedChatMode;
        } else {
            finalChatMode = playerChatMode;
        }

        if (finalChatMode != ChatMode.PUBLIC) {
            event.getPlayer().setMetadata("NoSpamCheck", new FixedMetadataValue(HCF.getInstance(), true));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.getPlayer().removeMetadata("NoSpamCheck", HCF.getInstance());

        Team playerTeam = HCF.getInstance().getTeamHandler().getTeam(event.getPlayer());

        String rankPrefix = AquaCoreAPI.INSTANCE.getPlayerRank(event.getPlayer().getUniqueId()).getPrefix() + event.getPlayer().getName();

        String ranksuffix = AquaCoreAPI.INSTANCE.getPlayerRank(event.getPlayer().getUniqueId()).getSuffix();
        String customPrefix = getCustomPrefix(event.getPlayer().getUniqueId());
        ChatMode playerChatMode = HCF.getInstance().getChatModeMap().getChatMode(event.getPlayer().getUniqueId());
        ChatMode forcedChatMode = ChatMode.findFromForcedPrefix(event.getMessage().charAt(0));
        ChatMode finalChatMode;

        if (forcedChatMode != null) {
            event.setMessage(event.getMessage().substring(1).trim());
        }
        if (forcedChatMode != null) {
            finalChatMode = forcedChatMode;
        } else {
            finalChatMode = playerChatMode;
        }

        if (event.isCancelled() && finalChatMode == ChatMode.PUBLIC) {
            return;
        }

        event.setCancelled(true);

        if (finalChatMode != ChatMode.PUBLIC && playerTeam == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "You can't speak in non-public chat if you're not in a team!");
            return;
        }

        if (finalChatMode != ChatMode.PUBLIC) {
            if (playerTeam == null) {
                event.getPlayer().sendMessage(ChatColor.RED + "You can't speak in non-public chat if you're not in a team!");
                return;
            } else if (finalChatMode == ChatMode.OFFICER && !playerTeam.isCaptain(event.getPlayer().getUniqueId()) && !playerTeam.isCoLeader(event.getPlayer().getUniqueId()) && !playerTeam.isOwner(event.getPlayer().getUniqueId())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You can't speak in officer chat if you're not an officer!");
                return;
            }
        }

        switch (finalChatMode) {
            case PUBLIC:
                if (TeamMuteCommand.getTeamMutes().containsKey(event.getPlayer().getUniqueId())) {
                    event.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Your team is muted!");
                    return;
                }

                String publicChatFormat = HCFConstants.publicChatFormat(playerTeam, rankPrefix, customPrefix);

                if (HCF.getInstance().getConfig().getBoolean("legions")) {
                    publicChatFormat = HCFConstants.publicChatFormatTwoPointOhBaby(playerTeam, rankPrefix, customPrefix);
                }

                String finalMessage = CC.translate(String.format(publicChatFormat, ranksuffix, event.getMessage()));


                for (Player player : event.getRecipients()) {
                    if (playerTeam == null) {
                        if (TeamShadowMuteCommand.getTeamShadowMutes().containsKey(event.getPlayer().getUniqueId())) {
                            continue;
                        }

                        if (event.getPlayer().isOp() || HCF.getInstance().getToggleGlobalChatMap().isGlobalChatToggled(player.getUniqueId())) {
                            player.sendMessage(finalMessage);
                        }
                    } else {
                        if (playerTeam.isMember(player.getUniqueId())) {
                            player.sendMessage(finalMessage.replace(ChatColor.GOLD + "[" + HCF.getInstance().getServerHandler().getDefaultRelationColor(), ChatColor.GOLD + "[" + ChatColor.DARK_GREEN));
                        } else if (playerTeam.isAlly(player.getUniqueId())) {
                            player.sendMessage(finalMessage.replace(ChatColor.GOLD + "[" + HCF.getInstance().getServerHandler().getDefaultRelationColor(), ChatColor.GOLD + "[" + Team.ALLY_COLOR));
                        } else {
                            if (TeamShadowMuteCommand.getTeamShadowMutes().containsKey(event.getPlayer().getUniqueId())) {
                                continue;
                            }

                            if (event.getPlayer().isOp() || HCF.getInstance().getToggleGlobalChatMap().isGlobalChatToggled(player.getUniqueId())) {
                                player.sendMessage(finalMessage);
                            }
                        }
                    }
                }

                ChatHandler.getPublicMessagesSent().incrementAndGet();
                HCF.getInstance().getServer().getConsoleSender().sendMessage(finalMessage);
                break;
            case ALLIANCE:
                String allyChatFormat = HCFConstants.allyChatFormat(event.getPlayer(), event.getMessage());
                String allyChatSpyFormat = HCFConstants.allyChatSpyFormat(playerTeam, event.getPlayer(), event.getMessage());

                for (Player player : HCF.getInstance().getServer().getOnlinePlayers()) {
                    if (playerTeam.isMember(player.getUniqueId()) || playerTeam.isAlly(player.getUniqueId())) {
                        player.sendMessage(allyChatFormat);
                    } else if (HCF.getInstance().getChatSpyMap().getChatSpy(player.getUniqueId()).contains(playerTeam.getUniqueId())) {
                        player.sendMessage(allyChatSpyFormat);
                    }
                }

                for (ObjectId allyId : playerTeam.getAllies()) {
                    Team ally = HCF.getInstance().getTeamHandler().getTeam(allyId);

                    if (ally != null) {
                        TeamActionTracker.logActionAsync(ally, TeamActionType.ALLY_CHAT_MESSAGE, ImmutableMap.<String, Object>builder()
                                .put("allyTeamId", playerTeam.getUniqueId())
                                .put("allyTeamName", playerTeam.getName())
                                .put("playerId", event.getPlayer().getUniqueId())
                                .put("playerName", event.getPlayer().getName())
                                .put("message", event.getMessage())
                                .build()
                        );
                    }
                }

                TeamActionTracker.logActionAsync(playerTeam, TeamActionType.ALLY_CHAT_MESSAGE, ImmutableMap.of(
                        "playerId", event.getPlayer().getUniqueId(),
                        "playerName", event.getPlayer().getName(),
                        "message", event.getMessage()
                ));

                HCF.getInstance().getServer().getLogger().info("[Ally Chat] [" + playerTeam.getName() + "] " + event.getPlayer().getName() + ": " + event.getMessage());
                break;
            case TEAM:
                String teamChatFormat = HCFConstants.teamChatFormat(event.getPlayer(), event.getMessage());
                String teamChatSpyFormat = HCFConstants.teamChatSpyFormat(playerTeam, event.getPlayer(), event.getMessage());

                for (Player player : HCF.getInstance().getServer().getOnlinePlayers()) {
                    if (playerTeam.isMember(player.getUniqueId())) {
                        player.sendMessage(teamChatFormat);
                    } else if (HCF.getInstance().getChatSpyMap().getChatSpy(player.getUniqueId()).contains(playerTeam.getUniqueId())) {
                        player.sendMessage(teamChatSpyFormat);
                    }
                }

                TeamActionTracker.logActionAsync(playerTeam, TeamActionType.TEAM_CHAT_MESSAGE, ImmutableMap.of(
                        "playerId", event.getPlayer().getUniqueId(),
                        "playerName", event.getPlayer().getName(),
                        "message", event.getMessage()
                ));

                HCF.getInstance().getServer().getLogger().info("[Team Chat] [" + playerTeam.getName() + "] " + event.getPlayer().getName() + ": " + event.getMessage());
                break;
            case OFFICER:
                String officerChatFormat = HCFConstants.officerChatFormat(event.getPlayer(), event.getMessage());

                for (Player player : HCF.getInstance().getServer().getOnlinePlayers()) {
                    if (playerTeam.isCaptain(player.getUniqueId()) || playerTeam.isCoLeader(player.getUniqueId()) || playerTeam.isOwner(player.getUniqueId())) {
                        player.sendMessage(officerChatFormat);
                    }
                }

                TeamActionTracker.logActionAsync(playerTeam, TeamActionType.OFFICER_CHAT_MESSAGE, ImmutableMap.of(
                        "playerId", event.getPlayer().getUniqueId(),
                        "playerName", event.getPlayer().getName(),
                        "message", event.getMessage()
                ));

                HCF.getInstance().getServer().getLogger().info("[Officer Chat] [" + playerTeam.getName() + "] " + event.getPlayer().getName() + ": " + event.getMessage());
                break;
        }
    }
}