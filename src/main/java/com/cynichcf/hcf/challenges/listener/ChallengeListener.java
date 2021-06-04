package com.cynichcf.hcf.challenges.listener;

import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.object.CBNotification;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.challenges.ChallengeTypes;
import com.cynichcf.hcf.challenges.maps.ChallengeCooldownMap;
import com.cynichcf.hcf.challenges.maps.ChallengeMap;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChallengeListener implements Listener {

    @EventHandler
    public void onMine(final BlockBreakEvent event) {
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            if (challengeTypes.getChallengeObjective() == ChallengeTypes.ChallengeObjective.MINE && event.getBlock().getType() == challengeTypes.getBlockType() && !event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                ChallengeCooldownMap challengeCooldownMap = challengeTypes.getChallengeCooldownMap();
                if (challengeCooldownMap.isOnCooldown(event.getPlayer().getUniqueId())) {
                    return;
                }
                ChallengeMap challengeMap = challengeTypes.getChallengeMap();
                if (challengeMap.getAmount(event.getPlayer().getUniqueId()) == challengeTypes.getChallengeAmount()) {
                    return;
                }
                challengeMap.setAmount(event.getPlayer().getUniqueId(), challengeMap.getAmount(event.getPlayer().getUniqueId()) + 1);
                if (challengeMap.getAmount(event.getPlayer().getUniqueId()) == challengeTypes.getChallengeAmount()) {
                    if (challengeTypes.getChallengeTier() == 1) {
                        challengeMap.setAmount(event.getPlayer().getUniqueId(), 0);
                    }
                    if (challengeTypes.getChallengeTier() == 2) {
                        challengeMap.setAmount(event.getPlayer().getUniqueId(), 0);
                        challengeCooldownMap.setCooldown(event.getPlayer().getUniqueId(), TimeUnit.HOURS.toMillis(12L));
                    }
                    if (challengeTypes.getChallengeTier() == 3) {
                        challengeMap.setAmount(event.getPlayer().getUniqueId(), 0);
                        challengeCooldownMap.setCooldown(event.getPlayer().getUniqueId(), TimeUnit.HOURS.toMillis(24L));
                    }
                    event.getPlayer().sendMessage(CC.translate("&eYou have successfully completed the " + challengeTypes.getChallengeTitle() + " &echallenge."));
                    event.getPlayer().sendMessage(CC.translate("&eYou have earned &f" + challengeTypes.getChallengeCharmReward() + "&e Charm(s)."));

                    HCF.getInstance().getCreditsMap().setCredits(event.getPlayer().getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(event.getPlayer().getUniqueId()) + challengeTypes.getChallengeCharmReward());
                }
            }
        }
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            if (challengeTypes.getChallengeObjective() == ChallengeTypes.ChallengeObjective.KILL) {
                Player player = (Player) event.getDamager();
                if (event.getEntityType() == challengeTypes.getEntityType()) {
                    LivingEntity entity = (LivingEntity) event.getEntity();
                    if (event.getFinalDamage() >= entity.getHealth()) {
                        ChallengeCooldownMap challengeCooldownMap = challengeTypes.getChallengeCooldownMap();
                        if (challengeCooldownMap.isOnCooldown(player.getUniqueId())) return;
                        ChallengeMap challengeMap = challengeTypes.getChallengeMap();
                        challengeMap.setAmount(player.getUniqueId(), (challengeMap.getAmount(player.getUniqueId()) + 1));

                        if (challengeMap.getAmount(player.getUniqueId()) == challengeTypes.getChallengeAmount()) {

                            if (challengeTypes.getChallengeTier() == 1) {
                                challengeMap.setAmount(player.getUniqueId(), 0);
                            }

                            if (challengeTypes.getChallengeTier() == 2) {
                                challengeMap.setAmount(player.getUniqueId(), 0);
                                challengeCooldownMap.setCooldown(player.getUniqueId(), TimeUnit.HOURS.toMillis(12));
                            }

                            if (challengeTypes.getChallengeTier() == 3) {
                                challengeMap.setAmount(player.getUniqueId(), 0);
                                challengeCooldownMap.setCooldown(player.getUniqueId(), TimeUnit.HOURS.toMillis(24));
                            }

                            player.sendMessage(CC.translate("&eYou have successfully completed the " + challengeTypes.getChallengeTitle() + " &echallenge."));
                            player.sendMessage(CC.translate("&eYou have earned &f" + challengeTypes.getChallengeCharmReward() + "&e Charm(s)."));

                            HCF.getInstance().getCreditsMap().setCredits(player.getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(player.getUniqueId()) + challengeTypes.getChallengeCharmReward());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            if (challengeTypes.getChallengeObjective() == ChallengeTypes.ChallengeObjective.DEATH) {
                ChallengeCooldownMap challengeCooldownMap = challengeTypes.getChallengeCooldownMap();
                if (challengeCooldownMap.isOnCooldown(event.getEntity().getUniqueId())) return;
                ChallengeMap challengeMap = challengeTypes.getChallengeMap();
                challengeMap.setAmount(event.getEntity().getUniqueId(), (challengeMap.getAmount(event.getEntity().getUniqueId()) + 1));
                if (challengeMap.getAmount(event.getEntity().getUniqueId()) == challengeTypes.getChallengeAmount()) {

                    if (challengeTypes.getChallengeTier() == 1) {
                        challengeMap.setAmount(event.getEntity().getUniqueId(), 0);
                    }

                    if (challengeTypes.getChallengeTier() == 2) {
                        challengeMap.setAmount(event.getEntity().getUniqueId(), 0);
                        challengeCooldownMap.setCooldown(event.getEntity().getUniqueId(), TimeUnit.HOURS.toMillis(12));
                    }

                    if (challengeTypes.getChallengeTier() == 3) {
                        challengeMap.setAmount(event.getEntity().getUniqueId(), 0);
                        challengeCooldownMap.setCooldown(event.getEntity().getUniqueId(), TimeUnit.HOURS.toMillis(24));
                    }

                    event.getEntity().sendMessage(CC.translate("&eYou have successfully completed the " + challengeTypes.getChallengeTitle() + " &echallenge."));
                    event.getEntity().sendMessage(CC.translate("&eYou have earned &f" + challengeTypes.getChallengeCharmReward() + "&e Charm(s)."));
                    sendCompletionNoti(event.getEntity(), challengeTypes);

                    HCF.getInstance().getCreditsMap().setCredits(event.getEntity().getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(event.getEntity().getUniqueId()) + challengeTypes.getChallengeCharmReward());
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            if (challengeTypes.getChallengeObjective() == ChallengeTypes.ChallengeObjective.PLACE) {
                if (event.getBlockPlaced().getType() == challengeTypes.getBlockType()) {
                    ChallengeCooldownMap challengeCooldownMap = challengeTypes.getChallengeCooldownMap();
                    if (challengeCooldownMap.isOnCooldown(event.getPlayer().getUniqueId())) return;
                    if (this.isBlockChallengeUsed(event.getBlock())) return;
                    event.getBlock().setMetadata("CHALLENGE", new FixedMetadataValue(HCF.getInstance(), true));
                    ChallengeMap challengeMap = challengeTypes.getChallengeMap();
                    challengeMap.setAmount(event.getPlayer().getUniqueId(), (challengeMap.getAmount(event.getPlayer().getUniqueId()) + 1));
                    if (challengeMap.getAmount(event.getPlayer().getUniqueId()) == challengeTypes.getChallengeAmount()) {

                        if (challengeTypes.getChallengeTier() == 1) {
                            challengeMap.setAmount(event.getPlayer().getUniqueId(), 0);
                        }

                        if (challengeTypes.getChallengeTier() == 2) {
                            challengeMap.setAmount(event.getPlayer().getUniqueId(), 0);
                            challengeCooldownMap.setCooldown(event.getPlayer().getUniqueId(), TimeUnit.HOURS.toMillis(12));
                        }

                        if (challengeTypes.getChallengeTier() == 3) {
                            challengeMap.setAmount(event.getPlayer().getUniqueId(), 0);
                            challengeCooldownMap.setCooldown(event.getPlayer().getUniqueId(), TimeUnit.HOURS.toMillis(24));
                        }

                        event.getPlayer().sendMessage(CC.translate("&eYou have successfully completed the " + challengeTypes.getChallengeTitle() + " &echallenge."));
                        event.getPlayer().sendMessage(CC.translate("&eYou have earned &f" + challengeTypes.getChallengeCharmReward() + "&e Charm(s)."));
                        sendCompletionNoti(event.getPlayer(), challengeTypes);

                        HCF.getInstance().getCreditsMap().setCredits(event.getPlayer().getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(event.getPlayer().getUniqueId()) + challengeTypes.getChallengeCharmReward());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            if (challengeTypes.getChallengeObjective() == ChallengeTypes.ChallengeObjective.CRAFT) {
                if (event.getRecipe().getResult().getType() == challengeTypes.getBlockType()) {
                    ChallengeCooldownMap challengeCooldownMap = challengeTypes.getChallengeCooldownMap();
                    if (challengeCooldownMap.isOnCooldown(event.getWhoClicked().getUniqueId())) return;
                    ChallengeMap challengeMap = challengeTypes.getChallengeMap();
                    challengeMap.setAmount(event.getWhoClicked().getUniqueId(), (challengeMap.getAmount(event.getWhoClicked().getUniqueId()) + 1));
                    if (challengeMap.getAmount(event.getWhoClicked().getUniqueId()) == challengeTypes.getChallengeAmount()) {

                        if (challengeTypes.getChallengeTier() == 1) {
                            challengeMap.setAmount(event.getWhoClicked().getUniqueId(), 0);
                        }

                        if (challengeTypes.getChallengeTier() == 2) {
                            challengeMap.setAmount(event.getWhoClicked().getUniqueId(), 0);
                            challengeCooldownMap.setCooldown(event.getWhoClicked().getUniqueId(), TimeUnit.HOURS.toMillis(12));
                        }

                        if (challengeTypes.getChallengeTier() == 3) {
                            challengeMap.setAmount(event.getWhoClicked().getUniqueId(), 0);
                            challengeCooldownMap.setCooldown(event.getWhoClicked().getUniqueId(), TimeUnit.HOURS.toMillis(24));
                        }

                        ((Player) event.getWhoClicked()).sendMessage(CC.translate("&eYou have successfully completed the " + challengeTypes.getChallengeTitle() + " &echallenge."));
                        ((Player) event.getWhoClicked()).sendMessage(CC.translate("&eYou have earned &f" + challengeTypes.getChallengeCharmReward() + "&e Charm(s)."));
                        sendCompletionNoti((Player) event.getWhoClicked(), challengeTypes);

                        HCF.getInstance().getCreditsMap().setCredits(event.getWhoClicked().getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(event.getWhoClicked().getUniqueId()) + challengeTypes.getChallengeCharmReward());

                    }
                }
            }
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            if (challengeTypes.getChallengeObjective() == ChallengeTypes.ChallengeObjective.ENCHANT) {
                ChallengeCooldownMap challengeCooldownMap = challengeTypes.getChallengeCooldownMap();
                if (challengeCooldownMap.isOnCooldown(event.getEnchanter().getUniqueId())) return;
                ChallengeMap challengeMap = challengeTypes.getChallengeMap();
                challengeMap.setAmount(event.getEnchanter().getUniqueId(), (challengeMap.getAmount(event.getEnchanter().getUniqueId()) + 1));
                if (challengeMap.getAmount(event.getEnchanter().getUniqueId()) == challengeTypes.getChallengeAmount()) {

                    if (challengeTypes.getChallengeTier() == 1) {
                        challengeMap.setAmount(event.getEnchanter().getUniqueId(), 0);
                    }

                    if (challengeTypes.getChallengeTier() == 2) {
                        challengeMap.setAmount(event.getEnchanter().getUniqueId(), 0);
                        challengeCooldownMap.setCooldown(event.getEnchanter().getUniqueId(), TimeUnit.HOURS.toMillis(12));
                    }

                    if (challengeTypes.getChallengeTier() == 3) {
                        challengeMap.setAmount(event.getEnchanter().getUniqueId(), 0);
                        challengeCooldownMap.setCooldown(event.getEnchanter().getUniqueId(), TimeUnit.HOURS.toMillis(24));
                    }

                    event.getEnchanter().sendMessage(CC.translate("&eYou have successfully completed the " + challengeTypes.getChallengeTitle() + " &echallenge."));
                    event.getEnchanter().sendMessage(CC.translate("&eYou have earned &f" + challengeTypes.getChallengeCharmReward() + "&e Charm(s)."));
                    sendCompletionNoti(event.getEnchanter(), challengeTypes);

                    HCF.getInstance().getCreditsMap().setCredits(event.getEnchanter().getUniqueId(), HCF.getInstance().getCreditsMap().getCredits(event.getEnchanter().getUniqueId()) + challengeTypes.getChallengeCharmReward());
                }
            }
        }
    }

    private void sendCompletionNoti(Player player, ChallengeTypes type) {
        CheatBreakerAPI.getInstance().sendNotification(player, new CBNotification(CC.translate("&eYou have completed the &6" + type.getChallengeTitle() + "'s &eChallenge"), 8, TimeUnit.SECONDS, CBNotification.Level.INFO));
    }

    public boolean isBlockChallengeUsed(Block b) {
        List<MetadataValue> metaDataValues = b.getMetadata("CHALLENGE");
        Iterator<MetadataValue> iterator = metaDataValues.iterator();
        if (iterator.hasNext()) {
            MetadataValue value = iterator.next();
            return value.asBoolean();
        }
        return false;
    }
}
