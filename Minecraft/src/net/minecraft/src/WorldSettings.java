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
	
	public WorldSettings(long p_i3735_1_, EnumGameType p_i3735_3_, boolean p_i3735_4_, boolean p_i3735_5_, WorldType p_i3735_6_)
	{
		field_82751_h = "";
		seed = p_i3735_1_;
		theGameType = p_i3735_3_;
		mapFeaturesEnabled = p_i3735_4_;
		hardcoreEnabled = p_i3735_5_;
		terrainType = p_i3735_6_;
	}
	
	public WorldSettings(WorldInfo p_i3736_1_)
	{
		this(p_i3736_1_.getSeed(), p_i3736_1_.getGameType(), p_i3736_1_.isMapFeaturesEnabled(), p_i3736_1_.isHardcoreModeEnabled(), p_i3736_1_.getTerrainType());
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
	
	public WorldSettings func_82750_a(String p_82750_1_)
	{
		field_82751_h = p_82750_1_;
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
	
	public static EnumGameType getGameTypeById(int p_77161_0_)
	{
		return EnumGameType.getByID(p_77161_0_);
	}
}
