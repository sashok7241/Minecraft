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
	
	public MapGenVillage(Map par1Map)
	{
		this();
		Iterator var2 = par1Map.entrySet().iterator();
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
	
	@Override protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		int var3 = par1;
		int var4 = par2;
		if(par1 < 0)
		{
			par1 -= field_82665_g - 1;
		}
		if(par2 < 0)
		{
			par2 -= field_82665_g - 1;
		}
		int var5 = par1 / field_82665_g;
		int var6 = par2 / field_82665_g;
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
	
	@Override protected StructureStart getStructureStart(int par1, int par2)
	{
		return new StructureVillageStart(worldObj, rand, par1, par2, terrainType);
	}
}
