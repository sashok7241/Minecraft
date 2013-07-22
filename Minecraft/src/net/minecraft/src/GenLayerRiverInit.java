package net.minecraft.src;

public class GenLayerRiverInit extends GenLayer
{
	public GenLayerRiverInit(long p_i3893_1_, GenLayer p_i3893_3_)
	{
		super(p_i3893_1_);
		parent = p_i3893_3_;
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
				var6[var8 + var7 * p_75904_3_] = var5[var8 + var7 * p_75904_3_] > 0 ? nextInt(2) + 2 : 0;
			}
		}
		return var6;
	}
}
