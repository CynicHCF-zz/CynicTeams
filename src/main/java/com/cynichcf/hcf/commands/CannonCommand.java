package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.dtr.DTRBitmask;
import rip.lazze.libraries.Library;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class        CannonCommand {

    public static int SPAWN_CANNON_MAX_DISTANCE = 600;
    public static int SPAWN_CANNON_MIN_DISTANCE = 100;

    @Command(names={ "cannon" }, permission="")
    public static void cannon(Player sender) {
        sender.sendMessage(ChatColor.RED + "/cannon launch - cannons to random coordinates");
        sender.sendMessage(ChatColor.RED + "/cannon aim <x> <z> - cannons to the given coordinate");
    }

    @Command(names={ "cannon launch", "spawncannon launch" }, permission="")
    public static void cannonLaunch(Player sender) {
        if (HCF.getInstance().getServerHandler().isEOTW()) {
            sender.sendMessage(ChatColor.RED + "Spawn cannon disabled: Server is in EOTW mode.");
            return;
        }

        if (!DTRBitmask.SAFE_ZONE.appliesAt(sender.getLocation()) || sender.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.BEACON) {
            sender.sendMessage(ChatColor.RED + "You must be standing on the spawn cannon.");
            return;
        }

        int x = Library.RANDOM.nextInt(SPAWN_CANNON_MAX_DISTANCE - SPAWN_CANNON_MIN_DISTANCE) + SPAWN_CANNON_MIN_DISTANCE;
        int z = Library.RANDOM.nextInt(SPAWN_CANNON_MAX_DISTANCE - SPAWN_CANNON_MIN_DISTANCE) + SPAWN_CANNON_MIN_DISTANCE;

        if (Library.RANDOM.nextBoolean()) {
            x = -x;
        }

        if (Library.RANDOM.nextBoolean()) {
            z = -z;
        }

        spawnCannon(sender, x, z);
    }

    @Command(names={ "cannon aim", "spawncannon aim" }, permission="foxtrot.spawncannon.aim")
    public static void cannonAim(Player sender, @Param(name="x") int x, @Param(name="z") int z) {
        if (HCF.getInstance().getServerHandler().isEOTW()) {
            sender.sendMessage(ChatColor.RED + "Spawn cannon disabled: Server is in EOTW mode.");
            return;
        }

        if (!DTRBitmask.SAFE_ZONE.appliesAt(sender.getLocation()) || sender.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.BEACON) {
            sender.sendMessage(ChatColor.RED + "You must be standing on the spawn cannon.");
            return;
        }

        int maxDistance = getMaxCannonDistance(sender);

        if (Math.abs(x) > maxDistance || Math.abs(z) > maxDistance) {
            sender.sendMessage(ChatColor.RED + "You cannot cannon that far. Your spawn cannon limit is " + maxDistance + ".");
            return;
        }

        if (Math.abs(x) < SPAWN_CANNON_MIN_DISTANCE || Math.abs(z) < SPAWN_CANNON_MIN_DISTANCE) {
            sender.sendMessage(ChatColor.RED + "You cannot cannon to a location that close to spawn! Cannon to a distance at least " + SPAWN_CANNON_MIN_DISTANCE + " blocks from spawn");
        }

        spawnCannon(sender, x, z);
    }

    public static void spawnCannon(Player player, int x, int z) {
        player.sendMessage(ChatColor.YELLOW + "Cannoning to " + ChatColor.GREEN + x + ", " + z + ChatColor.YELLOW + ".");

        new BukkitRunnable() {

            int timer = 0;

            public void run() {
                timer++;

                if (timer < 40) {
                    if (timer % 2 == 0) {
                        player.playSound(player.getLocation(), Sound.EXPLODE, 1F, 1F);
                    }
                } else {
                    Block block = player.getWorld().getBlockAt(x, 200, z);

                    while (block.getType() == Material.AIR && block.getY() > 1) {
                        block = block.getRelative(BlockFace.DOWN);
                    }

                    player.teleport(new Location(player.getWorld(), x, block.getY(), z));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 4, 1));
                    cancel();
                }
            }

        }.runTaskTimerAsynchronously(HCF.getInstance(), 1L, 1L);
    }

    public static int getMaxCannonDistance(Player player) {
        if (player.hasPermission("foxtrot.spawncannon.1250")) {
            return (1250);
        } else if (player.hasPermission("foxtrot.spawncannon.1000")) {
            return (1000);
        } else if (player.hasPermission("foxtrot.spawncannon.750")) {
            return (750);
        } else if (player.hasPermission("foxtrot.spawncannon.500")) {
            return (500);
        }

        // Should never happen
        return (100);
    }

}