package com.cynichcf.hcf.chat.taks;

import com.cynichcf.hcf.HCF;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveCustomPrefixesTask extends BukkitRunnable {
    public void run() {
        HCF.getInstance().getChatHandler().saveCustomPrefixes();
    }
}
