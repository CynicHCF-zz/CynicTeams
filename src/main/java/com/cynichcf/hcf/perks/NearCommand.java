package com.cynichcf.hcf.perks;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import net.minecraft.util.com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NearCommand {

    @Command(names = {"near", "nearme"}, permission = "foxtrot.near")
    public static void near(CommandSender sender) {
        int RADIUS = 30;
        Player player = (Player) sender;
        List<Player> playerList = getNearbyEnemies(player);
        if (playerList.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No visible enemies in a " + RADIUS + " block radius");
        } else {
            sender.sendMessage(ChatColor.GOLD + "Nearby visible enemies within " + RADIUS + " block radius");
            sender.sendMessage(ChatColor.GRAY + Joiner.on(", ").join(playerList.stream().map(Player::getName).collect(Collectors.toList())));
        }
    }

    public static List<Player> getNearbyEnemies(Player player) {
        int RADIUS = 30;
        List<Player> players = new ArrayList<>();
        Team playerFaction = HCF.getInstance().getTeamHandler().getTeam(player.getUniqueId());
        Collection<Entity> nearby = player.getNearbyEntities((double) RADIUS, (double) RADIUS, (double) RADIUS);
        for (Entity entity : nearby) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (!target.canSee(player)) {
                    continue;
                }
                if (!player.canSee(target)) {
                    continue;
                }
                if(target.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                    continue;
                }
                Team targetFaction;
                if (playerFaction != null && (targetFaction = HCF.getInstance().getTeamHandler().getTeam(target.getUniqueId())) != null && targetFaction.equals(playerFaction)) {
                    continue;
                }
                players.add(target);
            }
        }
        return players;
    }
}
