package com.cynichcf.hcf.events.citadel.tasks;

import com.cynichcf.hcf.HCF;
import org.bukkit.scheduler.BukkitRunnable;

public class CitadelSaveTask extends BukkitRunnable {

    public void run() {
        HCF.getInstance().getCitadelHandler().saveCitadelInfo();
    }

}