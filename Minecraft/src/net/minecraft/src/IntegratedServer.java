package net.minecraft.src;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class IntegratedServer extends MinecraftServer
{
	private final Minecraft mc;
	private final WorldSettings theWorldSettings;
	private final ILogAgent serverLogAgent = new LogAgent("Minecraft-Server", " [SERVER]", new File(Minecraft.getMinecraftDir(), "output-server.log").getAbsolutePath());
	private IntegratedServerListenThread theServerListeningThread;
	private boolean isGamePaused = false;
	private boolean isPublic;
	private ThreadLanServerPing lanServerPing;
	
	public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings)
	{
		super(new File(Minecraft.getMinecraftDir(), "saves"));
		setServerOwner(par1Minecraft.session.username);
		setFolderName(par2Str);
		setWorldName(par3Str);
		setDemo(par1Minecraft.isDemo());
		canCreateBonusChest(par4WorldSettings.isBonusChestEnabled());
		setBuildLimit(256);
		setConfigurationManager(new IntegratedPlayerList(this));
		mc = par1Minecraft;
		theWorldSettings = par4WorldSettings;
		try
		{
			theServerListeningThread = new IntegratedServerListenThread(this);
		} catch(IOException var6)
		{
			throw new Error();
		}
	}
	
	@Override public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport)
	{
		par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
		par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableType3(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableIsModded(this));
		return par1CrashReport;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
	{
		super.addServerStatsToSnooper(par1PlayerUsageSnooper);
		par1PlayerUsageSnooper.addData("snooper_partner", mc.getPlayerUsageSnooper().getUniqueID());
	}
	
	@Override public boolean canStructuresSpawn()
	{
		return false;
	}
	
	@Override protected void finalTick(CrashReport par1CrashReport)
	{
		mc.crashed(par1CrashReport);
	}
	
	@Override protected File getDataDirectory()
	{
		return mc.mcDataDir;
	}
	
	@Override public int getDifficulty()
	{
		return mc.gameSettings.difficulty;
	}
	
	@Override public EnumGameType getGameType()
	{
		return theWorldSettings.getGameType();
	}
	
	@Override public ILogAgent getLogAgent()
	{
		return serverLogAgent;
	}
	
	@Override public NetworkListenThread getNetworkThread()
	{
		return getServerListeningThread();
	}
	
	public boolean getPublic()
	{
		return isPublic;
	}
	
	public IntegratedServerListenThread getServerListeningThread()
	{
		return theServerListeningThread;
	}
	
	@Override public void initiateShutdown()
	{
		super.initiateShutdown();
		if(lanServerPing != null)
		{
			lanServerPing.interrupt();
			lanServerPing = null;
		}
	}
	
	@Override public boolean isCommandBlockEnabled()
	{
		return true;
	}
	
	@Override public boolean isDedicatedServer()
	{
		return false;
	}
	
	@Override public boolean isHardcore()
	{
		return theWorldSettings.getHardcoreEnabled();
	}
	
	@Override public boolean isSnooperEnabled()
	{
		return Minecraft.getMinecraft().isSnooperEnabled();
	}
	
	@Override protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str)
	{
		convertMapIfNeeded(par1Str);
		worldServers = new WorldServer[3];
		timeOfLastDimensionTick = new long[worldServers.length][100];
		ISaveHandler var7 = getActiveAnvilConverter().getSaveLoader(par1Str, true);
		for(int var8 = 0; var8 < worldServers.length; ++var8)
		{
			byte var9 = 0;
			if(var8 == 1)
			{
				var9 = -1;
			}
			if(var8 == 2)
			{
				var9 = 1;
			}
			if(var8 == 0)
			{
				if(isDemo())
				{
					worldServers[var8] = new DemoWorldServer(this, var7, par2Str, var9, theProfiler, getLogAgent());
				} else
				{
					worldServers[var8] = new WorldServer(this, var7, par2Str, var9, theWorldSettings, theProfiler, getLogAgent());
				}
			} else
			{
				worldServers[var8] = new WorldServerMulti(this, var7, par2Str, var9, theWorldSettings, worldServers[0], theProfiler, getLogAgent());
			}
			worldServers[var8].addWorldAccess(new WorldManager(this, worldServers[var8]));
			getConfigurationManager().setPlayerManager(worldServers);
		}
		setDifficultyForAllWorlds(getDifficulty());
		initialWorldChunkLoad();
	}
	
	@Override public void setGameType(EnumGameType par1EnumGameType)
	{
		getConfigurationManager().setGameType(par1EnumGameType);
	}
	
	@Override public String shareToLAN(EnumGameType par1EnumGameType, boolean par2)
	{
		try
		{
			String var3 = theServerListeningThread.func_71755_c();
			getLogAgent().logInfo("Started on " + var3);
			isPublic = true;
			lanServerPing = new ThreadLanServerPing(getMOTD(), var3);
			lanServerPing.start();
			getConfigurationManager().setGameType(par1EnumGameType);
			getConfigurationManager().setCommandsAllowedForAll(par2);
			return var3;
		} catch(IOException var4)
		{
			return null;
		}
	}
	
	@Override protected boolean startServer() throws IOException
	{
		serverLogAgent.logInfo("Starting integrated minecraft server version 1.5.2");
		setOnlineMode(false);
		setCanSpawnAnimals(true);
		setCanSpawnNPCs(true);
		setAllowPvp(true);
		setAllowFlight(true);
		serverLogAgent.logInfo("Generating keypair");
		setKeyPair(CryptManager.createNewKeyPair());
		loadAllWorlds(getFolderName(), getWorldName(), theWorldSettings.getSeed(), theWorldSettings.getTerrainType(), theWorldSettings.func_82749_j());
		setMOTD(getServerOwner() + " - " + worldServers[0].getWorldInfo().getWorldName());
		return true;
	}
	
	@Override public void stopServer()
	{
		super.stopServer();
		if(lanServerPing != null)
		{
			lanServerPing.interrupt();
			lanServerPing = null;
		}
	}
	
	@Override public void tick()
	{
		boolean var1 = isGamePaused;
		isGamePaused = theServerListeningThread.isGamePaused();
		if(!var1 && isGamePaused)
		{
			serverLogAgent.logInfo("Saving and pausing game...");
			getConfigurationManager().saveAllPlayerData();
			saveAllWorlds(false);
		}
		if(!isGamePaused)
		{
			super.tick();
		}
	}
}
