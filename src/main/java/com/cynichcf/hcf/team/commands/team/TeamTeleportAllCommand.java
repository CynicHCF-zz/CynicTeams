package com.cynichcf.hcf.team.commands.team;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

public class TeamTeleportAllCommand {

    @Command(names={ "team tpall", "t tpall", "f tpall", "faction tpall", "fac tpall" }, permission="foxtrot.factiontpall")
    public static void teamTP(Player sender, @Param(name="team") Team team) {
        ConversationFactory factory = new ConversationFactory(HCF.getInstance()).withModality(true).withPrefix(new NullConversationPrefix()).withFirstPrompt(new StringPrompt() {

            public String getPromptText(ConversationContext context) {
                return "§aAre you sure you want to teleport all players in " + team.getName() + " ("  + team.getOnlineMembers().size() + ") to your location? Type §byes§a to confirm or §cno§a to quit.";
            }

            
            public Prompt acceptInput(ConversationContext cc, String s) {
                if (s.equalsIgnoreCase("yes")) {
                    for (Player player : team.getOnlineMembers()) {
                        player.teleport(sender.getLocation());
                    }

                    sender.sendMessage(ChatColor.GREEN + "Teleported " + team.getOnlineMembers().size() + " to you.");
                    return Prompt.END_OF_CONVERSATION;
                }

                if (s.equalsIgnoreCase("no")) {
                    cc.getForWhom().sendRawMessage(ChatColor.GREEN + "Cancelled.");
                    return Prompt.END_OF_CONVERSATION;
                }

                cc.getForWhom().sendRawMessage(ChatColor.GREEN + "Unrecognized response. Type §b/yes§a to confirm or §c/no§a to quit.");
                return Prompt.END_OF_CONVERSATION;
            }

        }).withLocalEcho(false).withEscapeSequence("/no").withTimeout(10).thatExcludesNonPlayersWithMessage("Go away evil console!");

        Conversation con = factory.buildConversation(sender);
        sender.beginConversation(con);
    }

}