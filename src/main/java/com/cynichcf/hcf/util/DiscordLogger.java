package com.cynichcf.hcf.util;

import com.cynichcf.hcf.HCF;
import org.bukkit.Bukkit;

import java.awt.*;
import java.util.UUID;

public class DiscordLogger {

    private HCF HCF;
    public DiscordLogger(HCF HCF) { this.HCF = HCF; }

    public void logRefund(String refunded, String refunder, String reason){
        UUID uuid = Bukkit.getPlayer(refunded).getUniqueId();

        Webhook webhook = new Webhook("https://discord.com/api/webhooks/834102951110377532/doxmgZNUxqHPUg87Lwx0FOR4MM2XJvRVeBYM3TRWQ0ucAUynKYbEZ7B7zAg9bsGktRs1");
        webhook.addEmbed(new Webhook.EmbedObject()
                .setAuthor("Refund", null, null)
                .setColor(Color.RED)
                .addField("Player Refunded", refunded, false)
                .addField("Refunded by", refunder, false)
                .addField("Reason", reason, true)

        );
    }
}