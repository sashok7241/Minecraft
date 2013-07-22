package net.minecraft.src;

public class GenLayerSwampRivers extends GenLayer
{
	public GenLayerSwampRivers(long p_i3898_1_, GenLayer p_i3898_3_)
	{
		super(p_i3898_1_);
		parent = p_i3898_3_;
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
				if((var9 != BiomeGenBase.swampland.biomeID || nextInt(6) != 0) && (var9 != BiomeGenBase.jungle.biomeID && var9 != BiomeGenBase.jungleHills.biomeID || nextInt(8) != 0))
				{
					var6[var8 + var7 * p_75904_3_] = var9;
				} else
				{
					var6[var8 + var7 * p_75904_3_] = BiomeGenBase.river.biomeID;
				}
			}
		}
		return var6;
	}
}
