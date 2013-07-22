package net.minecraft.src;

public class DerivedWorldInfo extends WorldInfo
{
	private final WorldInfo theWorldInfo;
	
	public DerivedWorldInfo(WorldInfo par1WorldInfo)
	{
		theWorldInfo = par1WorldInfo;
	}
	
	@Override public boolean areCommandsAllowed()
	{
		return theWorldInfo.areCommandsAllowed();
	}
	
	@Override public NBTTagCompound cloneNBTCompound(NBTTagCompound par1NBTTagCompound)
	{
		return theWorldInfo.cloneNBTCompound(par1NBTTagCompound);
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
	
	@Override public void incrementTotalWorldTime(long par1)
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
	
	@Override public void setRaining(boolean par1)
	{
	}
	
	@Override public void setRainTime(int par1)
	{
	}
	
	@Override public void setSaveVersion(int par1)
	{
	}
	
	@Override public void setServerInitialized(boolean par1)
	{
	}
	
	@Override public void setSpawnPosition(int par1, int par2, int par3)
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
	
	@Override public void setTerrainType(WorldType par1WorldType)
	{
	}
	
	@Override public void setThundering(boolean par1)
	{
	}
	
	@Override public void setThunderTime(int par1)
	{
	}
	
	@Override public void setWorldName(String par1Str)
	{
	}
	
	@Override public void setWorldTime(long par1)
	{
	}
}
