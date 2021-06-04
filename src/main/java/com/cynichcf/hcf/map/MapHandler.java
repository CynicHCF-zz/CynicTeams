package com.cynichcf.hcf.map;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.cynichcf.hcf.events.koth.KOTH;
import com.cynichcf.hcf.listener.BorderListener;
import com.cynichcf.hcf.map.deathban.DeathbanListener;
import com.cynichcf.hcf.map.deathban.purgatory.PurgatoryHandler;
import com.cynichcf.hcf.map.killstreaks.KillstreakHandler;
import com.cynichcf.hcf.map.kits.KitManager;
import com.cynichcf.hcf.map.kits.command.KitBypassCommand;
import com.cynichcf.hcf.map.kits.command.KitCommand;
import com.cynichcf.hcf.map.kits.command.KitCommands;
import com.cynichcf.hcf.map.stats.StatsHandler;
import com.cynichcf.hcf.server.Deathban;
import com.cynichcf.hcf.server.ServerHandler;
import com.cynichcf.hcf.team.track.TeamActionTracker;
import lombok.Getter;
import lombok.Setter;
import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.EventHandler;
import com.cynichcf.hcf.nametag.FoxtrotNametagProvider;
import com.cynichcf.hcf.scoreboard.FoxtrotScoreboardConfiguration;
import rip.lazze.libraries.Library;
import rip.lazze.libraries.command.FrozenCommandHandler;
import rip.lazze.libraries.framework.economy.FrozenEconomyHandler;
import rip.lazze.libraries.nametag.FrozenNametagHandler;
import rip.lazze.libraries.scoreboard.FrozenScoreboardHandler;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MapHandler {

    private transient File mapInfo;
    @Getter private boolean kitMap;
    @Getter private int allyLimit;
    @Getter private int teamSize;
    @Getter private long regenTimeDeath;
    @Getter private long regenTimeRaidable;
    @Getter private String scoreboardTitle;
    @Getter private String mapStartedString;
    @Getter private double baseLootingMultiplier;
    @Getter private double level1LootingMultiplier;
    @Getter private double level2LootingMultiplier;
    @Getter private double level3LootingMultiplier;
    @Getter private boolean craftingGopple;
    @Getter private boolean craftingReducedMelon;
    @Getter private int goppleCooldown;
    @Getter private int minForceInviteMembers = 10;
    @Getter private String endPortalLocation;
    @Getter private boolean fastSmeltEnabled;
    @Getter @Setter private int netherBuffer;
    @Getter @Setter private int worldBuffer;
    @Getter private float dtrIncrementMultiplier;

    // Kit-Map only stuff:
    @Getter private StatsHandler statsHandler;
    @Getter private KillstreakHandler killstreakHandler;
    @Getter private KitManager kitManager;

    // Deathban stuff:
    @Getter private boolean purgatory;
    @Getter private PurgatoryHandler purgatoryHandler;

    // Other stuff:

    public MapHandler() {}
    
    public void load() {
        reloadConfig();

       // FrozenNametagHandler.registerProvider(new FoxtrotNametagProvider());
        FrozenScoreboardHandler.setConfiguration(FoxtrotScoreboardConfiguration.create());
        FrozenEconomyHandler.init();
        
        Iterator<Recipe> recipeIterator = HCF.getInstance().getServer().recipeIterator();

        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();

            // Disallow the crafting of gopples.
            if (!craftingGopple && recipe.getResult().getDurability() == (short) 1 && recipe.getResult().getType() == org.bukkit.Material.GOLDEN_APPLE) {
                recipeIterator.remove();
            }

            // Remove vanilla glistering melon recipe
            if (craftingReducedMelon && recipe.getResult().getType() == Material.SPECKLED_MELON) {
                recipeIterator.remove();
            }

            if (HCF.getInstance().getConfig().getBoolean("rodPrevention") && recipe.getResult().getType() == Material.FISHING_ROD) {
                recipeIterator.remove();
            }

            if (recipe.getResult().getType() == Material.EXPLOSIVE_MINECART) {
                recipeIterator.remove();
            }
        }

        // add our glistering melon recipe
        if (craftingReducedMelon) {
            HCF.getInstance().getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.SPECKLED_MELON)).addIngredient(Material.MELON).addIngredient(Material.GOLD_NUGGET));
        }

        ShapedRecipe nametagRecipe = new ShapedRecipe(new ItemStack(Material.NAME_TAG));
        ShapedRecipe saddleRecipe = new ShapedRecipe(new ItemStack(Material.SADDLE));
        ShapedRecipe horseArmorRecipe = new ShapedRecipe(new ItemStack(Material.DIAMOND_BARDING));

        nametagRecipe.shape(" I ",
                                                     " P ",
                                                     " S ");
        nametagRecipe.setIngredient('I', Material.INK_SACK);
        nametagRecipe.setIngredient('P', Material.PAPER);
        nametagRecipe.setIngredient('S', Material.STRING);

        saddleRecipe.shape("  L",
                                                 "LLL",
                                                 "B B");
        saddleRecipe.setIngredient('L', Material.LEATHER);
        saddleRecipe.setIngredient('B', Material.LEASH);

        horseArmorRecipe.shape(" SD",
                                                            "BBL",
                                                            "LL ");
        horseArmorRecipe.setIngredient('S', Material.SADDLE);
        horseArmorRecipe.setIngredient('D', Material.DIAMOND);
        horseArmorRecipe.setIngredient('B', Material.DIAMOND_BLOCK);
        horseArmorRecipe.setIngredient('L', Material.LEATHER);

        HCF.getInstance().getServer().addRecipe(nametagRecipe);
        HCF.getInstance().getServer().addRecipe(saddleRecipe);
        HCF.getInstance().getServer().addRecipe(horseArmorRecipe);

        statsHandler = new StatsHandler();
        if (isKitMap() || HCF.getInstance().getServerHandler().isVeltKitMap()) {

            killstreakHandler = new KillstreakHandler();
            kitManager = new KitManager();

            // start a KOTH after 5 minutes of uptime
            Bukkit.getScheduler().runTaskLater(HCF.getInstance(), () -> {
                EventHandler kothHandler = HCF.getInstance().getEventHandler();
                List<KOTH> koths = new ArrayList<>(kothHandler.getEvents().stream().filter(e -> e instanceof KOTH).map(e -> (KOTH) e).collect(Collectors.toList()));

                if (koths.isEmpty()) {
                    return;
                }

                KOTH selected = koths.get(Library.RANDOM.nextInt(koths.size()));
                selected.activate();
            }, 5 * 60 * 20);

            TeamActionTracker.setDatabaseLogEnabled(false);
        } else {
            FrozenCommandHandler.unregisterClass(KitBypassCommand.class);
            FrozenCommandHandler.unregisterClass(KitCommand.class);
            FrozenCommandHandler.unregisterClass(KitCommands.class);

            purgatory = HCF.getInstance().getConfig().getBoolean("purgatory", false);

            if (purgatory)
                purgatoryHandler = new PurgatoryHandler();
            else
                HCF.getInstance().getServer().getPluginManager().registerEvents(new DeathbanListener(), HCF.getInstance());
        }
    }

    public void save() {

        if (purgatory)
            purgatoryHandler.save();
    }

    public void reloadConfig() {
        try {
            mapInfo = new File(HCF.getInstance().getDataFolder(), "mapInfo.json");

            if (!mapInfo.exists()) {
                mapInfo.createNewFile();

                BasicDBObject dbObject = getDefaults();

                FileUtils.write(mapInfo, Library.GSON.toJson(new JsonParser().parse(dbObject.toString())));
            } else {
                // basically check for any new keys in the defaults which aren't contained in the actual file
                // if there are any, add them to the file.
                BasicDBObject file = (BasicDBObject) JSON.parse(FileUtils.readFileToString(mapInfo));

                BasicDBObject defaults = getDefaults();

                defaults.keySet().stream().filter(key -> !file.containsKey(key)).forEach(key -> file.put(key, defaults.get(key)));

                FileUtils.write(mapInfo, Library.GSON.toJson(new JsonParser().parse(file.toString())));
            }

            BasicDBObject dbObject = (BasicDBObject) JSON.parse(FileUtils.readFileToString(mapInfo));

            if (dbObject != null) {
                this.kitMap = dbObject.getBoolean("kitMap", false);
                this.allyLimit = dbObject.getInt("allyLimit", 0);
                this.teamSize = dbObject.getInt("teamSize", 8);
                this.regenTimeDeath = TimeUnit.MINUTES.toMillis(dbObject.getInt("regenTimeDeath", 60));
                this.regenTimeRaidable = TimeUnit.MINUTES.toMillis(dbObject.getInt("regenTimeRaidable", 60));
                this.scoreboardTitle = ChatColor.translateAlternateColorCodes('&', HCF.getInstance().getConfig().getString("scoreboard.tittle"));
                this.mapStartedString = dbObject.getString("mapStartedString");
                ServerHandler.WARZONE_RADIUS = dbObject.getInt("warzone", 1000);
                BorderListener.BORDER_SIZE = dbObject.getInt("border", 3000);
                this.goppleCooldown = dbObject.getInt("goppleCooldown");
                this.netherBuffer = dbObject.getInt("netherBuffer");
                this.worldBuffer = dbObject.getInt("worldBuffer");
                this.endPortalLocation = dbObject.getString("endPortalLocation");
                this.fastSmeltEnabled = dbObject.getBoolean("fastSmeltEnabled", true);

                BasicDBObject looting = (BasicDBObject) dbObject.get("looting");

                this.baseLootingMultiplier = looting.getDouble("base");
                this.level1LootingMultiplier = looting.getDouble("level1");
                this.level2LootingMultiplier = looting.getDouble("level2");
                this.level3LootingMultiplier = looting.getDouble("level3");

                BasicDBObject crafting = (BasicDBObject) dbObject.get("crafting");

                this.craftingGopple = crafting.getBoolean("gopple");
                this.craftingReducedMelon = crafting.getBoolean("reducedMelon");

                if (dbObject.containsKey("deathban")) {
                    BasicDBObject deathban = (BasicDBObject) dbObject.get("deathban");

                    Deathban.load(deathban);
                }

                if (dbObject.containsKey("minForceInviteMembers")) {
                    minForceInviteMembers = dbObject.getInt("minForceInviteMembers");
                }

                this.dtrIncrementMultiplier = (float) dbObject.getDouble("dtrIncrementMultiplier", 4.5F);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BasicDBObject getDefaults() {
        BasicDBObject dbObject = new BasicDBObject();
        BasicDBObject looting = new BasicDBObject();
        BasicDBObject crafting = new BasicDBObject();
        BasicDBObject deathban = new BasicDBObject();
        dbObject.put("kitMap", false);
        dbObject.put("allyLimit", 0);
        dbObject.put("teamSize", 8);
        dbObject.put("regenTimeDeath", 60);
        dbObject.put("regenTimeRaidable", 60);
        dbObject.put("scoreboardTitle", "edit on config.yml");
        dbObject.put("mapStartedString", "Map 3 - Started January 31, 2015");
        dbObject.put("warzone", 1000);
        dbObject.put("netherBuffer", 150);
        dbObject.put("worldBuffer", 300);
        dbObject.put("endPortalLocation", "1000, 1000");
        dbObject.put("border", 3000);
        dbObject.put("goppleCooldown", TimeUnit.HOURS.toMinutes(4));
        dbObject.put("fastSmeltEnabled", true);
        looting.put("base", 1D);
        looting.put("level1", 1.2D);
        looting.put("level2", 1.4D);
        looting.put("level3", 2D);
        dbObject.put("looting", looting);
        crafting.put("gopple", true);
        crafting.put("reducedMelon", true);
        dbObject.put("crafting", crafting);
        deathban.put("PARTNER", 120);
        deathban.put("FAMOUS", 120);
        deathban.put("CYNIC", 120);
        deathban.put("YOUTUBER", 120);
        deathban.put("PLATINUM", 120);
        deathban.put("DIAMOND", 120);
        deathban.put("GOLD", 120);
        deathban.put("IRON", 120);
        deathban.put("DEFAULT", 120);
        dbObject.put("deathban", deathban);
        dbObject.put("minForceInviteMembers", 10);
        dbObject.put("dtrIncrementMultiplier", 4.5F);
        return dbObject;
    }

    public void saveBorder() {
        try {
            BasicDBObject dbObject = (BasicDBObject) JSON.parse(FileUtils.readFileToString(mapInfo));

            if (dbObject != null) {
                dbObject.put("border", BorderListener.BORDER_SIZE); // update the border

                FileUtils.write(mapInfo, Library.GSON.toJson(new JsonParser().parse(dbObject.toString()))); // save it exactly like it was except for the border that was changed.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveNetherBuffer() {
        try {
            BasicDBObject dbObject = (BasicDBObject) JSON.parse(FileUtils.readFileToString(mapInfo));

            if (dbObject != null) {
                dbObject.put("netherBuffer", HCF.getInstance().getMapHandler().getNetherBuffer()); // update the nether buffer

                FileUtils.write(mapInfo, Library.GSON.toJson(new JsonParser().parse(dbObject.toString()))); // save it exactly like it was except for the nether that was changed.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveWorldBuffer() {
        try {
            BasicDBObject dbObject = (BasicDBObject) JSON.parse(FileUtils.readFileToString(mapInfo));

            if (dbObject != null) {
                dbObject.put("worldBuffer", HCF.getInstance().getMapHandler().getWorldBuffer()); // update the world buffer

                FileUtils.write(mapInfo, Library.GSON.toJson(new JsonParser().parse(dbObject.toString()))); // save it exactly like it was except for the nether that was changed.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}