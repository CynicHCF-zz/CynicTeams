package com.cynichcf.hcf.poll;

public class PollCommand {

//    @Command(names = {"poll", "polls"}, async = true, permission = "")
//    public static void pollcmd(Player sender) {
//        if (HCF.getInstance().getPollHandler().getCurrentPoll() != null) {
//            new PollMenu().openMenu(sender);
//        } else if (sender.isOp()) {
//            sender.sendMessage(CC.translate("&cCorrect Usage /poll <create, close, addanswer, delete>"));
//        }
//    }
//    @Command(names = { "poll create", "polls create" }, async = true, permission = "poll.admin")
//    public static void pollcreate(Player sender, @Param(name = "question", wildcard = true)String question) {
//        HCF.getInstance().getPollHandler().createPoll(new Poll(question, Collections.emptyList()));
//    }
//
//    @Command(names = { "poll addanswer", "polls addanswer"}, async = true, permission = "poll.admin")
//    public static void polladdanswer(Player sender, @Param(name = "addanswer", wildcard = true)String answer) {
//        List<String> answers = new ArrayList<>(HCF.getInstance().getPollHandler().getCurrentPoll().getAnswers());
//
//        if (answers.contains(answer)) {
//            sender.sendMessage(CC.translate("&cThat answer already exists."));
//            return;
//        }
//
//        answers.add(answer);
//
//        HCF.getInstance().getPollHandler().getCurrentPoll().setAnswers(answers);
//
//        sender.sendMessage(CC.translate(" &eYou have added &7\"&f" + answer + "&7\" &eto the answer list."));
//    }
//
//    @Command(names = { "poll close", "polls close" }, async = true, permission = "poll.admin")
//    public static void pollclose(Player sender) {
//        Bukkit.broadcastMessage(CC.translate("&7&m------------------------------------"));
//        Bukkit.broadcastMessage(CC.translate(""));
//        Bukkit.broadcastMessage(CC.translate(" &eThe poll has ended. The results are the following:"));
//
//        for (String answer : HCF.getInstance().getPollHandler().getCurrentPoll().getAnswers()) {
//            Bukkit.broadcastMessage(CC.translate("   &7* &6" + answer + "&7: &f" + HCF.getInstance().getPollHandler().getPercentage(answer) + "%"));
//        }
//
//        Bukkit.broadcastMessage(CC.translate(""));
//        Bukkit.broadcastMessage(CC.translate("&7&m------------------------------------"));
//
//        sender.sendMessage(CC.translate(" &eYou have closed the poll. The winner was &7\"&f" + HCF.getInstance().getPollHandler().getWinner() + "&7\"&e."));
//        HCF.getInstance().getPollHandler().setCurrentPoll(null);
//        HCF.getInstance().getPollHandler().setVotedUsers(new ArrayList<>());
//    }
//
//    @Command(names = { "poll delete", "polls delete" }, async = true, permission = "poll.admin")
//    public static void polldelete(Player sender) {
//        HCF.getInstance().getPollHandler().setCurrentPoll(null);
//        HCF.getInstance().getPollHandler().setVotedUsers(new ArrayList<>());
//        sender.sendMessage(CC.translate(" &eYou have deleted the current poll."));
//    }

}
