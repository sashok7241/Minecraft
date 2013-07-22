package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenScatteredFeature extends MapGenStructure
{
	private static List biomelist = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland });
	private List scatteredFeatureSpawnList;
	private int maxDistanceBetweenScatteredFeatures;
	private int minDistanceBetweenScatteredFeatures;
	
	public MapGenScatteredFeature()
	{
		scatteredFeatureSpawnList = new ArrayList();
		maxDistanceBetweenScatteredFeatures = 32;
		minDistanceBetweenScatteredFeatures = 8;
		scatteredFeatureSpawnList.add(new SpawnListEntry(EntityWitch.class, 1, 1, 1));
	}
	
	public MapGenScatteredFeature(Map par1Map)
	{
		this();
		Iterator var2 = par1Map.entrySet().iterator();
		while(var2.hasNext())
		{
			Entry var3 = (Entry) var2.next();
			if(((String) var3.getKey()).equals("distance"))
			{
				maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String) var3.getValue(), maxDistanceBetweenScatteredFeatures, minDistanceBetweenScatteredFeatures + 1);
			}
		}
	}
	
	@Override protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		int var3 = par1;
		int var4 = par2;
		if(par1 < 0)
		{
			par1 -= maxDistanceBetweenScatteredFeatures - 1;
		}
		if(par2 < 0)
		{
			par2 -= maxDistanceBetweenScatteredFeatures - 1;
		}
		int var5 = par1 / maxDistanceBetweenScatteredFeatures;
		int var6 = par2 / maxDistanceBetweenScatteredFeatures;
		Random var7 = worldObj.setRandomSeed(var5, var6, 14357617);
		var5 *= maxDistanceBetweenScatteredFeatures;
		var6 *= maxDistanceBetweenScatteredFeatures;
		var5 += var7.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);
		var6 += var7.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);
		if(var3 == var5 && var4 == var6)
		{
			BiomeGenBase var8 = worldObj.getWorldChunkManager().getBiomeGenAt(var3 * 16 + 8, var4 * 16 + 8);
			Iterator var9 = biomelist.iterator();
			while(var9.hasNext())
			{
				BiomeGenBase var10 = (BiomeGenBase) var9.next();
				if(var8 == var10) return true;
			}
		}
		return false;
	}
	
	public List getScatteredFeatureSpawnList()
	{
		return scatteredFeatureSpawnList;
	}
	
	@Override protected StructureStart getStructureStart(int par1, int par2)
	{
		return new StructureScatteredFeatureStart(worldObj, rand, par1, par2);
	}
}
