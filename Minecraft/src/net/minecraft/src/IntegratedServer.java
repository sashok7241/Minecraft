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
	
	public IntegratedServer(Minecraft p_i3118_1_, String p_i3118_2_, String p_i3118_3_, WorldSettings p_i3118_4_)
	{
		super(new File(Minecraft.getMinecraftDir(), "saves"));
		setServerOwner(p_i3118_1_.session.username);
		setFolderName(p_i3118_2_);
		setWorldName(p_i3118_3_);
		setDemo(p_i3118_1_.isDemo());
		canCreateBonusChest(p_i3118_4_.isBonusChestEnabled());
		setBuildLimit(256);
		setConfigurationManager(new IntegratedPlayerList(this));
		mc = p_i3118_1_;
		theWorldSettings = p_i3118_4_;
		try
		{
			theServerListeningThread = new IntegratedServerListenThread(this);
		} catch(IOException var6)
		{
			throw new Error();
		}
	}
	
	@Override public CrashReport addServerInfoToCrashReport(CrashReport p_71230_1_)
	{
		p_71230_1_ = super.addServerInfoToCrashReport(p_71230_1_);
		p_71230_1_.func_85056_g().addCrashSectionCallable("Type", new CallableType3(this));
		p_71230_1_.func_85056_g().addCrashSectionCallable("Is Modded", new CallableIsModded(this));
		return p_71230_1_;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper p_70000_1_)
	{
		super.addServerStatsToSnooper(p_70000_1_);
		p_70000_1_.addData("snooper_partner", mc.getPlayerUsageSnooper().getUniqueID());
	}
	
	@Override public boolean canStructuresSpawn()
	{
		return false;
	}
	
	@Override protected void finalTick(CrashReport p_71228_1_)
	{
		mc.crashed(p_71228_1_);
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
	
	@Override protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long p_71247_3_, WorldType p_71247_5_, String p_71247_6_)
	{
		convertMapIfNeeded(p_71247_1_);
		worldServers = new WorldServer[3];
		timeOfLastDimensionTick = new long[worldServers.length][100];
		ISaveHandler var7 = getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
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
					worldServers[var8] = new DemoWorldServer(this, var7, p_71247_2_, var9, theProfiler, getLogAgent());
				} else
				{
					worldServers[var8] = new WorldServer(this, var7, p_71247_2_, var9, theWorldSettings, theProfiler, getLogAgent());
				}
			} else
			{
				worldServers[var8] = new WorldServerMulti(this, var7, p_71247_2_, var9, theWorldSettings, worldServers[0], theProfiler, getLogAgent());
			}
			worldServers[var8].addWorldAccess(new WorldManager(this, worldServers[var8]));
			getConfigurationManager().setPlayerManager(worldServers);
		}
		setDifficultyForAllWorlds(getDifficulty());
		initialWorldChunkLoad();
	}
	
	@Override public void setGameType(EnumGameType p_71235_1_)
	{
		getConfigurationManager().setGameType(p_71235_1_);
	}
	
	@Override public String shareToLAN(EnumGameType p_71206_1_, boolean p_71206_2_)
	{
		try
		{
			String var3 = theServerListeningThread.func_71755_c();
			getLogAgent().logInfo("Started on " + var3);
			isPublic = true;
			lanServerPing = new ThreadLanServerPing(getMOTD(), var3);
			lanServerPing.start();
			getConfigurationManager().setGameType(p_71206_1_);
			getConfigurationManager().setCommandsAllowedForAll(p_71206_2_);
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
