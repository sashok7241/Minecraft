package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
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
import net.minecraft.src.ChatMessageComponent;
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
	private static MinecraftServer mcServer;
	private final ISaveFormat anvilConverterForAnvilFile;
	private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, func_130071_aq());
	private final File anvilFile;
	private final List tickables = new ArrayList();
	private final ICommandManager commandManager;
	public final Profiler theProfiler = new Profiler();
	private String hostname;
	private int serverPort = -1;
	public WorldServer[] worldServers;
	private ServerConfigurationManager serverConfigManager;
	private boolean serverRunning = true;
	private boolean serverStopped;
	private int tickCounter;
	protected Proxy field_110456_c;
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
	public final long[] sentPacketCountArray;
	public final long[] sentPacketSizeArray;
	public final long[] receivedPacketCountArray;
	public final long[] receivedPacketSizeArray;
	public final long[] tickTimeArray;
	public long[][] timeOfLastDimensionTick;
	private KeyPair serverKeyPair;
	private String serverOwner;
	private String folderName;
	private String worldName;
	private boolean isDemo;
	private boolean enableBonusChest;
	private boolean worldIsBeingDeleted;
	private String texturePack;
	private boolean serverIsRunning;
	private long timeOfLastWarning;
	private String userMessage;
	private boolean startProfiling;
	private boolean field_104057_T;
	
	public MinecraftServer(File par1File)
	{
		field_110456_c = Proxy.NO_PROXY;
		sentPacketCountArray = new long[100];
		sentPacketSizeArray = new long[100];
		receivedPacketCountArray = new long[100];
		receivedPacketSizeArray = new long[100];
		tickTimeArray = new long[100];
		texturePack = "";
		mcServer = this;
		anvilFile = par1File;
		commandManager = new ServerCommandManager();
		anvilConverterForAnvilFile = new AnvilSaveConverter(par1File);
		registerDispenseBehaviors();
	}
	
	public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport)
	{
		par1CrashReport.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableIsServerModded(this));
		if(worldServers != null && worldServers.length > 0 && worldServers[0] != null)
		{
			par1CrashReport.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new CallableServerProfiler(this));
		}
		if(serverConfigManager != null)
		{
			par1CrashReport.func_85056_g().addCrashSectionCallable("Player Count", new CallableServerMemoryStats(this));
		}
		return par1CrashReport;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
	{
		par1PlayerUsageSnooper.addData("whitelist_enabled", Boolean.valueOf(false));
		par1PlayerUsageSnooper.addData("whitelist_count", Integer.valueOf(0));
		par1PlayerUsageSnooper.addData("players_current", Integer.valueOf(getCurrentPlayerCount()));
		par1PlayerUsageSnooper.addData("players_max", Integer.valueOf(getMaxPlayers()));
		par1PlayerUsageSnooper.addData("players_seen", Integer.valueOf(serverConfigManager.getAvailablePlayerDat().length));
		par1PlayerUsageSnooper.addData("uses_auth", Boolean.valueOf(onlineMode));
		par1PlayerUsageSnooper.addData("gui_state", getGuiEnabled() ? "enabled" : "disabled");
		par1PlayerUsageSnooper.addData("run_time", Long.valueOf((func_130071_aq() - par1PlayerUsageSnooper.func_130105_g()) / 60L * 1000L));
		par1PlayerUsageSnooper.addData("avg_tick_ms", Integer.valueOf((int) (MathHelper.average(tickTimeArray) * 1.0E-6D)));
		par1PlayerUsageSnooper.addData("avg_sent_packet_count", Integer.valueOf((int) MathHelper.average(sentPacketCountArray)));
		par1PlayerUsageSnooper.addData("avg_sent_packet_size", Integer.valueOf((int) MathHelper.average(sentPacketSizeArray)));
		par1PlayerUsageSnooper.addData("avg_rec_packet_count", Integer.valueOf((int) MathHelper.average(receivedPacketCountArray)));
		par1PlayerUsageSnooper.addData("avg_rec_packet_size", Integer.valueOf((int) MathHelper.average(receivedPacketSizeArray)));
		int var2 = 0;
		for(WorldServer var4 : worldServers)
		{
			if(var4 != null)
			{
				WorldInfo var5 = var4.getWorldInfo();
				par1PlayerUsageSnooper.addData("world[" + var2 + "][dimension]", Integer.valueOf(var4.provider.dimensionId));
				par1PlayerUsageSnooper.addData("world[" + var2 + "][mode]", var5.getGameType());
				par1PlayerUsageSnooper.addData("world[" + var2 + "][difficulty]", Integer.valueOf(var4.difficultySetting));
				par1PlayerUsageSnooper.addData("world[" + var2 + "][hardcore]", Boolean.valueOf(var5.isHardcoreModeEnabled()));
				par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
				par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_version]", Integer.valueOf(var5.getTerrainType().getGeneratorVersion()));
				par1PlayerUsageSnooper.addData("world[" + var2 + "][height]", Integer.valueOf(buildLimit));
				par1PlayerUsageSnooper.addData("world[" + var2 + "][chunks_loaded]", Integer.valueOf(var4.getChunkProvider().getLoadedChunkCount()));
				++var2;
			}
		}
		par1PlayerUsageSnooper.addData("worlds", Integer.valueOf(var2));
	}
	
	@Override public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
	{
		par1PlayerUsageSnooper.addData("singleplayer", Boolean.valueOf(isSinglePlayer()));
		par1PlayerUsageSnooper.addData("server_brand", getServerModName());
		par1PlayerUsageSnooper.addData("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
		par1PlayerUsageSnooper.addData("dedicated", Boolean.valueOf(isDedicatedServer()));
	}
	
	protected boolean allowSpawnMonsters()
	{
		return true;
	}
	
	@Override public boolean canCommandSenderUseCommand(int par1, String par2Str)
	{
		return true;
	}
	
	public void canCreateBonusChest(boolean par1)
	{
		enableBonusChest = par1;
	}
	
	public abstract boolean canStructuresSpawn();
	
	protected void clearCurrentTask()
	{
		currentTask = null;
		percentDone = 0;
	}
	
	protected void convertMapIfNeeded(String par1Str)
	{
		if(getActiveAnvilConverter().isOldMapFormat(par1Str))
		{
			getLogAgent().logInfo("Converting map!");
			setUserMessage("menu.convertingLevel");
			getActiveAnvilConverter().convertMapFormat(par1Str, new ConvertingProgressUpdate(this));
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
	
	public String executeCommand(String par1Str)
	{
		RConConsoleSource.consoleBuffer.resetLog();
		commandManager.executeCommand(RConConsoleSource.consoleBuffer, par1Str);
		return RConConsoleSource.consoleBuffer.getChatBuffer();
	}
	
	protected void finalTick(CrashReport par1CrashReport)
	{
	}
	
	public void func_104055_i(boolean par1)
	{
		field_104057_T = par1;
	}
	
	public boolean func_104056_am()
	{
		return field_104057_T;
	}
	
	public Proxy func_110454_ao()
	{
		return field_110456_c;
	}
	
	public abstract int func_110455_j();
	
	@Override public World func_130014_f_()
	{
		return worldServers[0];
	}
	
	public boolean func_96290_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
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
	
	public File getFile(String par1Str)
	{
		return new File(getDataDirectory(), par1Str);
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
		return "1.6.2";
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
	
	public List getPossibleCompletions(ICommandSender par1ICommandSender, String par2Str)
	{
		ArrayList var3 = new ArrayList();
		if(par2Str.startsWith("/"))
		{
			par2Str = par2Str.substring(1);
			boolean var10 = !par2Str.contains(" ");
			List var11 = commandManager.getPossibleCommands(par1ICommandSender, par2Str);
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
			String[] var4 = par2Str.split(" ", -1);
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
		boolean var1 = true;
		boolean var2 = true;
		boolean var3 = true;
		boolean var4 = true;
		int var5 = 0;
		setUserMessage("menu.generatingTerrain");
		byte var6 = 0;
		getLogAgent().logInfo("Preparing start region for level " + var6);
		WorldServer var7 = worldServers[var6];
		ChunkCoordinates var8 = var7.getSpawnPoint();
		long var9 = func_130071_aq();
		for(int var11 = -192; var11 <= 192 && isServerRunning(); var11 += 16)
		{
			for(int var12 = -192; var12 <= 192 && isServerRunning(); var12 += 16)
			{
				long var13 = func_130071_aq();
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
	
	protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str)
	{
		convertMapIfNeeded(par1Str);
		setUserMessage("menu.loadingLevel");
		worldServers = new WorldServer[3];
		timeOfLastDimensionTick = new long[worldServers.length][100];
		ISaveHandler var7 = anvilConverterForAnvilFile.getSaveLoader(par1Str, true);
		WorldInfo var9 = var7.loadWorldInfo();
		WorldSettings var8;
		if(var9 == null)
		{
			var8 = new WorldSettings(par3, getGameType(), canStructuresSpawn(), isHardcore(), par5WorldType);
			var8.func_82750_a(par6Str);
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
					worldServers[var10] = new DemoWorldServer(this, var7, par2Str, var11, theProfiler, getLogAgent());
				} else
				{
					worldServers[var10] = new WorldServer(this, var7, par2Str, var11, var8, theProfiler, getLogAgent());
				}
			} else
			{
				worldServers[var10] = new WorldServerMulti(this, var7, par2Str, var11, var8, worldServers[0], theProfiler, getLogAgent());
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
	
	public void logDebug(String par1Str)
	{
		if(isDebuggingEnabled())
		{
			getLogAgent().logInfo(par1Str);
		}
	}
	
	public void logInfo(String par1Str)
	{
		getLogAgent().logInfo(par1Str);
	}
	
	public void logSevere(String par1Str)
	{
		getLogAgent().logSevere(par1Str);
	}
	
	public void logWarning(String par1Str)
	{
		getLogAgent().logWarning(par1Str);
	}
	
	protected void outputPercentRemaining(String par1Str, int par2)
	{
		currentTask = par1Str;
		percentDone = par2;
		getLogAgent().logInfo(par1Str + ": " + par2 + "%");
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
				long var1 = func_130071_aq();
				for(long var50 = 0L; serverRunning; serverIsRunning = true)
				{
					long var5 = func_130071_aq();
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
	
	protected void saveAllWorlds(boolean par1)
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
					if(!par1)
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
	
	@Override public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent)
	{
		getLogAgent().logInfo(par1ChatMessageComponent.toString());
	}
	
	public boolean serverIsInRunLoop()
	{
		return serverIsRunning;
	}
	
	public void setAllowFlight(boolean par1)
	{
		allowFlight = par1;
	}
	
	public void setAllowPvp(boolean par1)
	{
		pvpEnabled = par1;
	}
	
	public void setBuildLimit(int par1)
	{
		buildLimit = par1;
	}
	
	public void setCanSpawnAnimals(boolean par1)
	{
		canSpawnAnimals = par1;
	}
	
	public void setCanSpawnNPCs(boolean par1)
	{
		canSpawnNPCs = par1;
	}
	
	public void setConfigurationManager(ServerConfigurationManager par1ServerConfigurationManager)
	{
		serverConfigManager = par1ServerConfigurationManager;
	}
	
	public void setDemo(boolean par1)
	{
		isDemo = par1;
	}
	
	public void setDifficultyForAllWorlds(int par1)
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
					var3.difficultySetting = par1;
					var3.setAllowedSpawnTypes(var3.difficultySetting > 0, true);
				} else
				{
					var3.difficultySetting = par1;
					var3.setAllowedSpawnTypes(allowSpawnMonsters(), canSpawnAnimals);
				}
			}
		}
	}
	
	public void setFolderName(String par1Str)
	{
		folderName = par1Str;
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
		for(int var2 = 0; var2 < worldServers.length; ++var2)
		{
			getServer().worldServers[var2].getWorldInfo().setGameType(par1EnumGameType);
		}
	}
	
	public void setHostname(String par1Str)
	{
		hostname = par1Str;
	}
	
	public void setKeyPair(KeyPair par1KeyPair)
	{
		serverKeyPair = par1KeyPair;
	}
	
	public void setMOTD(String par1Str)
	{
		motd = par1Str;
	}
	
	public void setOnlineMode(boolean par1)
	{
		onlineMode = par1;
	}
	
	public void setServerOwner(String par1Str)
	{
		serverOwner = par1Str;
	}
	
	public void setServerPort(int par1)
	{
		serverPort = par1;
	}
	
	public void setTexturePack(String par1Str)
	{
		texturePack = par1Str;
	}
	
	protected synchronized void setUserMessage(String par1Str)
	{
		userMessage = par1Str;
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
					serverConfigManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(var4.getTotalWorldTime(), var4.getWorldTime(), var4.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), var4.provider.dimensionId);
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
	
	public WorldServer worldServerForDimension(int par1)
	{
		return par1 == -1 ? worldServers[1] : par1 == 1 ? worldServers[2] : worldServers[0];
	}
	
	public static long func_130071_aq()
	{
		return System.currentTimeMillis();
	}
	
	public static MinecraftServer getServer()
	{
		return mcServer;
	}
	
	public static ServerConfigurationManager getServerConfigurationManager(MinecraftServer par0MinecraftServer)
	{
		return par0MinecraftServer.serverConfigManager;
	}
}
