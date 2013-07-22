package net.minecraft.src;

public class GenLayerBiome extends GenLayer
{
	private BiomeGenBase[] allowedBiomes;
	
	public GenLayerBiome(long p_i3888_1_, GenLayer p_i3888_3_, WorldType p_i3888_4_)
	{
		super(p_i3888_1_);
		allowedBiomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga, BiomeGenBase.jungle };
		parent = p_i3888_3_;
		if(p_i3888_4_ == WorldType.DEFAULT_1_1)
		{
			allowedBiomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };
		}
	}
	
	@Override public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
	{
		int[] var5 = parent.getInts(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
		int[] var6 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
		for(int var7 = 0; var7 < p_75904_4_; ++var7)
		{
			for(int var8 = 0; var8 < p_75904_3_; ++var8)
			{
				initChunkSeed(var8 + p_75904_1_, var7 + p_75904_2_);
				int var9 = var5[var8 + var7 * p_75904_3_];
				if(var9 == 0)
				{
					var6[var8 + var7 * p_75904_3_] = 0;
				} else if(var9 == BiomeGenBase.mushroomIsland.biomeID)
				{
					var6[var8 + var7 * p_75904_3_] = var9;
				} else if(var9 == 1)
				{
					var6[var8 + var7 * p_75904_3_] = allowedBiomes[nextInt(allowedBiomes.length)].biomeID;
				} else
				{
					int var10 = allowedBiomes[nextInt(allowedBiomes.length)].biomeID;
					if(var10 == BiomeGenBase.taiga.biomeID)
					{
						var6[var8 + var7 * p_75904_3_] = var10;
					} else
					{
						var6[var8 + var7 * p_75904_3_] = BiomeGenBase.icePlains.biomeID;
					}
				}
			}
		}
		return var6;
	}
}
