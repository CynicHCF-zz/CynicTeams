package com.cynichcf.hcf.team.commands.team.subclaim;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.claims.VisualClaim;
import com.cynichcf.hcf.team.claims.VisualClaimType;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamSubclaimMapCommand {

    @Command(names={ "team subclaim map", "t subclaim map", "f subclaim map", "faction subclaim map", "fac subclaim map", "team sub map", "t sub map", "f sub map", "faction sub map", "fac sub map" }, permission="")
    public static void teamSubclaimMap(Player sender) {
        if (HCF.getInstance().getDeathbanMap().isDeathbanned(sender.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "You can't do this while you are deathbanned.");
            return;
        }

        (new VisualClaim(sender, VisualClaimType.SUBCLAIM_MAP, false)).draw(false);
    }

}