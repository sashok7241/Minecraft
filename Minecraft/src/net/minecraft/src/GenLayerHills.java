package net.minecraft.src;

public class GenLayerHills extends GenLayer
{
	public GenLayerHills(long p_i3892_1_, GenLayer p_i3892_3_)
	{
		super(p_i3892_1_);
		parent = p_i3892_3_;
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
				if(nextInt(3) == 0)
				{
					int var10 = var9;
					if(var9 == BiomeGenBase.desert.biomeID)
					{
						var10 = BiomeGenBase.desertHills.biomeID;
					} else if(var9 == BiomeGenBase.forest.biomeID)
					{
						var10 = BiomeGenBase.forestHills.biomeID;
					} else if(var9 == BiomeGenBase.taiga.biomeID)
					{
						var10 = BiomeGenBase.taigaHills.biomeID;
					} else if(var9 == BiomeGenBase.plains.biomeID)
					{
						var10 = BiomeGenBase.forest.biomeID;
					} else if(var9 == BiomeGenBase.icePlains.biomeID)
					{
						var10 = BiomeGenBase.iceMountains.biomeID;
					} else if(var9 == BiomeGenBase.jungle.biomeID)
					{
						var10 = BiomeGenBase.jungleHills.biomeID;
					}
					if(var10 == var9)
					{
						var6[var8 + var7 * p_75904_3_] = var9;
					} else
					{
						int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
						int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
						int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
						int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
						if(var11 == var9 && var12 == var9 && var13 == var9 && var14 == var9)
						{
							var6[var8 + var7 * p_75904_3_] = var10;
						} else
						{
							var6[var8 + var7 * p_75904_3_] = var9;
						}
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
