package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;

public class FDToggleCommand {

    @Command(names={ "FD Toggle", "ToggleFoundDiamonds", "ToggleFD" }, permission="")
    public static void fdToggle(Player sender) {
        boolean val = !HCF.getInstance().getToggleFoundDiamondsMap().isFoundDiamondToggled(sender.getUniqueId());

        sender.sendMessage(ChatColor.YELLOW + "You are now " + (!val ? ChatColor.RED + "unable" : ChatColor.GREEN + "able") + ChatColor.YELLOW + " to see Found Diamonds messages!");
        HCF.getInstance().getToggleFoundDiamondsMap().setFoundDiamondToggled(sender.getUniqueId(), val);
    }

}