package com.cynichcf.hcf.chat;

import com.google.common.collect.ImmutableMap;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.chat.listeners.ChatListener;
import com.cynichcf.hcf.chat.taks.SaveCustomPrefixesTask;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import rip.lazze.libraries.Library;

public class ChatHandler {
    private static File customPrefixesFile;

    private static AtomicInteger publicMessagesSent = new AtomicInteger();

    private Map<UUID, String> customPrefixes = new HashMap<>();

    public ChatHandler() {
        customPrefixesFile = new File(HCF.getInstance().getDataFolder(), "customPrefixes.json");
        HCF.getInstance().getServer().getPluginManager().registerEvents((Listener)new ChatListener(), (Plugin) HCF.getInstance());
        (new SaveCustomPrefixesTask()).runTaskTimerAsynchronously((Plugin) HCF.getInstance(), 6000L, 6000L);
        reloadCustomPrefixes();
    }

    public void reloadCustomPrefixes() {
        long started = System.currentTimeMillis();
        try {
            if (!customPrefixesFile.exists())
                customPrefixesFile.createNewFile();
            BasicDBObject json = (BasicDBObject)JSON.parse(FileUtils.readFileToString(customPrefixesFile));
            this.customPrefixes.clear();
            for (Map.Entry<String, Object> prefixEntry : (Iterable<Map.Entry<String, Object>>)((BasicDBObject)json.get("prefixes")).entrySet())
                this.customPrefixes.put(UUID.fromString(prefixEntry.getKey()), ChatColor.translateAlternateColorCodes('&', prefixEntry.getValue().toString()));
            int loaded = this.customPrefixes.size();
            long timeElapsed = System.currentTimeMillis() - started;
            HCF.getInstance().getLogger().warning("Loaded " + loaded + " custom chat prefix" + ((loaded == 1) ? "" : "es") + " in " + timeElapsed + "ms");
        } catch (Exception e) {
            HCF.getInstance().getLogger().warning("Failed to load custom chat prefixes: " + e.getMessage());
        }
    }

    public void saveCustomPrefixes() {
        try {
            long started = System.currentTimeMillis();
            BasicDBObject json = new BasicDBObject("prefixes", this.customPrefixes);
            FileUtils.write(customPrefixesFile, Library.GSON.toJson((new JsonParser()).parse(json.toString())));
            int loaded = this.customPrefixes.size();
            long timeElapsed = System.currentTimeMillis() - started;
            HCF.getInstance().getLogger().warning("Saved " + loaded + " custom chat prefix" + ((loaded == 1) ? "" : "es") + " in " + timeElapsed + "ms");
        } catch (Exception e) {
            HCF.getInstance().getLogger().warning("Failed to save custom chat prefixes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean hasCustomPrefix(UUID player) {
        return this.customPrefixes.containsKey(player);
    }

    public String getCustomPrefix(UUID player) {
        if (this.customPrefixes.containsKey(player))
            return this.customPrefixes.get(player);
        return "";
    }

    public void setCustomPrefix(UUID player, String customPrefix) {
        if (customPrefix == null || customPrefix.isEmpty()) {
            this.customPrefixes.remove(player);
        } else {
            this.customPrefixes.put(player, customPrefix);
        }
    }

    public Collection<Map.Entry<UUID, String>> getAllCustomPrefixes() {
        return (Collection<Map.Entry<UUID, String>>)ImmutableMap.copyOf(this.customPrefixes).entrySet();
    }

    public static AtomicInteger getPublicMessagesSent() {
        return publicMessagesSent;
    }
}