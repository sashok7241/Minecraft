package net.minecraft.src;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenVillage extends MapGenStructure
{
	public static final List villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.plains, BiomeGenBase.desert });
	private int terrainType;
	private int field_82665_g;
	private int field_82666_h;
	
	public MapGenVillage()
	{
		terrainType = 0;
		field_82665_g = 32;
		field_82666_h = 8;
	}
	
	public MapGenVillage(Map p_i5097_1_)
	{
		this();
		Iterator var2 = p_i5097_1_.entrySet().iterator();
		while(var2.hasNext())
		{
			Entry var3 = (Entry) var2.next();
			if(((String) var3.getKey()).equals("size"))
			{
				terrainType = MathHelper.parseIntWithDefaultAndMax((String) var3.getValue(), terrainType, 0);
			} else if(((String) var3.getKey()).equals("distance"))
			{
				field_82665_g = MathHelper.parseIntWithDefaultAndMax((String) var3.getValue(), field_82665_g, field_82666_h + 1);
			}
		}
	}
	
	@Override protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
	{
		int var3 = p_75047_1_;
		int var4 = p_75047_2_;
		if(p_75047_1_ < 0)
		{
			p_75047_1_ -= field_82665_g - 1;
		}
		if(p_75047_2_ < 0)
		{
			p_75047_2_ -= field_82665_g - 1;
		}
		int var5 = p_75047_1_ / field_82665_g;
		int var6 = p_75047_2_ / field_82665_g;
		Random var7 = worldObj.setRandomSeed(var5, var6, 10387312);
		var5 *= field_82665_g;
		var6 *= field_82665_g;
		var5 += var7.nextInt(field_82665_g - field_82666_h);
		var6 += var7.nextInt(field_82665_g - field_82666_h);
		if(var3 == var5 && var4 == var6)
		{
			boolean var8 = worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 0, villageSpawnBiomes);
			if(var8) return true;
		}
		return false;
	}
	
	@Override protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
	{
		return new StructureVillageStart(worldObj, rand, p_75049_1_, p_75049_2_, terrainType);
	}
}
