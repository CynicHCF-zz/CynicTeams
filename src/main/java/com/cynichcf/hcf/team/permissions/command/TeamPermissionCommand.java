package com.cynichcf.hcf.team.permissions.command;

import com.cynichcf.hcf.team.permissions.menu.PermissionsMenu;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamPermissionCommand {

    @Command(names={ "team permissions", "t permissions", "f permissions", "faction permissions", "fac permissions", "team perms", "t perms", "f perms", "faction perms", "fac perms" }, permission="")
    public static void perms(Player sender) {
        Team senderTeam = HCF.getInstance().getTeamHandler().getTeam(sender);

        if (senderTeam == null) {
            sender.sendMessage(ChatColor.GRAY + "Sorry, but it seems that you're not on a team, to join one, use /t join!");
            return;
        }

        if (!(senderTeam.isOwner(sender.getUniqueId()) || senderTeam.isCaptain(sender.getUniqueId()) || senderTeam.isCoLeader(sender.getUniqueId()))) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Only team captains can do this.");
            return;
        }

        new PermissionsMenu(senderTeam).openMenu(sender);
    }

}
