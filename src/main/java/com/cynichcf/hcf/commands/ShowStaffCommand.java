package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.util.CC;
import rip.lazze.libraries.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ShowStaffCommand {

    @Command(names = "showstaff", permission = "foxtrot.showstaff")
    public static void onCommand(Player player) {
        if (player.hasMetadata("nostaff")) {
            player.removeMetadata("nostaff", HCF.getInstance());
        } else {
            player.setMetadata("nostaff", new FixedMetadataValue(HCF.getInstance(), true));
        }
        player.sendMessage(CC.translate(player.hasMetadata("nostaff") ? "&aYou can see staff" : "&cYou cannot see staff"));
    }
}
