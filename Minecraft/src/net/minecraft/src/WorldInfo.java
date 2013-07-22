package net.minecraft.src;

public class WorldInfo
{
	private long randomSeed;
	private WorldType terrainType;
	private String generatorOptions;
	private int spawnX;
	private int spawnY;
	private int spawnZ;
	private long totalTime;
	private long worldTime;
	private long lastTimePlayed;
	private long sizeOnDisk;
	private NBTTagCompound playerTag;
	private int dimension;
	private String levelName;
	private int saveVersion;
	private boolean raining;
	private int rainTime;
	private boolean thundering;
	private int thunderTime;
	private EnumGameType theGameType;
	private boolean mapFeaturesEnabled;
	private boolean hardcore;
	private boolean allowCommands;
	private boolean initialized;
	private GameRules theGameRules;
	
	protected WorldInfo()
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
	}
	
	public WorldInfo(NBTTagCompound par1NBTTagCompound)
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
		randomSeed = par1NBTTagCompound.getLong("RandomSeed");
		if(par1NBTTagCompound.hasKey("generatorName"))
		{
			String var2 = par1NBTTagCompound.getString("generatorName");
			terrainType = WorldType.parseWorldType(var2);
			if(terrainType == null)
			{
				terrainType = WorldType.DEFAULT;
			} else if(terrainType.isVersioned())
			{
				int var3 = 0;
				if(par1NBTTagCompound.hasKey("generatorVersion"))
				{
					var3 = par1NBTTagCompound.getInteger("generatorVersion");
				}
				terrainType = terrainType.getWorldTypeForGeneratorVersion(var3);
			}
			if(par1NBTTagCompound.hasKey("generatorOptions"))
			{
				generatorOptions = par1NBTTagCompound.getString("generatorOptions");
			}
		}
		theGameType = EnumGameType.getByID(par1NBTTagCompound.getInteger("GameType"));
		if(par1NBTTagCompound.hasKey("MapFeatures"))
		{
			mapFeaturesEnabled = par1NBTTagCompound.getBoolean("MapFeatures");
		} else
		{
			mapFeaturesEnabled = true;
		}
		spawnX = par1NBTTagCompound.getInteger("SpawnX");
		spawnY = par1NBTTagCompound.getInteger("SpawnY");
		spawnZ = par1NBTTagCompound.getInteger("SpawnZ");
		totalTime = par1NBTTagCompound.getLong("Time");
		if(par1NBTTagCompound.hasKey("DayTime"))
		{
			worldTime = par1NBTTagCompound.getLong("DayTime");
		} else
		{
			worldTime = totalTime;
		}
		lastTimePlayed = par1NBTTagCompound.getLong("LastPlayed");
		sizeOnDisk = par1NBTTagCompound.getLong("SizeOnDisk");
		levelName = par1NBTTagCompound.getString("LevelName");
		saveVersion = par1NBTTagCompound.getInteger("version");
		rainTime = par1NBTTagCompound.getInteger("rainTime");
		raining = par1NBTTagCompound.getBoolean("raining");
		thunderTime = par1NBTTagCompound.getInteger("thunderTime");
		thundering = par1NBTTagCompound.getBoolean("thundering");
		hardcore = par1NBTTagCompound.getBoolean("hardcore");
		if(par1NBTTagCompound.hasKey("initialized"))
		{
			initialized = par1NBTTagCompound.getBoolean("initialized");
		} else
		{
			initialized = true;
		}
		if(par1NBTTagCompound.hasKey("allowCommands"))
		{
			allowCommands = par1NBTTagCompound.getBoolean("allowCommands");
		} else
		{
			allowCommands = theGameType == EnumGameType.CREATIVE;
		}
		if(par1NBTTagCompound.hasKey("Player"))
		{
			playerTag = par1NBTTagCompound.getCompoundTag("Player");
			dimension = playerTag.getInteger("Dimension");
		}
		if(par1NBTTagCompound.hasKey("GameRules"))
		{
			theGameRules.readGameRulesFromNBT(par1NBTTagCompound.getCompoundTag("GameRules"));
		}
	}
	
	public WorldInfo(WorldInfo par1WorldInfo)
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
		randomSeed = par1WorldInfo.randomSeed;
		terrainType = par1WorldInfo.terrainType;
		generatorOptions = par1WorldInfo.generatorOptions;
		theGameType = par1WorldInfo.theGameType;
		mapFeaturesEnabled = par1WorldInfo.mapFeaturesEnabled;
		spawnX = par1WorldInfo.spawnX;
		spawnY = par1WorldInfo.spawnY;
		spawnZ = par1WorldInfo.spawnZ;
		totalTime = par1WorldInfo.totalTime;
		worldTime = par1WorldInfo.worldTime;
		lastTimePlayed = par1WorldInfo.lastTimePlayed;
		sizeOnDisk = par1WorldInfo.sizeOnDisk;
		playerTag = par1WorldInfo.playerTag;
		dimension = par1WorldInfo.dimension;
		levelName = par1WorldInfo.levelName;
		saveVersion = par1WorldInfo.saveVersion;
		rainTime = par1WorldInfo.rainTime;
		raining = par1WorldInfo.raining;
		thunderTime = par1WorldInfo.thunderTime;
		thundering = par1WorldInfo.thundering;
		hardcore = par1WorldInfo.hardcore;
		allowCommands = par1WorldInfo.allowCommands;
		initialized = par1WorldInfo.initialized;
		theGameRules = par1WorldInfo.theGameRules;
	}
	
	public WorldInfo(WorldSettings par1WorldSettings, String par2Str)
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
		randomSeed = par1WorldSettings.getSeed();
		theGameType = par1WorldSettings.getGameType();
		mapFeaturesEnabled = par1WorldSettings.isMapFeaturesEnabled();
		levelName = par2Str;
		hardcore = par1WorldSettings.getHardcoreEnabled();
		terrainType = par1WorldSettings.getTerrainType();
		generatorOptions = par1WorldSettings.func_82749_j();
		allowCommands = par1WorldSettings.areCommandsAllowed();
		initialized = false;
	}
	
	public void addToCrashReport(CrashReportCategory par1CrashReportCategory)
	{
		par1CrashReportCategory.addCrashSectionCallable("Level seed", new CallableLevelSeed(this));
		par1CrashReportCategory.addCrashSectionCallable("Level generator", new CallableLevelGenerator(this));
		par1CrashReportCategory.addCrashSectionCallable("Level generator options", new CallableLevelGeneratorOptions(this));
		par1CrashReportCategory.addCrashSectionCallable("Level spawn location", new CallableLevelSpawnLocation(this));
		par1CrashReportCategory.addCrashSectionCallable("Level time", new CallableLevelTime(this));
		par1CrashReportCategory.addCrashSectionCallable("Level dimension", new CallableLevelDimension(this));
		par1CrashReportCategory.addCrashSectionCallable("Level storage version", new CallableLevelStorageVersion(this));
		par1CrashReportCategory.addCrashSectionCallable("Level weather", new CallableLevelWeather(this));
		par1CrashReportCategory.addCrashSectionCallable("Level game mode", new CallableLevelGamemode(this));
	}
	
	public boolean areCommandsAllowed()
	{
		return allowCommands;
	}
	
	public NBTTagCompound cloneNBTCompound(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagCompound var2 = new NBTTagCompound();
		updateTagCompound(var2, par1NBTTagCompound);
		return var2;
	}
	
	public GameRules getGameRulesInstance()
	{
		return theGameRules;
	}
	
	public EnumGameType getGameType()
	{
		return theGameType;
	}
	
	public String getGeneratorOptions()
	{
		return generatorOptions;
	}
	
	public long getLastTimePlayed()
	{
		return lastTimePlayed;
	}
	
	public NBTTagCompound getNBTTagCompound()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		updateTagCompound(var1, playerTag);
		return var1;
	}
	
	public NBTTagCompound getPlayerNBTTagCompound()
	{
		return playerTag;
	}
	
	public int getRainTime()
	{
		return rainTime;
	}
	
	public int getSaveVersion()
	{
		return saveVersion;
	}
	
	public long getSeed()
	{
		return randomSeed;
	}
	
	public long getSizeOnDisk()
	{
		return sizeOnDisk;
	}
	
	public int getSpawnX()
	{
		return spawnX;
	}
	
	public int getSpawnY()
	{
		return spawnY;
	}
	
	public int getSpawnZ()
	{
		return spawnZ;
	}
	
	public WorldType getTerrainType()
	{
		return terrainType;
	}
	
	public int getThunderTime()
	{
		return thunderTime;
	}
	
	public int getVanillaDimension()
	{
		return dimension;
	}
	
	public String getWorldName()
	{
		return levelName;
	}
	
	public long getWorldTime()
	{
		return worldTime;
	}
	
	public long getWorldTotalTime()
	{
		return totalTime;
	}
	
	public void incrementTotalWorldTime(long par1)
	{
		totalTime = par1;
	}
	
	public boolean isHardcoreModeEnabled()
	{
		return hardcore;
	}
	
	public boolean isInitialized()
	{
		return initialized;
	}
	
	public boolean isMapFeaturesEnabled()
	{
		return mapFeaturesEnabled;
	}
	
	public boolean isRaining()
	{
		return raining;
	}
	
	public boolean isThundering()
	{
		return thundering;
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
		theGameType = par1EnumGameType;
	}
	
	public void setRaining(boolean par1)
	{
		raining = par1;
	}
	
	public void setRainTime(int par1)
	{
		rainTime = par1;
	}
	
	public void setSaveVersion(int par1)
	{
		saveVersion = par1;
	}
	
	public void setServerInitialized(boolean par1)
	{
		initialized = par1;
	}
	
	public void setSpawnPosition(int par1, int par2, int par3)
	{
		spawnX = par1;
		spawnY = par2;
		spawnZ = par3;
	}
	
	public void setSpawnX(int par1)
	{
		spawnX = par1;
	}
	
	public void setSpawnY(int par1)
	{
		spawnY = par1;
	}
	
	public void setSpawnZ(int par1)
	{
		spawnZ = par1;
	}
	
	public void setTerrainType(WorldType par1WorldType)
	{
		terrainType = par1WorldType;
	}
	
	public void setThundering(boolean par1)
	{
		thundering = par1;
	}
	
	public void setThunderTime(int par1)
	{
		thunderTime = par1;
	}
	
	public void setWorldName(String par1Str)
	{
		levelName = par1Str;
	}
	
	public void setWorldTime(long par1)
	{
		worldTime = par1;
	}
	
	private void updateTagCompound(NBTTagCompound par1NBTTagCompound, NBTTagCompound par2NBTTagCompound)
	{
		par1NBTTagCompound.setLong("RandomSeed", randomSeed);
		par1NBTTagCompound.setString("generatorName", terrainType.getWorldTypeName());
		par1NBTTagCompound.setInteger("generatorVersion", terrainType.getGeneratorVersion());
		par1NBTTagCompound.setString("generatorOptions", generatorOptions);
		par1NBTTagCompound.setInteger("GameType", theGameType.getID());
		par1NBTTagCompound.setBoolean("MapFeatures", mapFeaturesEnabled);
		par1NBTTagCompound.setInteger("SpawnX", spawnX);
		par1NBTTagCompound.setInteger("SpawnY", spawnY);
		par1NBTTagCompound.setInteger("SpawnZ", spawnZ);
		par1NBTTagCompound.setLong("Time", totalTime);
		par1NBTTagCompound.setLong("DayTime", worldTime);
		par1NBTTagCompound.setLong("SizeOnDisk", sizeOnDisk);
		par1NBTTagCompound.setLong("LastPlayed", System.currentTimeMillis());
		par1NBTTagCompound.setString("LevelName", levelName);
		par1NBTTagCompound.setInteger("version", saveVersion);
		par1NBTTagCompound.setInteger("rainTime", rainTime);
		par1NBTTagCompound.setBoolean("raining", raining);
		par1NBTTagCompound.setInteger("thunderTime", thunderTime);
		par1NBTTagCompound.setBoolean("thundering", thundering);
		par1NBTTagCompound.setBoolean("hardcore", hardcore);
		par1NBTTagCompound.setBoolean("allowCommands", allowCommands);
		par1NBTTagCompound.setBoolean("initialized", initialized);
		par1NBTTagCompound.setCompoundTag("GameRules", theGameRules.writeGameRulesToNBT());
		if(par2NBTTagCompound != null)
		{
			par1NBTTagCompound.setCompoundTag("Player", par2NBTTagCompound);
		}
	}
	
	static boolean func_85117_p(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.hardcore;
	}
	
	static int func_85122_i(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.dimension;
	}
	
	static long func_85126_g(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.totalTime;
	}
	
	static boolean func_85131_q(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.allowCommands;
	}
	
	static EnumGameType getGameType(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.theGameType;
	}
	
	static boolean getMapFeaturesEnabled(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.mapFeaturesEnabled;
	}
	
	static boolean getRaining(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.raining;
	}
	
	static int getRainTime(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.rainTime;
	}
	
	static int getSaveVersion(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.saveVersion;
	}
	
	static int getSpawnXCoordinate(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.spawnX;
	}
	
	static int getSpawnYCoordinate(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.spawnY;
	}
	
	static int getSpawnZCoordinate(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.spawnZ;
	}
	
	static WorldType getTerrainTypeOfWorld(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.terrainType;
	}
	
	static boolean getThundering(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.thundering;
	}
	
	static int getThunderTime(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.thunderTime;
	}
	
	static String getWorldGeneratorOptions(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.generatorOptions;
	}
	
	static long getWorldTime(WorldInfo par0WorldInfo)
	{
		return par0WorldInfo.worldTime;
	}
}
