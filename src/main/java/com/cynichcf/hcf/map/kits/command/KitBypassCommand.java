package com.cynichcf.hcf.map.kits.command;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KitBypassCommand {

	@Command(names = "kitbypass", permission = "op")
	public static void kitBypass(Player sender, @Param(name = "kitname") String name) {
		if (!HCF.getInstance().getMapHandler().isKitMap() && !HCF.getInstance().getServerHandler().isVeltKitMap()) {
			sender.sendMessage("§cThis is a KitMap only command.");
			return;
		}

		if (HCF.getInstance().getMapHandler().getKitManager().getUseAll().contains(name.toLowerCase())) {
			HCF.getInstance().getMapHandler().getKitManager().getUseAll().remove(name.toLowerCase());
			sender.sendMessage(ChatColor.RED + "Removed " + name + " from bypass list!");
		} else {
			HCF.getInstance().getMapHandler().getKitManager().getUseAll().add(name.toLowerCase());
			sender.sendMessage(ChatColor.GREEN + "Added " + name + " to bypass list!");
		}
	}

}