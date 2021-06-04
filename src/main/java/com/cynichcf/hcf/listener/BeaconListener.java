package com.cynichcf.hcf.listener;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.dtr.DTRBitmask;
import com.cynichcf.hcf.util.CC;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;
import java.util.*;

public class BeaconListener implements Listener
{
    private HCF instance;
    private Map<String, Long> cooldown;
    
    public BeaconListener(HCF plugin) {
        this.cooldown = new HashMap<String, Long>();
        this.instance = plugin;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @SuppressWarnings("incomplete-switch")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
         Player player = event.getPlayer();
         Location loc = player.getLocation();
        if (DTRBitmask.SAFE_ZONE.appliesAt(event.getPlayer().getLocation()) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.hasBlock()) {
            switch (event.getClickedBlock().getType()) {
                case BEACON: {
                    event.setCancelled(true);
                    int time = this.getRenameCooldown(player);
                    if (time != 0) {
                        player.sendMessage(CC.translate(HCF.getInstance().getConfig().getString("BEACON-RENAME.COOLDOWN").replace("%time%", String.valueOf(time))));
                        break;
                    }
                    if (player.getItemInHand().getType().equals(Material.DIAMOND_SWORD)) {
                        this.cooldown.put(player.getName(), System.currentTimeMillis() / 90L + 900L);
                        ItemStack item = player.getItemInHand();
                        for (int i = 0; i < 4; ++i) {
                            int b = i;
                            new BukkitRunnable() {
                                public void run() {
                                    if (b == 3) {
                                        String word = BeaconListener.this.getRandomWord();
                                        player.sendMessage(CC.translate(HCF.getInstance().getConfig().getString("BEACON-RENAME.MIXED")).replace("%name%", word));
                                        ItemMeta itemMeta = item.getItemMeta();
                                        itemMeta.setDisplayName(word);
                                        item.setItemMeta(itemMeta);
                                        player.updateInventory();
                                    }
                                    else {
                                        player.sendMessage(CC.translate(HCF.getInstance().getConfig().getString("BEACON-RENAME.MIXING").replace("%random%", BeaconListener.this.getRandomWord())));
                                    }
                                }
                            }.runTaskLaterAsynchronously((Plugin)this.instance, (long)(i * 10));
                        }
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public String getRandomWord() {
        Random random = new Random();
        String word = CC.translate(HCF.getInstance().getConfig().getStringList("BEACON-RENAME.RENAMES").get(random.nextInt(HCF.getInstance().getConfig().getStringList("BEACON-RENAME.RENAMES").size())));
        String capsWord = String.valueOf(word.substring(0, 1).toUpperCase()) + word.substring(1);
        return capsWord;
    }
    
    public int getRenameCooldown(Player p) {
        if (!this.cooldown.containsKey(p.getName())) {
            return 0;
        }
        if (this.cooldown.get(p.getName()) > System.currentTimeMillis() / 90L) {
            return (int)(this.cooldown.get(p.getName()) - System.currentTimeMillis() / 90L);
        }
        return 0;
    }
    
    public Map<String, Long> getCooldown() {
        return this.cooldown;
    }
}
