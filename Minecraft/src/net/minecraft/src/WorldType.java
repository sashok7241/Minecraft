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
	
	private WorldType(int par1, String par2Str)
	{
		this(par1, par2Str, 0);
	}
	
	private WorldType(int par1, String par2Str, int par3)
	{
		worldType = par2Str;
		generatorVersion = par3;
		canBeCreated = true;
		worldTypeId = par1;
		worldTypes[par1] = this;
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
	
	public WorldType getWorldTypeForGeneratorVersion(int par1)
	{
		return this == DEFAULT && par1 == 0 ? DEFAULT_1_1 : this;
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
	
	private WorldType setCanBeCreated(boolean par1)
	{
		canBeCreated = par1;
		return this;
	}
	
	private WorldType setVersioned()
	{
		isWorldTypeVersioned = true;
		return this;
	}
	
	public static WorldType parseWorldType(String par0Str)
	{
		for(WorldType worldType2 : worldTypes)
		{
			if(worldType2 != null && worldType2.worldType.equalsIgnoreCase(par0Str)) return worldType2;
		}
		return null;
	}
}
