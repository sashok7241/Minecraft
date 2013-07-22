package net.minecraft.src;

public final class WorldSettings
{
	private final long seed;
	private final EnumGameType theGameType;
	private final boolean mapFeaturesEnabled;
	private final boolean hardcoreEnabled;
	private final WorldType terrainType;
	private boolean commandsAllowed;
	private boolean bonusChestEnabled;
	private String field_82751_h;
	
	public WorldSettings(long par1, EnumGameType par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
	{
		field_82751_h = "";
		seed = par1;
		theGameType = par3EnumGameType;
		mapFeaturesEnabled = par4;
		hardcoreEnabled = par5;
		terrainType = par6WorldType;
	}
	
	public WorldSettings(WorldInfo par1WorldInfo)
	{
		this(par1WorldInfo.getSeed(), par1WorldInfo.getGameType(), par1WorldInfo.isMapFeaturesEnabled(), par1WorldInfo.isHardcoreModeEnabled(), par1WorldInfo.getTerrainType());
	}
	
	public boolean areCommandsAllowed()
	{
		return commandsAllowed;
	}
	
	public WorldSettings enableBonusChest()
	{
		bonusChestEnabled = true;
		return this;
	}
	
	public WorldSettings enableCommands()
	{
		commandsAllowed = true;
		return this;
	}
	
	public String func_82749_j()
	{
		return field_82751_h;
	}
	
	public WorldSettings func_82750_a(String par1Str)
	{
		field_82751_h = par1Str;
		return this;
	}
	
	public EnumGameType getGameType()
	{
		return theGameType;
	}
	
	public boolean getHardcoreEnabled()
	{
		return hardcoreEnabled;
	}
	
	public long getSeed()
	{
		return seed;
	}
	
	public WorldType getTerrainType()
	{
		return terrainType;
	}
	
	public boolean isBonusChestEnabled()
	{
		return bonusChestEnabled;
	}
	
	public boolean isMapFeaturesEnabled()
	{
		return mapFeaturesEnabled;
	}
	
	public static EnumGameType getGameTypeById(int par0)
	{
		return EnumGameType.getByID(par0);
	}
}
