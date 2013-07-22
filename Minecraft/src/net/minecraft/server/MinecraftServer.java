package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.CallableIsServerModded;
import net.minecraft.src.CallableServerMemoryStats;
import net.minecraft.src.CallableServerProfiler;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ConvertingProgressUpdate;
import net.minecraft.src.CrashReport;
import net.minecraft.src.DemoWorldServer;
import net.minecraft.src.DispenserBehaviors;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.ICommandManager;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.ILogAgent;
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.RConConsoleSource;
import net.minecraft.src.ReportedException;
import net.minecraft.src.ServerCommandManager;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.StringUtils;
import net.minecraft.src.ThreadMinecraftServer;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldServer;
import net.minecraft.src.WorldServerMulti;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;

public abstract class MinecraftServer implements ICommandSender, Runnable, IPlayerUsage
{
	private static MinecraftServer mcServer = null;
	private final ISaveFormat anvilConverterForAnvilFile;
	private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this);
	private final File anvilFile;
	private final List tickables = new ArrayList();
	private final ICommandManager commandManager;
	public final Profiler theProfiler = new Profiler();
	private String hostname;
	private int serverPort = -1;
	public WorldServer[] worldServers;
	private ServerConfigurationManager serverConfigManager;
	private boolean serverRunning = true;
	private boolean serverStopped = false;
	private int tickCounter = 0;
	public String currentTask;
	public int percentDone;
	private boolean onlineMode;
	private boolean canSpawnAnimals;
	private boolean canSpawnNPCs;
	private boolean pvpEnabled;
	private boolean allowFlight;
	private String motd;
	private int buildLimit;
	private long lastSentPacketID;
	private long lastSentPacketSize;
	private long lastReceivedID;
	private long lastReceivedSize;
	public final long[] sentPacketCountArray = new long[100];
	public final long[] sentPacketSizeArray = new long[100];
	public final long[] receivedPacketCountArray = new long[100];
	public final long[] receivedPacketSizeArray = new long[100];
	public final long[] tickTimeArray = new long[100];
	public long[][] timeOfLastDimensionTick;
	private KeyPair serverKeyPair;
	private String serverOwner;
	private String folderName;
	private String worldName;
	private boolean isDemo;
	private boolean enableBonusChest;
	private boolean worldIsBeingDeleted;
	private String texturePack = "";
	private boolean serverIsRunning = false;
	private long timeOfLastWarning;
	private String userMessage;
	private boolean startProfiling;
	private boolean field_104057_T = false;
	
	public MinecraftServer(File p_i3375_1_)
	{
		mcServer = this;
		anvilFile = p_i3375_1_;
		commandManager = new ServerCommandManager();
		anvilConverterForAnvilFile = new AnvilSaveConverter(p_i3375_1_);
		registerDispenseBehaviors();
	}
	
	public CrashReport addServerInfoToCrashReport(CrashReport p_71230_1_)
	{
		p_71230_1_.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableIsServerModded(this));
		if(worldServers != null && worldServers.length > 0 && worldServers[0] != null)
		{
			p_71230_1_.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new CallableServerProfiler(this));
		}
		if(serverConfigManager != null)
		{
			p_71230_1_.func_85056_g().addCrashSectionCallable("Player Count", new CallableServerMemoryStats(this));
		}
		return p_71230_1_;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper p_70000_1_)
	{
		p_70000_1_.addData("whitelist_enabled", Boolean.valueOf(false));
		p_70000_1_.addData("whitelist_count", Integer.valueOf(0));
		p_70000_1_.addData("players_current", Integer.valueOf(getCurrentPlayerCount()));
		p_70000_1_.addData("players_max", Integer.valueOf(getMaxPlayers()));
		p_70000_1_.addData("players_seen", Integer.valueOf(serverConfigManager.getAvailablePlayerDat().length));
		p_70000_1_.addData("uses_auth", Boolean.valueOf(onlineMode));
		p_70000_1_.addData("gui_state", getGuiEnabled() ? "enabled" : "disabled");
		p_70000_1_.addData("avg_tick_ms", Integer.valueOf((int) (MathHelper.average(tickTimeArray) * 1.0E-6D)));
		p_70000_1_.addData("avg_sent_packet_count", Integer.valueOf((int) MathHelper.average(sentPacketCountArray)));
		p_70000_1_.addData("avg_sent_packet_size", Integer.valueOf((int) MathHelper.average(sentPacketSizeArray)));
		p_70000_1_.addData("avg_rec_packet_count", Integer.valueOf((int) MathHelper.average(receivedPacketCountArray)));
		p_70000_1_.addData("avg_rec_packet_size", Integer.valueOf((int) MathHelper.average(receivedPacketSizeArray)));
		int var2 = 0;
		for(WorldServer var4 : worldServers)
		{
			if(var4 != null)
			{
				WorldInfo var5 = var4.getWorldInfo();
				p_70000_1_.addData("world[" + var2 + "][dimension]", Integer.valueOf(var4.provider.dimensionId));
				p_70000_1_.addData("world[" + var2 + "][mode]", var5.getGameType());
				p_70000_1_.addData("world[" + var2 + "][difficulty]", Integer.valueOf(var4.difficultySetting));
				p_70000_1_.addData("world[" + var2 + "][hardcore]", Boolean.valueOf(var5.isHardcoreModeEnabled()));
				p_70000_1_.addData("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
				p_70000_1_.addData("world[" + var2 + "][generator_version]", Integer.valueOf(var5.getTerrainType().getGeneratorVersion()));
				p_70000_1_.addData("world[" + var2 + "][height]", Integer.valueOf(buildLimit));
				p_70000_1_.addData("world[" + var2 + "][chunks_loaded]", Integer.valueOf(var4.getChunkProvider().getLoadedChunkCount()));
				++var2;
			}
		}
		p_70000_1_.addData("worlds", Integer.valueOf(var2));
	}
	
	@Override public void addServerTypeToSnooper(PlayerUsageSnooper p_70001_1_)
	{
		p_70001_1_.addData("singleplayer", Boolean.valueOf(isSinglePlayer()));
		p_70001_1_.addData("server_brand", getServerModName());
		p_70001_1_.addData("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
		p_70001_1_.addData("dedicated", Boolean.valueOf(isDedicatedServer()));
	}
	
	protected boolean allowSpawnMonsters()
	{
		return true;
	}
	
	@Override public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
	{
		return true;
	}
	
	public void canCreateBonusChest(boolean p_71194_1_)
	{
		enableBonusChest = p_71194_1_;
	}
	
	public abstract boolean canStructuresSpawn();
	
	protected void clearCurrentTask()
	{
		currentTask = null;
		percentDone = 0;
	}
	
	protected void convertMapIfNeeded(String p_71237_1_)
	{
		if(getActiveAnvilConverter().isOldMapFormat(p_71237_1_))
		{
			getLogAgent().logInfo("Converting map!");
			setUserMessage("menu.convertingLevel");
			getActiveAnvilConverter().convertMapFormat(p_71237_1_, new ConvertingProgressUpdate(this));
		}
	}
	
	public void deleteWorldAndStopServer()
	{
		worldIsBeingDeleted = true;
		getActiveAnvilConverter().flushCache();
		for(WorldServer var2 : worldServers)
		{
			if(var2 != null)
			{
				var2.flush();
			}
		}
		getActiveAnvilConverter().deleteWorldDirectory(worldServers[0].getSaveHandler().getWorldDirectoryName());
		initiateShutdown();
	}
	
	public void enableProfiling()
	{
		startProfiling = true;
	}
	
	public String executeCommand(String p_71252_1_)
	{
		RConConsoleSource.consoleBuffer.resetLog();
		commandManager.executeCommand(RConConsoleSource.consoleBuffer, p_71252_1_);
		return RConConsoleSource.consoleBuffer.getChatBuffer();
	}
	
	protected void finalTick(CrashReport p_71228_1_)
	{
	}
	
	public void func_104055_i(boolean p_104055_1_)
	{
		field_104057_T = p_104055_1_;
	}
	
	public boolean func_104056_am()
	{
		return field_104057_T;
	}
	
	public boolean func_96290_a(World p_96290_1_, int p_96290_2_, int p_96290_3_, int p_96290_4_, EntityPlayer p_96290_5_)
	{
		return false;
	}
	
	public ISaveFormat getActiveAnvilConverter()
	{
		return anvilConverterForAnvilFile;
	}
	
	public boolean getAllowNether()
	{
		return true;
	}
	
	public String[] getAllUsernames()
	{
		return serverConfigManager.getAllUsernames();
	}
	
	public int getBuildLimit()
	{
		return buildLimit;
	}
	
	public boolean getCanSpawnAnimals()
	{
		return canSpawnAnimals;
	}
	
	public boolean getCanSpawnNPCs()
	{
		return canSpawnNPCs;
	}
	
	public ICommandManager getCommandManager()
	{
		return commandManager;
	}
	
	@Override public String getCommandSenderName()
	{
		return "Server";
	}
	
	public ServerConfigurationManager getConfigurationManager()
	{
		return serverConfigManager;
	}
	
	public int getCurrentPlayerCount()
	{
		return serverConfigManager.getCurrentPlayerCount();
	}
	
	protected File getDataDirectory()
	{
		return new File(".");
	}
	
	public abstract int getDifficulty();
	
	public File getFile(String p_71209_1_)
	{
		return new File(getDataDirectory(), p_71209_1_);
	}
	
	public String getFolderName()
	{
		return folderName;
	}
	
	public abstract EnumGameType getGameType();
	
	public boolean getGuiEnabled()
	{
		return false;
	}
	
	public String getHostname()
	{
		return hostname;
	}
	
	public KeyPair getKeyPair()
	{
		return serverKeyPair;
	}
	
	@Override public abstract ILogAgent getLogAgent();
	
	public int getMaxPlayers()
	{
		return serverConfigManager.getMaxPlayers();
	}
	
	public String getMinecraftVersion()
	{
		return "1.5.2";
	}
	
	public String getMOTD()
	{
		return motd;
	}
	
	public abstract NetworkListenThread getNetworkThread();
	
	@Override public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(0, 0, 0);
	}
	
	public PlayerUsageSnooper getPlayerUsageSnooper()
	{
		return usageSnooper;
	}
	
	public String getPlugins()
	{
		return "";
	}
	
	public int getPort()
	{
		return serverPort;
	}
	
	public List getPossibleCompletions(ICommandSender p_71248_1_, String p_71248_2_)
	{
		ArrayList var3 = new ArrayList();
		if(p_71248_2_.startsWith("/"))
		{
			p_71248_2_ = p_71248_2_.substring(1);
			boolean var10 = !p_71248_2_.contains(" ");
			List var11 = commandManager.getPossibleCommands(p_71248_1_, p_71248_2_);
			if(var11 != null)
			{
				Iterator var12 = var11.iterator();
				while(var12.hasNext())
				{
					String var13 = (String) var12.next();
					if(var10)
					{
						var3.add("/" + var13);
					} else
					{
						var3.add(var13);
					}
				}
			}
			return var3;
		} else
		{
			String[] var4 = p_71248_2_.split(" ", -1);
			String var5 = var4[var4.length - 1];
			String[] var6 = serverConfigManager.getAllUsernames();
			int var7 = var6.length;
			for(int var8 = 0; var8 < var7; ++var8)
			{
				String var9 = var6[var8];
				if(CommandBase.doesStringStartWith(var5, var9))
				{
					var3.add(var9);
				}
			}
			return var3;
		}
	}
	
	public String getServerHostname()
	{
		return hostname;
	}
	
	public String getServerModName()
	{
		return "vanilla";
	}
	
	public String getServerMOTD()
	{
		return motd;
	}
	
	public String getServerOwner()
	{
		return serverOwner;
	}
	
	public int getServerPort()
	{
		return serverPort;
	}
	
	public int getSpawnProtectionSize()
	{
		return 16;
	}
	
	public String getTexturePack()
	{
		return texturePack;
	}
	
	public int getTickCounter()
	{
		return tickCounter;
	}
	
	public synchronized String getUserMessage()
	{
		return userMessage;
	}
	
	public String getWorldName()
	{
		return worldName;
	}
	
	protected void initialWorldChunkLoad()
	{
		int var5 = 0;
		setUserMessage("menu.generatingTerrain");
		byte var6 = 0;
		getLogAgent().logInfo("Preparing start region for level " + var6);
		WorldServer var7 = worldServers[var6];
		ChunkCoordinates var8 = var7.getSpawnPoint();
		long var9 = System.currentTimeMillis();
		for(int var11 = -192; var11 <= 192 && isServerRunning(); var11 += 16)
		{
			for(int var12 = -192; var12 <= 192 && isServerRunning(); var12 += 16)
			{
				long var13 = System.currentTimeMillis();
				if(var13 - var9 > 1000L)
				{
					outputPercentRemaining("Preparing spawn area", var5 * 100 / 625);
					var9 = var13;
				}
				++var5;
				var7.theChunkProviderServer.loadChunk(var8.posX + var11 >> 4, var8.posZ + var12 >> 4);
			}
		}
		clearCurrentTask();
	}
	
	public void initiateShutdown()
	{
		serverRunning = false;
	}
	
	public abstract boolean isCommandBlockEnabled();
	
	public boolean isDebuggingEnabled()
	{
		return false;
	}
	
	public abstract boolean isDedicatedServer();
	
	public boolean isDemo()
	{
		return isDemo;
	}
	
	public boolean isFlightAllowed()
	{
		return allowFlight;
	}
	
	public abstract boolean isHardcore();
	
	public boolean isPVPEnabled()
	{
		return pvpEnabled;
	}
	
	public boolean isServerInOnlineMode()
	{
		return onlineMode;
	}
	
	public boolean isServerRunning()
	{
		return serverRunning;
	}
	
	public boolean isServerStopped()
	{
		return serverStopped;
	}
	
	public boolean isSinglePlayer()
	{
		return serverOwner != null;
	}
	
	@Override public boolean isSnooperEnabled()
	{
		return true;
	}
	
	protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long p_71247_3_, WorldType p_71247_5_, String p_71247_6_)
	{
		convertMapIfNeeded(p_71247_1_);
		setUserMessage("menu.loadingLevel");
		worldServers = new WorldServer[3];
		timeOfLastDimensionTick = new long[worldServers.length][100];
		ISaveHandler var7 = anvilConverterForAnvilFile.getSaveLoader(p_71247_1_, true);
		WorldInfo var9 = var7.loadWorldInfo();
		WorldSettings var8;
		if(var9 == null)
		{
			var8 = new WorldSettings(p_71247_3_, getGameType(), canStructuresSpawn(), isHardcore(), p_71247_5_);
			var8.func_82750_a(p_71247_6_);
		} else
		{
			var8 = new WorldSettings(var9);
		}
		if(enableBonusChest)
		{
			var8.enableBonusChest();
		}
		for(int var10 = 0; var10 < worldServers.length; ++var10)
		{
			byte var11 = 0;
			if(var10 == 1)
			{
				var11 = -1;
			}
			if(var10 == 2)
			{
				var11 = 1;
			}
			if(var10 == 0)
			{
				if(isDemo())
				{
					worldServers[var10] = new DemoWorldServer(this, var7, p_71247_2_, var11, theProfiler, getLogAgent());
				} else
				{
					worldServers[var10] = new WorldServer(this, var7, p_71247_2_, var11, var8, theProfiler, getLogAgent());
				}
			} else
			{
				worldServers[var10] = new WorldServerMulti(this, var7, p_71247_2_, var11, var8, worldServers[0], theProfiler, getLogAgent());
			}
			worldServers[var10].addWorldAccess(new WorldManager(this, worldServers[var10]));
			if(!isSinglePlayer())
			{
				worldServers[var10].getWorldInfo().setGameType(getGameType());
			}
			serverConfigManager.setPlayerManager(worldServers);
		}
		setDifficultyForAllWorlds(getDifficulty());
		initialWorldChunkLoad();
	}
	
	public void logDebug(String p_71198_1_)
	{
		if(isDebuggingEnabled())
		{
			getLogAgent().logInfo(p_71198_1_);
		}
	}
	
	public void logInfo(String p_71244_1_)
	{
		getLogAgent().logInfo(p_71244_1_);
	}
	
	public void logSevere(String p_71201_1_)
	{
		getLogAgent().logSevere(p_71201_1_);
	}
	
	public void logWarning(String p_71236_1_)
	{
		getLogAgent().logWarning(p_71236_1_);
	}
	
	protected void outputPercentRemaining(String p_71216_1_, int p_71216_2_)
	{
		currentTask = p_71216_1_;
		percentDone = p_71216_2_;
		getLogAgent().logInfo(p_71216_1_ + ": " + p_71216_2_ + "%");
	}
	
	private void registerDispenseBehaviors()
	{
		DispenserBehaviors.func_96467_a();
	}
	
	@Override public void run()
	{
		try
		{
			if(startServer())
			{
				long var1 = System.currentTimeMillis();
				for(long var50 = 0L; serverRunning; serverIsRunning = true)
				{
					long var5 = System.currentTimeMillis();
					long var7 = var5 - var1;
					if(var7 > 2000L && var1 - timeOfLastWarning >= 15000L)
					{
						getLogAgent().logWarning("Can\'t keep up! Did the system time change, or is the server overloaded?");
						var7 = 2000L;
						timeOfLastWarning = var1;
					}
					if(var7 < 0L)
					{
						getLogAgent().logWarning("Time ran backwards! Did the system time change?");
						var7 = 0L;
					}
					var50 += var7;
					var1 = var5;
					if(worldServers[0].areAllPlayersAsleep())
					{
						tick();
						var50 = 0L;
					} else
					{
						while(var50 > 50L)
						{
							var50 -= 50L;
							tick();
						}
					}
					Thread.sleep(1L);
				}
			} else
			{
				finalTick((CrashReport) null);
			}
		} catch(Throwable var48)
		{
			var48.printStackTrace();
			getLogAgent().logSevereException("Encountered an unexpected exception " + var48.getClass().getSimpleName(), var48);
			CrashReport var2 = null;
			if(var48 instanceof ReportedException)
			{
				var2 = addServerInfoToCrashReport(((ReportedException) var48).getCrashReport());
			} else
			{
				var2 = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var48));
			}
			File var3 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
			if(var2.saveToFile(var3, getLogAgent()))
			{
				getLogAgent().logSevere("This crash report has been saved to: " + var3.getAbsolutePath());
			} else
			{
				getLogAgent().logSevere("We were unable to save this crash report to disk.");
			}
			finalTick(var2);
		} finally
		{
			try
			{
				stopServer();
				serverStopped = true;
			} catch(Throwable var46)
			{
				var46.printStackTrace();
			} finally
			{
				systemExitNow();
			}
		}
	}
	
	protected void saveAllWorlds(boolean p_71267_1_)
	{
		if(!worldIsBeingDeleted)
		{
			WorldServer[] var2 = worldServers;
			int var3 = var2.length;
			for(int var4 = 0; var4 < var3; ++var4)
			{
				WorldServer var5 = var2[var4];
				if(var5 != null)
				{
					if(!p_71267_1_)
					{
						getLogAgent().logInfo("Saving chunks for level \'" + var5.getWorldInfo().getWorldName() + "\'/" + var5.provider.getDimensionName());
					}
					try
					{
						var5.saveAllChunks(true, (IProgressUpdate) null);
					} catch(MinecraftException var7)
					{
						getLogAgent().logWarning(var7.getMessage());
					}
				}
			}
		}
	}
	
	@Override public void sendChatToPlayer(String p_70006_1_)
	{
		getLogAgent().logInfo(StringUtils.stripControlCodes(p_70006_1_));
	}
	
	public boolean serverIsInRunLoop()
	{
		return serverIsRunning;
	}
	
	public void setAllowFlight(boolean p_71245_1_)
	{
		allowFlight = p_71245_1_;
	}
	
	public void setAllowPvp(boolean p_71188_1_)
	{
		pvpEnabled = p_71188_1_;
	}
	
	public void setBuildLimit(int p_71191_1_)
	{
		buildLimit = p_71191_1_;
	}
	
	public void setCanSpawnAnimals(boolean p_71251_1_)
	{
		canSpawnAnimals = p_71251_1_;
	}
	
	public void setCanSpawnNPCs(boolean p_71257_1_)
	{
		canSpawnNPCs = p_71257_1_;
	}
	
	public void setConfigurationManager(ServerConfigurationManager p_71210_1_)
	{
		serverConfigManager = p_71210_1_;
	}
	
	public void setDemo(boolean p_71204_1_)
	{
		isDemo = p_71204_1_;
	}
	
	public void setDifficultyForAllWorlds(int p_71226_1_)
	{
		for(WorldServer worldServer : worldServers)
		{
			WorldServer var3 = worldServer;
			if(var3 != null)
			{
				if(var3.getWorldInfo().isHardcoreModeEnabled())
				{
					var3.difficultySetting = 3;
					var3.setAllowedSpawnTypes(true, true);
				} else if(isSinglePlayer())
				{
					var3.difficultySetting = p_71226_1_;
					var3.setAllowedSpawnTypes(var3.difficultySetting > 0, true);
				} else
				{
					var3.difficultySetting = p_71226_1_;
					var3.setAllowedSpawnTypes(allowSpawnMonsters(), canSpawnAnimals);
				}
			}
		}
	}
	
	public void setFolderName(String p_71261_1_)
	{
		folderName = p_71261_1_;
	}
	
	public void setGameType(EnumGameType p_71235_1_)
	{
		for(int var2 = 0; var2 < worldServers.length; ++var2)
		{
			getServer().worldServers[var2].getWorldInfo().setGameType(p_71235_1_);
		}
	}
	
	public void setHostname(String p_71189_1_)
	{
		hostname = p_71189_1_;
	}
	
	public void setKeyPair(KeyPair p_71253_1_)
	{
		serverKeyPair = p_71253_1_;
	}
	
	public void setMOTD(String p_71205_1_)
	{
		motd = p_71205_1_;
	}
	
	public void setOnlineMode(boolean p_71229_1_)
	{
		onlineMode = p_71229_1_;
	}
	
	public void setServerOwner(String p_71224_1_)
	{
		serverOwner = p_71224_1_;
	}
	
	public void setServerPort(int p_71208_1_)
	{
		serverPort = p_71208_1_;
	}
	
	public void setTexturePack(String p_71269_1_)
	{
		texturePack = p_71269_1_;
	}
	
	protected synchronized void setUserMessage(String p_71192_1_)
	{
		userMessage = p_71192_1_;
	}
	
	public void setWorldName(String par1Str)
	{
		worldName = par1Str;
	}
	
	public abstract String shareToLAN(EnumGameType var1, boolean var2);
	
	protected abstract boolean startServer() throws IOException;
	
	public void startServerThread()
	{
		new ThreadMinecraftServer(this, "Server thread").start();
	}
	
	public void stopServer()
	{
		if(!worldIsBeingDeleted)
		{
			getLogAgent().logInfo("Stopping server");
			if(getNetworkThread() != null)
			{
				getNetworkThread().stopListening();
			}
			if(serverConfigManager != null)
			{
				getLogAgent().logInfo("Saving players");
				serverConfigManager.saveAllPlayerData();
				serverConfigManager.removeAllPlayers();
			}
			getLogAgent().logInfo("Saving worlds");
			saveAllWorlds(false);
			for(WorldServer var2 : worldServers)
			{
				var2.flush();
			}
			if(usageSnooper != null && usageSnooper.isSnooperRunning())
			{
				usageSnooper.stopSnooper();
			}
		}
	}
	
	protected void systemExitNow()
	{
	}
	
	public int textureSize()
	{
		return 16;
	}
	
	public void tick()
	{
		long var1 = System.nanoTime();
		AxisAlignedBB.getAABBPool().cleanPool();
		++tickCounter;
		if(startProfiling)
		{
			startProfiling = false;
			theProfiler.profilingEnabled = true;
			theProfiler.clearProfiling();
		}
		theProfiler.startSection("root");
		updateTimeLightAndEntities();
		if(tickCounter % 900 == 0)
		{
			theProfiler.startSection("save");
			serverConfigManager.saveAllPlayerData();
			saveAllWorlds(true);
			theProfiler.endSection();
		}
		theProfiler.startSection("tallying");
		tickTimeArray[tickCounter % 100] = System.nanoTime() - var1;
		sentPacketCountArray[tickCounter % 100] = Packet.sentID - lastSentPacketID;
		lastSentPacketID = Packet.sentID;
		sentPacketSizeArray[tickCounter % 100] = Packet.sentSize - lastSentPacketSize;
		lastSentPacketSize = Packet.sentSize;
		receivedPacketCountArray[tickCounter % 100] = Packet.receivedID - lastReceivedID;
		lastReceivedID = Packet.receivedID;
		receivedPacketSizeArray[tickCounter % 100] = Packet.receivedSize - lastReceivedSize;
		lastReceivedSize = Packet.receivedSize;
		theProfiler.endSection();
		theProfiler.startSection("snooper");
		if(!usageSnooper.isSnooperRunning() && tickCounter > 100)
		{
			usageSnooper.startSnooper();
		}
		if(tickCounter % 6000 == 0)
		{
			usageSnooper.addMemoryStatsToSnooper();
		}
		theProfiler.endSection();
		theProfiler.endSection();
	}
	
	@Override public String translateString(String p_70004_1_, Object ... p_70004_2_)
	{
		return StringTranslate.getInstance().translateKeyFormat(p_70004_1_, p_70004_2_);
	}
	
	public void updateTimeLightAndEntities()
	{
		theProfiler.startSection("levels");
		int var1;
		for(var1 = 0; var1 < worldServers.length; ++var1)
		{
			long var2 = System.nanoTime();
			if(var1 == 0 || getAllowNether())
			{
				WorldServer var4 = worldServers[var1];
				theProfiler.startSection(var4.getWorldInfo().getWorldName());
				theProfiler.startSection("pools");
				var4.getWorldVec3Pool().clear();
				theProfiler.endSection();
				if(tickCounter % 20 == 0)
				{
					theProfiler.startSection("timeSync");
					serverConfigManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(var4.getTotalWorldTime(), var4.getWorldTime()), var4.provider.dimensionId);
					theProfiler.endSection();
				}
				theProfiler.startSection("tick");
				CrashReport var6;
				try
				{
					var4.tick();
				} catch(Throwable var8)
				{
					var6 = CrashReport.makeCrashReport(var8, "Exception ticking world");
					var4.addWorldInfoToCrashReport(var6);
					throw new ReportedException(var6);
				}
				try
				{
					var4.updateEntities();
				} catch(Throwable var7)
				{
					var6 = CrashReport.makeCrashReport(var7, "Exception ticking world entities");
					var4.addWorldInfoToCrashReport(var6);
					throw new ReportedException(var6);
				}
				theProfiler.endSection();
				theProfiler.startSection("tracker");
				var4.getEntityTracker().updateTrackedEntities();
				theProfiler.endSection();
				theProfiler.endSection();
			}
			timeOfLastDimensionTick[var1][tickCounter % 100] = System.nanoTime() - var2;
		}
		theProfiler.endStartSection("connection");
		getNetworkThread().networkTick();
		theProfiler.endStartSection("players");
		serverConfigManager.sendPlayerInfoToAllPlayers();
		theProfiler.endStartSection("tickables");
		for(var1 = 0; var1 < tickables.size(); ++var1)
		{
			((IUpdatePlayerListBox) tickables.get(var1)).update();
		}
		theProfiler.endSection();
	}
	
	public WorldServer worldServerForDimension(int p_71218_1_)
	{
		return p_71218_1_ == -1 ? worldServers[1] : p_71218_1_ == 1 ? worldServers[2] : worldServers[0];
	}
	
	public static MinecraftServer getServer()
	{
		return mcServer;
	}
	
	public static ServerConfigurationManager getServerConfigurationManager(MinecraftServer p_71196_0_)
	{
		return p_71196_0_.serverConfigManager;
	}
}
