package com.cynichcf.hcf.nametag;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.Map;

class NametagThread extends Thread
{
    private static Map<NametagUpdate, Boolean> pendingUpdates;
    
    public NametagThread() {
        super("HCF - Nametag Thread");
        this.setDaemon(true);
    }
    
    @Override
    public void run() {
        while (true) {
            Iterator<NametagUpdate> pendingUpdatesIterator = NametagThread.pendingUpdates.keySet().iterator();
            while (pendingUpdatesIterator.hasNext()) {
                NametagUpdate pendingUpdate = pendingUpdatesIterator.next();
                try {
                    NametagManager.applyUpdate(pendingUpdate);
                    pendingUpdatesIterator.remove();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                Thread.sleep(NametagManager.getUpdateInterval() * 50L);
            }
            catch (InterruptedException ex2) {
                ex2.printStackTrace();
            }
        }
    }
    
    public static Map<NametagUpdate, Boolean> getPendingUpdates() {
        return NametagThread.pendingUpdates;
    }
    
    static {
        NametagThread.pendingUpdates = new ConcurrentHashMap<NametagUpdate, Boolean>();
    }
}
