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
	
	public MapGenScatteredFeature(Map p_i5094_1_)
	{
		this();
		Iterator var2 = p_i5094_1_.entrySet().iterator();
		while(var2.hasNext())
		{
			Entry var3 = (Entry) var2.next();
			if(((String) var3.getKey()).equals("distance"))
			{
				maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String) var3.getValue(), maxDistanceBetweenScatteredFeatures, minDistanceBetweenScatteredFeatures + 1);
			}
		}
	}
	
	@Override protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
	{
		int var3 = p_75047_1_;
		int var4 = p_75047_2_;
		if(p_75047_1_ < 0)
		{
			p_75047_1_ -= maxDistanceBetweenScatteredFeatures - 1;
		}
		if(p_75047_2_ < 0)
		{
			p_75047_2_ -= maxDistanceBetweenScatteredFeatures - 1;
		}
		int var5 = p_75047_1_ / maxDistanceBetweenScatteredFeatures;
		int var6 = p_75047_2_ / maxDistanceBetweenScatteredFeatures;
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
	
	@Override protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
	{
		return new StructureScatteredFeatureStart(worldObj, rand, p_75049_1_, p_75049_2_);
	}
}
