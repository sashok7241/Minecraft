package net.minecraft.src;

public class GenLayerShore extends GenLayer
{
	public GenLayerShore(long p_i3896_1_, GenLayer p_i3896_3_)
	{
		super(p_i3896_1_);
		parent = p_i3896_3_;
	}
	
	@Override public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
	{
		int[] var5 = parent.getInts(p_75904_1_ - 1, p_75904_2_ - 1, p_75904_3_ + 2, p_75904_4_ + 2);
		int[] var6 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
		for(int var7 = 0; var7 < p_75904_4_; ++var7)
		{
			for(int var8 = 0; var8 < p_75904_3_; ++var8)
			{
				initChunkSeed(var8 + p_75904_1_, var7 + p_75904_2_);
				int var9 = var5[var8 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
				int var10;
				int var11;
				int var12;
				int var13;
				if(var9 == BiomeGenBase.mushroomIsland.biomeID)
				{
					var10 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
					var11 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
					var12 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
					var13 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
					if(var10 != BiomeGenBase.ocean.biomeID && var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID)
					{
						var6[var8 + var7 * p_75904_3_] = var9;
					} else
					{
						var6[var8 + var7 * p_75904_3_] = BiomeGenBase.mushroomIslandShore.biomeID;
					}
				} else if(var9 != BiomeGenBase.ocean.biomeID && var9 != BiomeGenBase.river.biomeID && var9 != BiomeGenBase.swampland.biomeID && var9 != BiomeGenBase.extremeHills.biomeID)
				{
					var10 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
					var11 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
					var12 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
					var13 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
					if(var10 != BiomeGenBase.ocean.biomeID && var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID)
					{
						var6[var8 + var7 * p_75904_3_] = var9;
					} else
					{
						var6[var8 + var7 * p_75904_3_] = BiomeGenBase.beach.biomeID;
					}
				} else if(var9 == BiomeGenBase.extremeHills.biomeID)
				{
					var10 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
					var11 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
					var12 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
					var13 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
					if(var10 == BiomeGenBase.extremeHills.biomeID && var11 == BiomeGenBase.extremeHills.biomeID && var12 == BiomeGenBase.extremeHills.biomeID && var13 == BiomeGenBase.extremeHills.biomeID)
					{
						var6[var8 + var7 * p_75904_3_] = var9;
					} else
					{
						var6[var8 + var7 * p_75904_3_] = BiomeGenBase.extremeHillsEdge.biomeID;
					}
				} else
				{
					var6[var8 + var7 * p_75904_3_] = var9;
				}
			}
		}
		return var6;
	}
}
