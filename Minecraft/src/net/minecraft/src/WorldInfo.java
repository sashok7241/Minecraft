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
	
	public WorldInfo(NBTTagCompound p_i3914_1_)
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
		randomSeed = p_i3914_1_.getLong("RandomSeed");
		if(p_i3914_1_.hasKey("generatorName"))
		{
			String var2 = p_i3914_1_.getString("generatorName");
			terrainType = WorldType.parseWorldType(var2);
			if(terrainType == null)
			{
				terrainType = WorldType.DEFAULT;
			} else if(terrainType.isVersioned())
			{
				int var3 = 0;
				if(p_i3914_1_.hasKey("generatorVersion"))
				{
					var3 = p_i3914_1_.getInteger("generatorVersion");
				}
				terrainType = terrainType.getWorldTypeForGeneratorVersion(var3);
			}
			if(p_i3914_1_.hasKey("generatorOptions"))
			{
				generatorOptions = p_i3914_1_.getString("generatorOptions");
			}
		}
		theGameType = EnumGameType.getByID(p_i3914_1_.getInteger("GameType"));
		if(p_i3914_1_.hasKey("MapFeatures"))
		{
			mapFeaturesEnabled = p_i3914_1_.getBoolean("MapFeatures");
		} else
		{
			mapFeaturesEnabled = true;
		}
		spawnX = p_i3914_1_.getInteger("SpawnX");
		spawnY = p_i3914_1_.getInteger("SpawnY");
		spawnZ = p_i3914_1_.getInteger("SpawnZ");
		totalTime = p_i3914_1_.getLong("Time");
		if(p_i3914_1_.hasKey("DayTime"))
		{
			worldTime = p_i3914_1_.getLong("DayTime");
		} else
		{
			worldTime = totalTime;
		}
		lastTimePlayed = p_i3914_1_.getLong("LastPlayed");
		sizeOnDisk = p_i3914_1_.getLong("SizeOnDisk");
		levelName = p_i3914_1_.getString("LevelName");
		saveVersion = p_i3914_1_.getInteger("version");
		rainTime = p_i3914_1_.getInteger("rainTime");
		raining = p_i3914_1_.getBoolean("raining");
		thunderTime = p_i3914_1_.getInteger("thunderTime");
		thundering = p_i3914_1_.getBoolean("thundering");
		hardcore = p_i3914_1_.getBoolean("hardcore");
		if(p_i3914_1_.hasKey("initialized"))
		{
			initialized = p_i3914_1_.getBoolean("initialized");
		} else
		{
			initialized = true;
		}
		if(p_i3914_1_.hasKey("allowCommands"))
		{
			allowCommands = p_i3914_1_.getBoolean("allowCommands");
		} else
		{
			allowCommands = theGameType == EnumGameType.CREATIVE;
		}
		if(p_i3914_1_.hasKey("Player"))
		{
			playerTag = p_i3914_1_.getCompoundTag("Player");
			dimension = playerTag.getInteger("Dimension");
		}
		if(p_i3914_1_.hasKey("GameRules"))
		{
			theGameRules.readGameRulesFromNBT(p_i3914_1_.getCompoundTag("GameRules"));
		}
	}
	
	public WorldInfo(WorldInfo p_i3916_1_)
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
		randomSeed = p_i3916_1_.randomSeed;
		terrainType = p_i3916_1_.terrainType;
		generatorOptions = p_i3916_1_.generatorOptions;
		theGameType = p_i3916_1_.theGameType;
		mapFeaturesEnabled = p_i3916_1_.mapFeaturesEnabled;
		spawnX = p_i3916_1_.spawnX;
		spawnY = p_i3916_1_.spawnY;
		spawnZ = p_i3916_1_.spawnZ;
		totalTime = p_i3916_1_.totalTime;
		worldTime = p_i3916_1_.worldTime;
		lastTimePlayed = p_i3916_1_.lastTimePlayed;
		sizeOnDisk = p_i3916_1_.sizeOnDisk;
		playerTag = p_i3916_1_.playerTag;
		dimension = p_i3916_1_.dimension;
		levelName = p_i3916_1_.levelName;
		saveVersion = p_i3916_1_.saveVersion;
		rainTime = p_i3916_1_.rainTime;
		raining = p_i3916_1_.raining;
		thunderTime = p_i3916_1_.thunderTime;
		thundering = p_i3916_1_.thundering;
		hardcore = p_i3916_1_.hardcore;
		allowCommands = p_i3916_1_.allowCommands;
		initialized = p_i3916_1_.initialized;
		theGameRules = p_i3916_1_.theGameRules;
	}
	
	public WorldInfo(WorldSettings p_i3915_1_, String p_i3915_2_)
	{
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		theGameRules = new GameRules();
		randomSeed = p_i3915_1_.getSeed();
		theGameType = p_i3915_1_.getGameType();
		mapFeaturesEnabled = p_i3915_1_.isMapFeaturesEnabled();
		levelName = p_i3915_2_;
		hardcore = p_i3915_1_.getHardcoreEnabled();
		terrainType = p_i3915_1_.getTerrainType();
		generatorOptions = p_i3915_1_.func_82749_j();
		allowCommands = p_i3915_1_.areCommandsAllowed();
		initialized = false;
	}
	
	public void addToCrashReport(CrashReportCategory p_85118_1_)
	{
		p_85118_1_.addCrashSectionCallable("Level seed", new CallableLevelSeed(this));
		p_85118_1_.addCrashSectionCallable("Level generator", new CallableLevelGenerator(this));
		p_85118_1_.addCrashSectionCallable("Level generator options", new CallableLevelGeneratorOptions(this));
		p_85118_1_.addCrashSectionCallable("Level spawn location", new CallableLevelSpawnLocation(this));
		p_85118_1_.addCrashSectionCallable("Level time", new CallableLevelTime(this));
		p_85118_1_.addCrashSectionCallable("Level dimension", new CallableLevelDimension(this));
		p_85118_1_.addCrashSectionCallable("Level storage version", new CallableLevelStorageVersion(this));
		p_85118_1_.addCrashSectionCallable("Level weather", new CallableLevelWeather(this));
		p_85118_1_.addCrashSectionCallable("Level game mode", new CallableLevelGamemode(this));
	}
	
	public boolean areCommandsAllowed()
	{
		return allowCommands;
	}
	
	public NBTTagCompound cloneNBTCompound(NBTTagCompound p_76082_1_)
	{
		NBTTagCompound var2 = new NBTTagCompound();
		updateTagCompound(var2, p_76082_1_);
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
	
	public void incrementTotalWorldTime(long p_82572_1_)
	{
		totalTime = p_82572_1_;
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
	
	public void setGameType(EnumGameType p_76060_1_)
	{
		theGameType = p_76060_1_;
	}
	
	public void setRaining(boolean p_76084_1_)
	{
		raining = p_76084_1_;
	}
	
	public void setRainTime(int p_76080_1_)
	{
		rainTime = p_76080_1_;
	}
	
	public void setSaveVersion(int p_76078_1_)
	{
		saveVersion = p_76078_1_;
	}
	
	public void setServerInitialized(boolean p_76091_1_)
	{
		initialized = p_76091_1_;
	}
	
	public void setSpawnPosition(int p_76081_1_, int p_76081_2_, int p_76081_3_)
	{
		spawnX = p_76081_1_;
		spawnY = p_76081_2_;
		spawnZ = p_76081_3_;
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
	
	public void setTerrainType(WorldType p_76085_1_)
	{
		terrainType = p_76085_1_;
	}
	
	public void setThundering(boolean p_76069_1_)
	{
		thundering = p_76069_1_;
	}
	
	public void setThunderTime(int p_76090_1_)
	{
		thunderTime = p_76090_1_;
	}
	
	public void setWorldName(String p_76062_1_)
	{
		levelName = p_76062_1_;
	}
	
	public void setWorldTime(long p_76068_1_)
	{
		worldTime = p_76068_1_;
	}
	
	private void updateTagCompound(NBTTagCompound p_76064_1_, NBTTagCompound p_76064_2_)
	{
		p_76064_1_.setLong("RandomSeed", randomSeed);
		p_76064_1_.setString("generatorName", terrainType.getWorldTypeName());
		p_76064_1_.setInteger("generatorVersion", terrainType.getGeneratorVersion());
		p_76064_1_.setString("generatorOptions", generatorOptions);
		p_76064_1_.setInteger("GameType", theGameType.getID());
		p_76064_1_.setBoolean("MapFeatures", mapFeaturesEnabled);
		p_76064_1_.setInteger("SpawnX", spawnX);
		p_76064_1_.setInteger("SpawnY", spawnY);
		p_76064_1_.setInteger("SpawnZ", spawnZ);
		p_76064_1_.setLong("Time", totalTime);
		p_76064_1_.setLong("DayTime", worldTime);
		p_76064_1_.setLong("SizeOnDisk", sizeOnDisk);
		p_76064_1_.setLong("LastPlayed", System.currentTimeMillis());
		p_76064_1_.setString("LevelName", levelName);
		p_76064_1_.setInteger("version", saveVersion);
		p_76064_1_.setInteger("rainTime", rainTime);
		p_76064_1_.setBoolean("raining", raining);
		p_76064_1_.setInteger("thunderTime", thunderTime);
		p_76064_1_.setBoolean("thundering", thundering);
		p_76064_1_.setBoolean("hardcore", hardcore);
		p_76064_1_.setBoolean("allowCommands", allowCommands);
		p_76064_1_.setBoolean("initialized", initialized);
		p_76064_1_.setCompoundTag("GameRules", theGameRules.writeGameRulesToNBT());
		if(p_76064_2_ != null)
		{
			p_76064_1_.setCompoundTag("Player", p_76064_2_);
		}
	}
	
	static boolean func_85117_p(WorldInfo p_85117_0_)
	{
		return p_85117_0_.hardcore;
	}
	
	static int func_85122_i(WorldInfo p_85122_0_)
	{
		return p_85122_0_.dimension;
	}
	
	static long func_85126_g(WorldInfo p_85126_0_)
	{
		return p_85126_0_.totalTime;
	}
	
	static boolean func_85131_q(WorldInfo p_85131_0_)
	{
		return p_85131_0_.allowCommands;
	}
	
	static EnumGameType getGameType(WorldInfo p_85120_0_)
	{
		return p_85120_0_.theGameType;
	}
	
	static boolean getMapFeaturesEnabled(WorldInfo p_85128_0_)
	{
		return p_85128_0_.mapFeaturesEnabled;
	}
	
	static boolean getRaining(WorldInfo p_85127_0_)
	{
		return p_85127_0_.raining;
	}
	
	static int getRainTime(WorldInfo p_85119_0_)
	{
		return p_85119_0_.rainTime;
	}
	
	static int getSaveVersion(WorldInfo p_85121_0_)
	{
		return p_85121_0_.saveVersion;
	}
	
	static int getSpawnXCoordinate(WorldInfo p_85125_0_)
	{
		return p_85125_0_.spawnX;
	}
	
	static int getSpawnYCoordinate(WorldInfo p_85124_0_)
	{
		return p_85124_0_.spawnY;
	}
	
	static int getSpawnZCoordinate(WorldInfo p_85123_0_)
	{
		return p_85123_0_.spawnZ;
	}
	
	static WorldType getTerrainTypeOfWorld(WorldInfo p_85132_0_)
	{
		return p_85132_0_.terrainType;
	}
	
	static boolean getThundering(WorldInfo p_85116_0_)
	{
		return p_85116_0_.thundering;
	}
	
	static int getThunderTime(WorldInfo p_85133_0_)
	{
		return p_85133_0_.thunderTime;
	}
	
	static String getWorldGeneratorOptions(WorldInfo p_85130_0_)
	{
		return p_85130_0_.generatorOptions;
	}
	
	static long getWorldTime(WorldInfo p_85129_0_)
	{
		return p_85129_0_.worldTime;
	}
}
