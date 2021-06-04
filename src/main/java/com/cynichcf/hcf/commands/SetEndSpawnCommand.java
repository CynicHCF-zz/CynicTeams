/*package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.Foxtrot;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetEndSpawnCommand
{
	@net.frozenorb.Library.command.Command(names = {"setendspawn"}, permission = "op")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		Foxtrot.getInstance().getConfig().set("World-Spawn.end-spawn", Utils.stringifyLocation(player.getLocation()));
		Foxtrot.getInstance().saveConfig();
		sender.sendMessage(CC.translate("&aYou have succsesfuly set End Spawn."));
		return false;
	}
}
*/