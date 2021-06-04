package com.cynichcf.hcf.challenges.commands;


import com.cynichcf.hcf.challenges.ChallengeTypes;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.challenges.maps.ChallengeCooldownMap;
import com.cynichcf.hcf.challenges.maps.ChallengeMap;
import com.cynichcf.hcf.challenges.menu.ChallengesMenu;
import org.bukkit.entity.Player;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

import java.util.UUID;

public class ChallengeCommand {

    @Command(names = {"challenge", "challenges", "ch"}, permission = "")
    public static void progress(Player sender) {
        if (sender != null) {
            new ChallengesMenu().openMenu(sender);
        }
    }

    @Command(names = {"challenge reset", "challenges reset", "ch reset"}, permission = "foxtrot.reset")
    public static void reset(Player sender, @Param(name = "target") UUID player, @Param(name = "progress|cooldowns") String type) {
        for (ChallengeTypes challengeTypes : ChallengeTypes.values()) {
            ChallengeCooldownMap cooldownMap = challengeTypes.getChallengeCooldownMap();
            ChallengeMap challengeMap = challengeTypes.getChallengeMap();

            if (type.equalsIgnoreCase("progress")) {
                challengeMap.setAmount(player, 0);
            } else if (type.equalsIgnoreCase("cooldowns")) {
                cooldownMap.setCooldown(player, 0);
            } else {
                sender.sendMessage(CC.RED + "Please put a valid reset option. You can only reset a players progress and cooldowns.");
                return;
            }
        }
        sender.sendMessage(CC.GREEN + "You have successfully reset " + sender.getName() + "'s challenge " + type.toLowerCase() + ".");
    }
}
