package com.cynichcf.hcf.team.commands.team;

import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.command.CommandSender;

public class TeamJSONCommand {

    @Command(names={ "team json", "t json", "f json", "faction json", "fac json" }, permission="op")
    public static void teamJSON(CommandSender sender, @Param(name="team", defaultValue="self") Team team) {
        sender.sendMessage(team.toJSON().toString());
    }

}