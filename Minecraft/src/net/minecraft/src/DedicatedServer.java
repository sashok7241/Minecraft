package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.server.MinecraftServer;

public class DedicatedServer extends MinecraftServer implements IServer
{
	private final List pendingCommandList = Collections.synchronizedList(new ArrayList());
	private final ILogAgent field_98131_l;
	private RConThreadQuery theRConThreadQuery;
	private RConThreadMain theRConThreadMain;
	private PropertyManager settings;
	private boolean canSpawnStructures;
	private EnumGameType gameType;
	private NetworkListenThread networkThread;
	private boolean guiIsEnabled;
	
	public DedicatedServer(File par1File)
	{
		super(par1File);
		field_98131_l = new LogAgent("Minecraft-Server", (String) null, new File(par1File, "server.log").getAbsolutePath());
		new DedicatedServerSleepThread(this);
	}
	
	public void addPendingCommand(String par1Str, ICommandSender par2ICommandSender)
	{
		pendingCommandList.add(new ServerCommand(par1Str, par2ICommandSender));
	}
	
	@Override public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport)
	{
		par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
		par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableType(this));
		par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableServerType(this));
		return par1CrashReport;
	}
	
	@Override public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
	{
		par1PlayerUsageSnooper.addData("whitelist_enabled", Boolean.valueOf(getDedicatedPlayerList().isWhiteListEnabled()));
		par1PlayerUsageSnooper.addData("whitelist_count", Integer.valueOf(getDedicatedPlayerList().getWhiteListedPlayers().size()));
		super.addServerStatsToSnooper(par1PlayerUsageSnooper);
	}
	
	@Override public boolean allowSpawnMonsters()
	{
		return settings.getBooleanProperty("spawn-monsters", true);
	}
	
	@Override public boolean canStructuresSpawn()
	{
		return canSpawnStructures;
	}
	
	public void executePendingCommands()
	{
		while(!pendingCommandList.isEmpty())
		{
			ServerCommand var1 = (ServerCommand) pendingCommandList.remove(0);
			getCommandManager().executeCommand(var1.sender, var1.command);
		}
	}
	
	@Override protected void finalTick(CrashReport par1CrashReport)
	{
		while(isServerRunning())
		{
			executePendingCommands();
			try
			{
				Thread.sleep(10L);
			} catch(InterruptedException var3)
			{
				var3.printStackTrace();
			}
		}
	}
	
	@Override public int func_110455_j()
	{
		return settings.getIntProperty("op-permission-level", 4);
	}
	
	@Override public boolean func_96290_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		if(par1World.provider.dimensionId != 0) return false;
		else if(getDedicatedPlayerList().getOps().isEmpty()) return false;
		else if(getDedicatedPlayerList().areCommandsAllowed(par5EntityPlayer.getCommandSenderName())) return false;
		else if(getSpawnProtectionSize() <= 0) return false;
		else
		{
			ChunkCoordinates var6 = par1World.getSpawnPoint();
			int var7 = MathHelper.abs_int(par2 - var6.posX);
			int var8 = MathHelper.abs_int(par4 - var6.posZ);
			int var9 = Math.max(var7, var8);
			return var9 <= getSpawnProtectionSize();
		}
	}
	
	@Override public boolean getAllowNether()
	{
		return settings.getBooleanProperty("allow-nether", true);
	}
	
	public boolean getBooleanProperty(String par1Str, boolean par2)
	{
		return settings.getBooleanProperty(par1Str, par2);
	}
	
	@Override public ServerConfigurationManager getConfigurationManager()
	{
		return getDedicatedPlayerList();
	}
	
	public DedicatedPlayerList getDedicatedPlayerList()
	{
		return (DedicatedPlayerList) super.getConfigurationManager();
	}
	
	@Override public int getDifficulty()
	{
		return settings.getIntProperty("difficulty", 1);
	}
	
	@Override public EnumGameType getGameType()
	{
		return gameType;
	}
	
	@Override public boolean getGuiEnabled()
	{
		return guiIsEnabled;
	}
	
	@Override public int getIntProperty(String par1Str, int par2)
	{
		return settings.getIntProperty(par1Str, par2);
	}
	
	@Override public ILogAgent getLogAgent()
	{
		return field_98131_l;
	}
	
	@Override public NetworkListenThread getNetworkThread()
	{
		return networkThread;
	}
	
	@Override public String getSettingsFilename()
	{
		File var1 = settings.getPropertiesFile();
		return var1 != null ? var1.getAbsolutePath() : "No settings file";
	}
	
	@Override public int getSpawnProtectionSize()
	{
		return settings.getIntProperty("spawn-protection", super.getSpawnProtectionSize());
	}
	
	@Override public String getStringProperty(String par1Str, String par2Str)
	{
		return settings.getProperty(par1Str, par2Str);
	}
	
	@Override public boolean isCommandBlockEnabled()
	{
		return settings.getBooleanProperty("enable-command-block", false);
	}
	
	@Override public boolean isDedicatedServer()
	{
		return true;
	}
	
	@Override public boolean isHardcore()
	{
		return settings.getBooleanProperty("hardcore", false);
	}
	
	@Override public boolean isSnooperEnabled()
	{
		return settings.getBooleanProperty("snooper-enabled", true);
	}
	
	@Override public void saveProperties()
	{
		settings.saveProperties();
	}
	
	@Override public void setProperty(String par1Str, Object par2Obj)
	{
		settings.setProperty(par1Str, par2Obj);
	}
	
	@Override public String shareToLAN(EnumGameType par1EnumGameType, boolean par2)
	{
		return "";
	}
	
	@Override protected boolean startServer() throws IOException
	{
		DedicatedServerCommandThread var1 = new DedicatedServerCommandThread(this);
		var1.setDaemon(true);
		var1.start();
		getLogAgent().logInfo("Starting minecraft server version 1.6.2");
		if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
		{
			getLogAgent().logWarning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
		}
		getLogAgent().logInfo("Loading properties");
		settings = new PropertyManager(new File("server.properties"), getLogAgent());
		if(isSinglePlayer())
		{
			setHostname("127.0.0.1");
		} else
		{
			setOnlineMode(settings.getBooleanProperty("online-mode", true));
			setHostname(settings.getProperty("server-ip", ""));
		}
		setCanSpawnAnimals(settings.getBooleanProperty("spawn-animals", true));
		setCanSpawnNPCs(settings.getBooleanProperty("spawn-npcs", true));
		setAllowPvp(settings.getBooleanProperty("pvp", true));
		setAllowFlight(settings.getBooleanProperty("allow-flight", false));
		setTexturePack(settings.getProperty("texture-pack", ""));
		setMOTD(settings.getProperty("motd", "A Minecraft Server"));
		func_104055_i(settings.getBooleanProperty("force-gamemode", false));
		if(settings.getIntProperty("difficulty", 1) < 0)
		{
			settings.setProperty("difficulty", Integer.valueOf(0));
		} else if(settings.getIntProperty("difficulty", 1) > 3)
		{
			settings.setProperty("difficulty", Integer.valueOf(3));
		}
		canSpawnStructures = settings.getBooleanProperty("generate-structures", true);
		int var2 = settings.getIntProperty("gamemode", EnumGameType.SURVIVAL.getID());
		gameType = WorldSettings.getGameTypeById(var2);
		getLogAgent().logInfo("Default game type: " + gameType);
		InetAddress var3 = null;
		if(getServerHostname().length() > 0)
		{
			var3 = InetAddress.getByName(getServerHostname());
		}
		if(getServerPort() < 0)
		{
			setServerPort(settings.getIntProperty("server-port", 25565));
		}
		getLogAgent().logInfo("Generating keypair");
		setKeyPair(CryptManager.createNewKeyPair());
		getLogAgent().logInfo("Starting Minecraft server on " + (getServerHostname().length() == 0 ? "*" : getServerHostname()) + ":" + getServerPort());
		try
		{
			networkThread = new DedicatedServerListenThread(this, var3, getServerPort());
		} catch(IOException var16)
		{
			getLogAgent().logWarning("**** FAILED TO BIND TO PORT!");
			getLogAgent().logWarningFormatted("The exception was: {0}", new Object[] { var16.toString() });
			getLogAgent().logWarning("Perhaps a server is already running on that port?");
			return false;
		}
		if(!isServerInOnlineMode())
		{
			getLogAgent().logWarning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
			getLogAgent().logWarning("The server will make no attempt to authenticate usernames. Beware.");
			getLogAgent().logWarning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
			getLogAgent().logWarning("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
		}
		setConfigurationManager(new DedicatedPlayerList(this));
		long var4 = System.nanoTime();
		if(getFolderName() == null)
		{
			setFolderName(settings.getProperty("level-name", "world"));
		}
		String var6 = settings.getProperty("level-seed", "");
		String var7 = settings.getProperty("level-type", "DEFAULT");
		String var8 = settings.getProperty("generator-settings", "");
		long var9 = new Random().nextLong();
		if(var6.length() > 0)
		{
			try
			{
				long var11 = Long.parseLong(var6);
				if(var11 != 0L)
				{
					var9 = var11;
				}
			} catch(NumberFormatException var15)
			{
				var9 = var6.hashCode();
			}
		}
		WorldType var17 = WorldType.parseWorldType(var7);
		if(var17 == null)
		{
			var17 = WorldType.DEFAULT;
		}
		setBuildLimit(settings.getIntProperty("max-build-height", 256));
		setBuildLimit((getBuildLimit() + 8) / 16 * 16);
		setBuildLimit(MathHelper.clamp_int(getBuildLimit(), 64, 256));
		settings.setProperty("max-build-height", Integer.valueOf(getBuildLimit()));
		getLogAgent().logInfo("Preparing level \"" + getFolderName() + "\"");
		loadAllWorlds(getFolderName(), getFolderName(), var9, var17, var8);
		long var12 = System.nanoTime() - var4;
		String var14 = String.format("%.3fs", new Object[] { Double.valueOf(var12 / 1.0E9D) });
		getLogAgent().logInfo("Done (" + var14 + ")! For help, type \"help\" or \"?\"");
		if(settings.getBooleanProperty("enable-query", false))
		{
			getLogAgent().logInfo("Starting GS4 status listener");
			theRConThreadQuery = new RConThreadQuery(this);
			theRConThreadQuery.startThread();
		}
		if(settings.getBooleanProperty("enable-rcon", false))
		{
			getLogAgent().logInfo("Starting remote control listener");
			theRConThreadMain = new RConThreadMain(this);
			theRConThreadMain.startThread();
		}
		return true;
	}
	
	@Override protected void systemExitNow()
	{
		System.exit(0);
	}
	
	@Override public void updateTimeLightAndEntities()
	{
		super.updateTimeLightAndEntities();
		executePendingCommands();
	}
}
