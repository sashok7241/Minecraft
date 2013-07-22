package net.minecraft.src;

import java.util.Random;

public class StructureScatteredFeatureStart extends StructureStart
{
	public StructureScatteredFeatureStart(World p_i3832_1_, Random p_i3832_2_, int p_i3832_3_, int p_i3832_4_)
	{
		BiomeGenBase var5 = p_i3832_1_.getBiomeGenForCoords(p_i3832_3_ * 16 + 8, p_i3832_4_ * 16 + 8);
		if(var5 != BiomeGenBase.jungle && var5 != BiomeGenBase.jungleHills)
		{
			if(var5 == BiomeGenBase.swampland)
			{
				ComponentScatteredFeatureSwampHut var8 = new ComponentScatteredFeatureSwampHut(p_i3832_2_, p_i3832_3_ * 16, p_i3832_4_ * 16);
				components.add(var8);
			} else
			{
				ComponentScatteredFeatureDesertPyramid var7 = new ComponentScatteredFeatureDesertPyramid(p_i3832_2_, p_i3832_3_ * 16, p_i3832_4_ * 16);
				components.add(var7);
			}
		} else
		{
			ComponentScatteredFeatureJunglePyramid var6 = new ComponentScatteredFeatureJunglePyramid(p_i3832_2_, p_i3832_3_ * 16, p_i3832_4_ * 16);
			components.add(var6);
		}
		updateBoundingBox();
	}
}
