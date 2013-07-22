package net.minecraft.src;

public class DerivedWorldInfo extends WorldInfo
{
	private final WorldInfo theWorldInfo;
	
	public DerivedWorldInfo(WorldInfo p_i3911_1_)
	{
		theWorldInfo = p_i3911_1_;
	}
	
	@Override public boolean areCommandsAllowed()
	{
		return theWorldInfo.areCommandsAllowed();
	}
	
	@Override public NBTTagCompound cloneNBTCompound(NBTTagCompound p_76082_1_)
	{
		return theWorldInfo.cloneNBTCompound(p_76082_1_);
	}
	
	@Override public GameRules getGameRulesInstance()
	{
		return theWorldInfo.getGameRulesInstance();
	}
	
	@Override public EnumGameType getGameType()
	{
		return theWorldInfo.getGameType();
	}
	
	@Override public long getLastTimePlayed()
	{
		return theWorldInfo.getLastTimePlayed();
	}
	
	@Override public NBTTagCompound getNBTTagCompound()
	{
		return theWorldInfo.getNBTTagCompound();
	}
	
	@Override public NBTTagCompound getPlayerNBTTagCompound()
	{
		return theWorldInfo.getPlayerNBTTagCompound();
	}
	
	@Override public int getRainTime()
	{
		return theWorldInfo.getRainTime();
	}
	
	@Override public int getSaveVersion()
	{
		return theWorldInfo.getSaveVersion();
	}
	
	@Override public long getSeed()
	{
		return theWorldInfo.getSeed();
	}
	
	@Override public long getSizeOnDisk()
	{
		return theWorldInfo.getSizeOnDisk();
	}
	
	@Override public int getSpawnX()
	{
		return theWorldInfo.getSpawnX();
	}
	
	@Override public int getSpawnY()
	{
		return theWorldInfo.getSpawnY();
	}
	
	@Override public int getSpawnZ()
	{
		return theWorldInfo.getSpawnZ();
	}
	
	@Override public WorldType getTerrainType()
	{
		return theWorldInfo.getTerrainType();
	}
	
	@Override public int getThunderTime()
	{
		return theWorldInfo.getThunderTime();
	}
	
	@Override public int getVanillaDimension()
	{
		return theWorldInfo.getVanillaDimension();
	}
	
	@Override public String getWorldName()
	{
		return theWorldInfo.getWorldName();
	}
	
	@Override public long getWorldTime()
	{
		return theWorldInfo.getWorldTime();
	}
	
	@Override public long getWorldTotalTime()
	{
		return theWorldInfo.getWorldTotalTime();
	}
	
	@Override public void incrementTotalWorldTime(long p_82572_1_)
	{
	}
	
	@Override public boolean isHardcoreModeEnabled()
	{
		return theWorldInfo.isHardcoreModeEnabled();
	}
	
	@Override public boolean isInitialized()
	{
		return theWorldInfo.isInitialized();
	}
	
	@Override public boolean isMapFeaturesEnabled()
	{
		return theWorldInfo.isMapFeaturesEnabled();
	}
	
	@Override public boolean isRaining()
	{
		return theWorldInfo.isRaining();
	}
	
	@Override public boolean isThundering()
	{
		return theWorldInfo.isThundering();
	}
	
	@Override public void setRaining(boolean p_76084_1_)
	{
	}
	
	@Override public void setRainTime(int p_76080_1_)
	{
	}
	
	@Override public void setSaveVersion(int p_76078_1_)
	{
	}
	
	@Override public void setServerInitialized(boolean p_76091_1_)
	{
	}
	
	@Override public void setSpawnPosition(int p_76081_1_, int p_76081_2_, int p_76081_3_)
	{
	}
	
	@Override public void setSpawnX(int par1)
	{
	}
	
	@Override public void setSpawnY(int par1)
	{
	}
	
	@Override public void setSpawnZ(int par1)
	{
	}
	
	@Override public void setTerrainType(WorldType p_76085_1_)
	{
	}
	
	@Override public void setThundering(boolean p_76069_1_)
	{
	}
	
	@Override public void setThunderTime(int p_76090_1_)
	{
	}
	
	@Override public void setWorldName(String p_76062_1_)
	{
	}
	
	@Override public void setWorldTime(long p_76068_1_)
	{
	}
}
