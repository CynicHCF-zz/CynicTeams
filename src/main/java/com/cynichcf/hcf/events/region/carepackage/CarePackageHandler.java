package com.cynichcf.hcf.events.region.carepackage;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.claims.LandBoard;
import rip.lazze.libraries.Library;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.FrozenCommandHandler;
import net.md_5.bungee.api.ChatColor;
import redis.clients.jedis.Jedis;
import rip.lazze.libraries.framework.redis.RedisCommand;

import static org.bukkit.Material.CHEST;

public class CarePackageHandler implements Listener {

    private Location lastCarePackage;
    private World world;
    private static List<ItemStack> loot;

    public CarePackageHandler() {
        FrozenCommandHandler.registerClass(this.getClass());
        this.world = Bukkit.getWorlds().get(0);
        try {
            loot = Lists.newArrayList(Library.GSON.fromJson(Library.getInstance().runBackboneRedisCommand(new RedisCommand<String>() {

                
                public String execute(Jedis redis) {
                    String lookupString = Bukkit.getServerName() + ":" + "carePackageLoot";
                    Bukkit.getLogger().info("Lookup string: " + lookupString);
                    return redis.get(lookupString);
                }

            }), ItemStack[].class));
        } catch (Exception e) {
            loot = Lists.newArrayList();
            Bukkit.getLogger().info("No care package loot is set up.");
        }
    }

    @Command(names = "savecarepackageloot", permission = "op", async = true)
    public static void save(Player sender) {
        Library.getInstance().runBackboneRedisCommand((redis) -> {
            String lookupString = Bukkit.getServerName() + ":" + "carePackageLoot";
            Bukkit.getLogger().info("Lookup string: " + lookupString);
            redis.set(lookupString, Library.PLAIN_GSON.toJson(Arrays.stream(sender.getInventory().getContents()).filter(item -> item != null && item.getType() != Material.AIR).collect(Collectors.toList())));
            return null;
        });

        loot = Arrays.stream(sender.getInventory().getContents()).filter(item -> item != null && item.getType() != Material.AIR).collect(Collectors.toList());
        sender.sendMessage(ChatColor.GREEN + "Loot updated.");
    }

    private void spawnCarePackage() {
        int x = 0, z = 0;

        while (Math.abs(x) <= 100) x = Library.RANDOM.nextInt(1000) - 500;
        while (Math.abs(z) <= 100) z = Library.RANDOM.nextInt(1000) - 500;

        while (LandBoard.getInstance().getTeam(new Location(world, x, 100, z)) != null) {
            x = 0; z = 0;

            while (Math.abs(x) <= 100) x = Library.RANDOM.nextInt(1000) - 500;
            while (Math.abs(z) <= 100) z = Library.RANDOM.nextInt(1000) - 500;
        }

        int y = world.getHighestBlockYAt(x, z);
        Block block = world.getBlockAt(x, y, z);

        if (block == null) {
            // couldn't find location, lets try again
            spawnCarePackage();
            return;
        }

        Block realBlock = block.getRelative(BlockFace.UP);
        block.setType(CHEST);
        block.setMetadata("CarePackage", new FixedMetadataValue(HCF.getInstance(), new Object()));
        lastCarePackage = block.getLocation();

        Bukkit.getLogger().info("Spawning crate at " + block.getLocation());
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCarePackages&8] &bA &3Crate &bhas spawned at &3" + x + " " + z + "&b."));
        Bukkit.getScheduler().runTaskLaterAsynchronously(HCF.getInstance(), this::removeCarePackage, 10 * 20 * 60);
    }

    private void removeCarePackage() {
        if (lastCarePackage != null && lastCarePackage.getBlock() != null && lastCarePackage.getBlock().getType() == CHEST) {
            lastCarePackage.getBlock().setType(Material.AIR);
            lastCarePackage.getBlock().removeMetadata("CarePackage", HCF.getInstance());
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[CarePackages&8] &bThe &3Crate &bat &3" + lastCarePackage.getBlockX() + " " + lastCarePackage.getBlockZ() + " &bhas despawned."));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getLocation().getBlockY() >= 45) return;
            if (event.getBlock().getType() == CHEST) {
                Chest chest = (Chest) event.getBlock().getState();
                if (chest.hasMetadata("CarePackage")) event.setCancelled(true);
                if (event.getBlock().hasMetadata("CarePackage")) event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || !clickedBlock.hasMetadata("CarePackage")) {
            return;
        }

        Set<ItemStack> toGive = Sets.newHashSet();
        while (toGive.size() < 4) {
            toGive.add(loot.get(Library.RANDOM.nextInt(loot.size())));
        }

        for (ItemStack item : toGive) {
            world.dropItemNaturally(clickedBlock.getLocation(), item);
        }

        clickedBlock.removeMetadata("CarePackage", HCF.getInstance());
        clickedBlock.setType(Material.AIR);
        event.setCancelled(true);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCarePackages&8] &bA &3Crate &bat &3" + clickedBlock.getLocation().getBlockX() + " " + clickedBlock.getLocation().getBlockZ() + " &bhas been opened."));
    }

    @EventHandler
    public void onPluginShutdown(PluginDisableEvent event) {
        if (event.getPlugin() == HCF.getInstance()) {
            removeCarePackage();
        }
    }
}
