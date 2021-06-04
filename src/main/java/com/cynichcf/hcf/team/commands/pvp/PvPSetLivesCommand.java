package com.cynichcf.hcf.team.commands.pvp;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PvPSetLivesCommand {

    @Command(names={ "pvptimer setlives", "pvp setlives", "pvptimer setlives", "pvp setlives" }, permission="foxtrot.setlives")
    public static void pvpSetLives(Player sender, @Param(name="player") UUID player, @Param(name="life type") String lifeType, @Param(name="amount") int amount) {
        if (lifeType.equalsIgnoreCase("soulbound")) {
            HCF.getInstance().getSoulboundLivesMap().setLives(player, amount);
            sender.sendMessage(ChatColor.YELLOW + "Set " + ChatColor.GREEN + UUIDUtils.name(player) + ChatColor.YELLOW + "'s soulbound life count to " + amount + ".");
        } else if (lifeType.equalsIgnoreCase("friend")) {
            HCF.getInstance().getFriendLivesMap().setLives(player, amount);
            sender.sendMessage(ChatColor.YELLOW + "Set " + ChatColor.GREEN + UUIDUtils.name(player) + ChatColor.YELLOW + "'s friend life count to " + amount + ".");
        } else {
            sender.sendMessage(ChatColor.RED + "Not a valid life type: Options are soulbound or friend.");
        }
    }

}