package net.minecraft.src;

public class WorldType
{
	public static final WorldType[] worldTypes = new WorldType[16];
	public static final WorldType DEFAULT = new WorldType(0, "default", 1).setVersioned();
	public static final WorldType FLAT = new WorldType(1, "flat");
	public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
	public static final WorldType DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
	private final int worldTypeId;
	private final String worldType;
	private final int generatorVersion;
	private boolean canBeCreated;
	private boolean isWorldTypeVersioned;
	
	private WorldType(int p_i3737_1_, String p_i3737_2_)
	{
		this(p_i3737_1_, p_i3737_2_, 0);
	}
	
	private WorldType(int p_i3738_1_, String p_i3738_2_, int p_i3738_3_)
	{
		worldType = p_i3738_2_;
		generatorVersion = p_i3738_3_;
		canBeCreated = true;
		worldTypeId = p_i3738_1_;
		worldTypes[p_i3738_1_] = this;
	}
	
	public boolean getCanBeCreated()
	{
		return canBeCreated;
	}
	
	public int getGeneratorVersion()
	{
		return generatorVersion;
	}
	
	public String getTranslateName()
	{
		return "generator." + worldType;
	}
	
	public WorldType getWorldTypeForGeneratorVersion(int p_77132_1_)
	{
		return this == DEFAULT && p_77132_1_ == 0 ? DEFAULT_1_1 : this;
	}
	
	public int getWorldTypeID()
	{
		return worldTypeId;
	}
	
	public String getWorldTypeName()
	{
		return worldType;
	}
	
	public boolean isVersioned()
	{
		return isWorldTypeVersioned;
	}
	
	private WorldType setCanBeCreated(boolean p_77124_1_)
	{
		canBeCreated = p_77124_1_;
		return this;
	}
	
	private WorldType setVersioned()
	{
		isWorldTypeVersioned = true;
		return this;
	}
	
	public static WorldType parseWorldType(String p_77130_0_)
	{
		for(WorldType worldType2 : worldTypes)
		{
			if(worldType2 != null && worldType2.worldType.equalsIgnoreCase(p_77130_0_)) return worldType2;
		}
		return null;
	}
}
