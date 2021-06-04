package com.cynichcf.hcf.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class ClientCommandPacketAdaper extends PacketAdapter {

    public ClientCommandPacketAdaper() {
        super(HCF.getInstance(), PacketType.Play.Client.CLIENT_COMMAND);
    }

    
    public void onPacketReceiving(PacketEvent event) {
        if (event.getPacket().getClientCommands().read(0) == EnumWrappers.ClientCommand.PERFORM_RESPAWN) {
            if (!HCF.getInstance().getDeathbanMap().isDeathbanned(event.getPlayer().getUniqueId())) {
                return;
            }

            if (HCF.getInstance().getMapHandler().isPurgatory()) {
                event.getPlayer().teleport(HCF.getInstance().getMapHandler().getPurgatoryHandler().getPurgatoryLocation());
                return;
            }

            long unbannedOn = HCF.getInstance().getDeathbanMap().getDeathban(event.getPlayer().getUniqueId());
            long left = unbannedOn - System.currentTimeMillis();
            String time = TimeUtils.formatIntoDetailedString((int) left / 1000);
            event.setCancelled(true);

            new BukkitRunnable() {

                public void run() {
                    event.getPlayer().setMetadata("loggedout", new FixedMetadataValue(HCF.getInstance(), true));

                    if (HCF.getInstance().getServerHandler().isPreEOTW()) {
                        event.getPlayer().kickPlayer(ChatColor.YELLOW + "Come back tomorrow for SOTW!");
                    } else {
                        event.getPlayer().kickPlayer(ChatColor.YELLOW + "Come back in " + time + "!");
                    }
                }

            }.runTask(HCF.getInstance());
        }
    }

}