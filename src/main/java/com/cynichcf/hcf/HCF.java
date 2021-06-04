package com.cynichcf.hcf;

import com.comphenix.protocol.ProtocolLibrary;
import com.cynichcf.hcf.events.purge.listener.PurgeListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.cynichcf.hcf.challenges.listener.ChallengeListener;
//import com.cynichcf.hcf.credit.CreditHandler;
import com.cynichcf.hcf.events.citadel.CitadelHandler;
import com.cynichcf.hcf.events.conquest.ConquestHandler;
import com.cynichcf.hcf.events.killtheking.KingEventListener;
import com.cynichcf.hcf.events.region.carepackage.CarePackageHandler;
import com.cynichcf.hcf.events.region.cavern.CavernHandler;
import com.cynichcf.hcf.events.region.glowmtn.GlowHandler;
import com.cynichcf.hcf.listener.*;
import com.cynichcf.hcf.map.MapHandler;
import com.cynichcf.hcf.map.deathban.purgatory.PurgatoryHandler;
import com.cynichcf.hcf.map.kits.KitListener;
import com.cynichcf.hcf.nametag.FoxtrotNametagProvider;
import com.cynichcf.hcf.nametag.NametagManager;
import com.cynichcf.hcf.persist.RedisSaveTask;
import com.cynichcf.hcf.persist.maps.*;
import com.cynichcf.hcf.powers.FightHandler;
import com.cynichcf.hcf.protocol.ClientCommandPacketAdaper;
import com.cynichcf.hcf.protocol.SignGUIPacketAdaper;
import com.cynichcf.hcf.reclaim.ReclaimHandler;
import com.cynichcf.hcf.reclaim.config.ReclaimConfigFile;
import com.cynichcf.hcf.server.EnderpearlCooldownHandler;
import com.cynichcf.hcf.server.ServerHandler;
import com.cynichcf.hcf.tab.DefaultFoxtrotTabLayoutProvider;
import com.cynichcf.hcf.tasks.RallyExpireTask;
import com.cynichcf.hcf.team.TeamHandler;
import com.cynichcf.hcf.team.claims.LandBoard;
import com.cynichcf.hcf.team.commands.team.TeamClaimCommand;
import com.cynichcf.hcf.team.commands.team.subclaim.TeamSubclaimCommand;
import com.cynichcf.hcf.team.dtr.DTRHandler;
import com.cynichcf.hcf.util.CC;
import com.cynichcf.hcf.util.DiscordLogger;
import com.cynichcf.hcf.util.RegenUtils;
import lombok.Getter;
import lombok.Setter;
import com.cynichcf.hcf.chat.ChatHandler;
import com.cynichcf.hcf.giveaway.GiveawayHandler;
import com.cynichcf.hcf.giveaway.GiveawayListener;
import com.cynichcf.hcf.deathmessage.DeathMessageHandler;
import com.cynichcf.hcf.elevators.ElevatorListener;
import com.cynichcf.hcf.events.EventHandler;
import com.cynichcf.hcf.packetborder.PacketBorderThread;
//import com.cynichcf.hcf.poll.PollHandler;
import com.cynichcf.hcf.pvpclasses.PvPClassHandler;
import com.cynichcf.hcf.pvpclasses.pvpclasses.ArcherClass;
import com.cynichcf.hcf.pvpclasses.pvpclasses.BardClass;
import com.cynichcf.hcf.pvpclasses.pvpclasses.RogueClass;
import com.cynichcf.hcf.tournaments.handler.TournamentHandler;
import com.cynichcf.hcf.tournaments.listener.TournamentListener;
//import com.cynichcf.hcf.vouchers.VoucherHandler;
import net.minecraft.server.v1_7_R4.Item;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import rip.lazze.libraries.Library;
import rip.lazze.libraries.command.FrozenCommandHandler;
import rip.lazze.libraries.tab.FrozenTabHandler;
import rip.lazze.libraries.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HCF extends JavaPlugin {

	public static String MONGO_DB_NAME = "HCTeams";

	@Getter private static HCF instance;
	@Getter @Setter private CreditsMap creditsMap;
	@Getter private MongoClient mongoPool;
	@Getter private ChatHandler chatHandler;
	@Getter private PvPClassHandler pvpClassHandler;
	@Getter private CarePackageHandler carePackageHandler;
	@Getter private TeamHandler teamHandler;
	@Getter @Setter private FightHandler fightHandler;
	@Getter private ServerHandler serverHandler;
	@Getter @Setter private PurgatoryHandler purgatoryHandler;
	@Getter private MapHandler mapHandler;
	@Getter private CitadelHandler citadelHandler;
	@Getter private EventHandler eventHandler;
	@Getter private ConquestHandler conquestHandler;
//	@Getter private PollHandler pollHandler;
	@Getter private CavernHandler cavernHandler;
	@Getter private GlowHandler glowHandler;
	@Getter private PlaytimeMap playtimeMap;
	@Getter private OppleMap oppleMap;
	@Getter private DeathbanMap deathbanMap;
	@Getter private com.cynichcf.hcf.persist.maps.PvPTimerMap PvPTimerMap;
	@Getter private StartingPvPTimerMap startingPvPTimerMap;
	@Getter private DeathsMap deathsMap;
	@Getter private KillsMap killsMap;
	@Getter private ChatModeMap chatModeMap;
	@Getter private FishingKitMap fishingKitMap;
	@Getter private ToggleGlobalChatMap toggleGlobalChatMap;
	@Getter private ToggleLFFMessageMap toggleLFFMessageMap;
	@Getter private StaffBoardMap staffBoardMap;
	@Getter private AbilityCooldownsMap abilityCooldownsMap;
	@Getter private FDisplayMap fDisplayMap;
	@Getter private CheatbreakerNotificationMap cheatbreakerNotificationMap;
	@Getter private ReceiveFactionInviteMap receiveFactionInviteMap;
	@Getter private TeamColorMap teamColorMap;
	@Getter private EnemyColorMap enemyColorMap;
	@Getter private AllyColorMap allyColorMap;
	@Getter private ArcherTagColorMap archerTagColorMap;
	@Getter private FocusColorMap focusColorMap;
//	@Getter @Setter private CreditHandler creditHandler;
	@Getter private ClassCooldownsMap classCooldownsMap;
	@Getter private ChatSpyMap chatSpyMap;
	@Getter private DiamondMinedMap diamondMinedMap;
	@Getter private GoldMinedMap goldMinedMap;
	@Getter private IronMinedMap ironMinedMap;
	@Getter private CoalMinedMap coalMinedMap;
	@Getter private RedstoneMinedMap redstoneMinedMap;
	@Getter private LapisMinedMap lapisMinedMap;
	@Getter private EmeraldMinedMap emeraldMinedMap;
	@Getter private FirstJoinMap firstJoinMap;
	@Getter private ReclaimConfigFile reclaimConfig;
	@Getter ReclaimHandler reclaimHandler;
	@Getter private LastJoinMap lastJoinMap;
	@Getter private SoulboundLivesMap soulboundLivesMap;
	@Getter private FriendLivesMap friendLivesMap;
	@Getter private WrappedBalanceMap wrappedBalanceMap;
	@Getter private ToggleFoundDiamondsMap toggleFoundDiamondsMap;
	@Getter private ToggleDeathMessageMap toggleDeathMessageMap;
	@Getter private TabListModeMap tabListModeMap;
	@Getter private CobblePickupMap cobblePickupMap;
	@Getter private KDRMap kdrMap;
	@Getter private GiveawayHandler giveawayHandler;
	@Getter @Setter private ArcherClass archerClass;
	@Getter @Setter private BardClass bardClass;
	@Getter @Setter private RogueClass rogueClass;
	@Getter @Setter private ELOMap eloMap;
	private TournamentHandler tournamentHandler;
	//private VoucherHandler voucherHandler;
	private DiscordLogger discordLogger;
	@Getter private CombatLoggerListener combatLoggerListener;
	@Getter @Setter private Predicate<Player> inDuelPredicate = (player) -> false;


	public void onEnable() {

		if (Bukkit.getServerName().contains(" ")) {
			System.out.println("*********************************************");
			System.out.println("               ATTENTION");
			System.out.println("SET server-name VALUE IN server.properties TO");
			System.out.println("A PROPER SERVER NAME. THIS WILL BE USED AS THE");
			System.out.println("MONGO DATABASE NAME.");
			System.out.println("*********************************************");
			this.getServer().shutdown();
			return;
		}

		instance = this;
		saveDefaultConfig();
		try {
			String host = getConfig().getString("Mongo.Host", "127.0.0.1");
			String authDB = getConfig().getString("Mongo.AuthDB", "admin");
			String username = getConfig().getString("Mongo.Username", "CynicHCF");
			String password = getConfig().getString("Mongo.Password", "");

			boolean authRequired = password.length() > 0;
			ServerAddress address = new ServerAddress(host, 27017);

			if (!authRequired) {
				mongoPool = new MongoClient(address);
			} else {
				mongoPool = new MongoClient(address, MongoCredential.createCredential(
						username,
						authDB,
						password.toCharArray()
				), MongoClientOptions.builder()
						.retryWrites(true)
						.build());
			}

			MONGO_DB_NAME = Bukkit.getServerName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		(new DTRHandler()).runTaskTimer(this, 20L, 1200L);
		(new DTRHandler()).runTaskAsynchronously(this);
		(new RedisSaveTask()).runTaskTimerAsynchronously(this, 1200L, 1200L);
		(new PacketBorderThread()).start();
		NametagManager.init();
		NametagManager.registerProvider(new FoxtrotNametagProvider());

//		if (Bukkit.getWorld("mars") == null) {
//			Bukkit.createWorld(new WorldCreator("mars"));
//		}
		setupHandlers();
		setupPersistence();
		setupListeners();
		reclaimConfig = new ReclaimConfigFile(this, "reclaims", this.getDataFolder().getAbsolutePath());
		getServer().getPluginManager().registerEvents(new ClientListener(), this);
		if (HCF.getInstance().getConfig().getBoolean("tab.normal")) {
			FrozenTabHandler.setLayoutProvider(new DefaultFoxtrotTabLayoutProvider());
		}
		ProtocolLibrary.getProtocolManager().addPacketListener(new SignGUIPacketAdaper());
		ProtocolLibrary.getProtocolManager().addPacketListener(new ClientCommandPacketAdaper());

		for (World world : Bukkit.getWorlds()) {
			world.setThundering(false);
			world.setStorm(false);
			world.setWeatherDuration(Integer.MAX_VALUE);
			world.setGameRuleValue("doFireTick", "false");
			world.setGameRuleValue("mobGriefing", "false");
			world.setGameRuleValue("doMobGriefing", "false");
		}

		if (getConfig().getBoolean("legions")) {
			try {
				Field field = Item.class.getDeclaredField("maxStackSize");
				field.setAccessible(true);
				field.setInt(Item.getById(322), 6);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (getConfig().getBoolean("powers")) {
			System.out.println("*********************************************");
			System.out.println("               Powers Mode Enabled");
			System.out.println("*********************************************");
		}
		EndListener.loadEndReturn();
	//	if (!new ChestListerner(HCF.getInstance().getConfig().getString("LICENSE-KEY"), "https://lazzeservice.000webhostapp.com/verify.php", this).register())
	//		return;

		new BukkitRunnable() {
			public void run() {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "difficulty peaceful");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule doMobSpawning false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule commandBlockOutput false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule logAdminCommands false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule mobGriefing false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule doDaylightCycle false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "difficulty normal");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time set day");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "timings off");
			}
		}.runTaskLater( this, 300L);
		new BukkitRunnable() {
			public void run() {
				String players = Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("top.rank") && !player.isOp() && !player.hasPermission("*")).map(HumanEntity::getName).collect(Collectors.joining(", "));
				if (players.isEmpty()) {
					players = "No online users";
				}
				Bukkit.broadcastMessage(CC.translate("&6&lOnline Donators&7: ") + ChatColor.WHITE + players);
				Bukkit.broadcastMessage(CC.translate("&eYou can purchase the rank at " + HCF.getInstance().getConfig().getString("store")));
			}
		}.runTaskTimer(this, 200L, 18000L);
	}

	public void onDisable() {
		getEventHandler().saveEvents();

		for (Player player : HCF.getInstance().getServer().getOnlinePlayers()) {
			getPlaytimeMap().playerQuit(player.getUniqueId(), false);
			player.setMetadata("loggedout", new FixedMetadataValue(this, true));
		}

		for (String playerName : PvPClassHandler.getEquippedKits().keySet()) {
			PvPClassHandler.getEquippedKits().get(playerName).remove(getServer().getPlayerExact(playerName));
		}

		for (Entity e : this.combatLoggerListener.getCombatLoggers()) {
			if (e != null) {
				e.remove();
			}
		}
		RedisSaveTask.save(null, false);
		HCF.getInstance().getServerHandler().save();
		HCF.getInstance().getMapHandler().save();
		HCF.getInstance().getMapHandler().getStatsHandler().save();
		RegenUtils.resetAll();
		//getVoucherHandler().save();
		Library.getInstance().runRedisCommand((jedis) -> {
			jedis.save();
			return null;
		});
	}

	private void setupHandlers() {
		serverHandler = new ServerHandler();
		giveawayHandler = new GiveawayHandler();
		mapHandler = new MapHandler();
		mapHandler.load();
		//pollHandler = new PollHandler();
		teamHandler = new TeamHandler();
		LandBoard.getInstance().loadFromTeams();
		reclaimHandler = new ReclaimHandler();
		chatHandler = new ChatHandler();
		citadelHandler = new CitadelHandler();
		pvpClassHandler = new PvPClassHandler();
		eventHandler = new EventHandler();
		conquestHandler = new ConquestHandler();
		carePackageHandler = new CarePackageHandler();
		tournamentHandler = new TournamentHandler();
		discordLogger = new DiscordLogger(this);

		if (getConfig().getBoolean("glowstoneMountain", false)) {
			glowHandler = new GlowHandler();
		}

		if (getConfig().getBoolean("cavern", false)) {
			cavernHandler = new CavernHandler();
		}

		FrozenCommandHandler.registerAll(this);
		for (Class<?> clazz : ClassUtils.getClassesInPackage(this, getClass().getPackage().getName())) {
			if (clazz == null || clazz.getCanonicalName() == null) continue;
			if (clazz.getCanonicalName().contains("conditional")) continue;
			FrozenCommandHandler.registerClass(clazz);
		}

		DeathMessageHandler.init();
		DTRHandler.loadDTR();
	}

	private void setupListeners() {
		getServer().getPluginManager().registerEvents(new SettingsSignListener(), this);
//		getServer().getPluginManager().registerEvents(new CustomEnchantListener(), this);
		//getServer().getPluginManager().registerEvents(new DonorKitsListener(), this);
//		getServer().getPluginManager().registerEvents(new ArmorListener(), this);
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		getServer().getPluginManager().registerEvents(new DisposibalSignListener(this), this);
		getServer().getPluginManager().registerEvents(new BeaconListener(this), this);
		getServer().getPluginManager().registerEvents(new GiveawayListener(), this);
		getServer().getPluginManager().registerEvents(new PurgeListener(), this);
		getServer().getPluginManager().registerEvents(new MapListener(), this);
		getServer().getPluginManager().registerEvents(new AntiGlitchListener(), this);
		getServer().getPluginManager().registerEvents(new BasicPreventionListener(), this);
		getServer().getPluginManager().registerEvents(new BorderListener(), this);
		getServer().getPluginManager().registerEvents((combatLoggerListener = new CombatLoggerListener()), this);
		getServer().getPluginManager().registerEvents(new CrowbarListener(), this);
		getServer().getPluginManager().registerEvents(new EnchantmentLimiterListener(), this);
		getServer().getPluginManager().registerEvents(new EnderpearlCooldownHandler(), this);
		getServer().getPluginManager().registerEvents(new EndListener(), this);
		getServer().getPluginManager().registerEvents(new ElevatorListener(this), this);
		getServer().getPluginManager().registerEvents(new FoundDiamondsListener(), this);
		getServer().getPluginManager().registerEvents(new FoxListener(), this);
		getServer().getPluginManager().registerEvents(new GoldenAppleListener(), this);
		getServer().getPluginManager().registerEvents(new KOTHRewardKeyListener(), this);
		getServer().getPluginManager().registerEvents(new PvPTimerListener(), this);
		getServer().getPluginManager().registerEvents(new PotionLimiterListener(), this);
		getServer().getPluginManager().registerEvents(new KitListener(), this);
	    getServer().getPluginManager().registerEvents(new NetherPortalListener(), this);
		getServer().getPluginManager().registerEvents(new PortalTrapListener(), this);
		getServer().getPluginManager().registerEvents(new SignSubclaimListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnerTrackerListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnTagListener(), this);
		getServer().getPluginManager().registerEvents(new StaffUtilsListener(), this);
		getServer().getPluginManager().registerEvents(new TeamListener(), this);
		getServer().getPluginManager().registerEvents(new WebsiteListener(), this);
		getServer().getPluginManager().registerEvents(new TeamSubclaimCommand(), this);
		getServer().getPluginManager().registerEvents(new TeamClaimCommand(), this);
		getServer().getPluginManager().registerEvents(new StatTrakListener(), this);
		getServer().getPluginManager().registerEvents(new StrengthListener(), this);
		getServer().getPluginManager().registerEvents(new ChallengeListener(), this);
		//getServer().getPluginManager().registerEvents(new CreditListener(), this);
		getServer().getPluginManager().registerEvents(new PearlGlitchListener(this), this);


		if (getServerHandler().isReduceArmorDamage()) {
			getServer().getPluginManager().registerEvents(new ArmorDamageListener(), this);
		}

		if (getServerHandler().isBlockEntitiesThroughPortals()) {
			getServer().getPluginManager().registerEvents(new EntityPortalListener(), this);
		}

		if (getServerHandler().isBlockRemovalEnabled()) {
			getServer().getPluginManager().registerEvents(new BlockRegenListener(), this);
		}
		// yes
		if (Bukkit.getPluginManager().getPlugin("CheatBreakerAPI") != null && Bukkit.getPluginManager().getPlugin("CheatBreakerAPI").isEnabled()) {
			Bukkit.getScheduler().runTaskTimer(this, new RallyExpireTask(), 0, 20);
		}

		// Register kitmap specific listeners
		if (getServerHandler().isVeltKitMap() || getMapHandler().isKitMap()) {
			getServer().getPluginManager().registerEvents(new KitMapListener(), this);
			getServer().getPluginManager().registerEvents(new BountyListerner(), this);
			getServer().getPluginManager().registerEvents(new CarePackageHandler(), this);
		}
		getServer().getPluginManager().registerEvents(new BlockConvenienceListener(), this);
		getServer().getPluginManager().registerEvents(new TournamentListener(), this);
		//getServer().getPluginManager().registerEvents(new PackageListener(), this);
		getServer().getPluginManager().registerEvents(new RefillSignListener(), this);
		getServer().getPluginManager().registerEvents(new RefillSignCreateEvent(), this);
		getServer().getPluginManager().registerEvents(new KingEventListener(), this);

	}

	private void setupPersistence() {
		(playtimeMap = new PlaytimeMap()).loadFromRedis();
		(oppleMap = new OppleMap()).loadFromRedis();
		(creditsMap = new CreditsMap()).loadFromRedis();
		(deathbanMap = new DeathbanMap()).loadFromRedis();
		(PvPTimerMap = new PvPTimerMap()).loadFromRedis();
		(startingPvPTimerMap = new StartingPvPTimerMap()).loadFromRedis();
		(deathsMap = new DeathsMap()).loadFromRedis();
		(killsMap = new KillsMap()).loadFromRedis();
		(chatModeMap = new ChatModeMap()).loadFromRedis();
		(toggleGlobalChatMap = new ToggleGlobalChatMap()).loadFromRedis();
		(toggleLFFMessageMap = new ToggleLFFMessageMap()).loadFromRedis();
		(abilityCooldownsMap = new AbilityCooldownsMap()).loadFromRedis();
		(fDisplayMap = new FDisplayMap()).loadFromRedis();
		(cheatbreakerNotificationMap = new CheatbreakerNotificationMap()).loadFromRedis();
		(receiveFactionInviteMap = new ReceiveFactionInviteMap()).loadFromRedis();
		(teamColorMap = new TeamColorMap()).loadFromRedis();
		(enemyColorMap = new EnemyColorMap()).loadFromRedis();
		(allyColorMap = new AllyColorMap()).loadFromRedis();
		(archerTagColorMap = new ArcherTagColorMap()).loadFromRedis();
		(focusColorMap = new FocusColorMap()).loadFromRedis();
		(staffBoardMap = new StaffBoardMap()).loadFromRedis();
		(classCooldownsMap = new ClassCooldownsMap()).loadFromRedis();
		(fishingKitMap = new FishingKitMap()).loadFromRedis();
		(soulboundLivesMap = new SoulboundLivesMap()).loadFromRedis();
		(friendLivesMap = new FriendLivesMap()).loadFromRedis();
		(chatSpyMap = new ChatSpyMap()).loadFromRedis();
		(diamondMinedMap = new DiamondMinedMap()).loadFromRedis();
		(goldMinedMap = new GoldMinedMap()).loadFromRedis();
		(ironMinedMap = new IronMinedMap()).loadFromRedis();
		(coalMinedMap = new CoalMinedMap()).loadFromRedis();
		(redstoneMinedMap = new RedstoneMinedMap()).loadFromRedis();
		(lapisMinedMap = new LapisMinedMap()).loadFromRedis();
		(emeraldMinedMap = new EmeraldMinedMap()).loadFromRedis();
		(firstJoinMap = new FirstJoinMap()).loadFromRedis();
		(lastJoinMap = new LastJoinMap()).loadFromRedis();
		(wrappedBalanceMap = new WrappedBalanceMap()).loadFromRedis();
		(eloMap = new ELOMap()).loadFromRedis();
		(toggleFoundDiamondsMap = new ToggleFoundDiamondsMap()).loadFromRedis();
		(toggleDeathMessageMap = new ToggleDeathMessageMap()).loadFromRedis();
		(tabListModeMap = new TabListModeMap()).loadFromRedis();
		(cobblePickupMap = new CobblePickupMap()).loadFromRedis();
		(kdrMap = new KDRMap()).loadFromRedis();
	}

	public TournamentHandler getTournamentHandler(){
		return this.tournamentHandler;
	}

	public DiscordLogger getDiscordLogger(){
		return this.discordLogger;
	}

}
