package net.minecraft.src;

public class GenLayerFuzzyZoom extends GenLayer
{
	public GenLayerFuzzyZoom(long p_i3889_1_, GenLayer p_i3889_3_)
	{
		super(p_i3889_1_);
		super.parent = p_i3889_3_;
	}
	
	protected int choose(int p_75913_1_, int p_75913_2_)
	{
		return nextInt(2) == 0 ? p_75913_1_ : p_75913_2_;
	}
	
	protected int choose(int p_75912_1_, int p_75912_2_, int p_75912_3_, int p_75912_4_)
	{
		int var5 = nextInt(4);
		return var5 == 0 ? p_75912_1_ : var5 == 1 ? p_75912_2_ : var5 == 2 ? p_75912_3_ : p_75912_4_;
	}
	
	@Override public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
	{
		int var5 = p_75904_1_ >> 1;
		int var6 = p_75904_2_ >> 1;
		int var7 = (p_75904_3_ >> 1) + 3;
		int var8 = (p_75904_4_ >> 1) + 3;
		int[] var9 = parent.getInts(var5, var6, var7, var8);
		int[] var10 = IntCache.getIntCache(var7 * 2 * var8 * 2);
		int var11 = var7 << 1;
		int var13;
		for(int var12 = 0; var12 < var8 - 1; ++var12)
		{
			var13 = var12 << 1;
			int var14 = var13 * var11;
			int var15 = var9[0 + (var12 + 0) * var7];
			int var16 = var9[0 + (var12 + 1) * var7];
			for(int var17 = 0; var17 < var7 - 1; ++var17)
			{
				initChunkSeed(var17 + var5 << 1, var12 + var6 << 1);
				int var18 = var9[var17 + 1 + (var12 + 0) * var7];
				int var19 = var9[var17 + 1 + (var12 + 1) * var7];
				var10[var14] = var15;
				var10[var14++ + var11] = this.choose(var15, var16);
				var10[var14] = this.choose(var15, var18);
				var10[var14++ + var11] = this.choose(var15, var18, var16, var19);
				var15 = var18;
				var16 = var19;
			}
		}
		int[] var20 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
		for(var13 = 0; var13 < p_75904_4_; ++var13)
		{
			System.arraycopy(var10, (var13 + (p_75904_2_ & 1)) * (var7 << 1) + (p_75904_1_ & 1), var20, var13 * p_75904_3_, p_75904_3_);
		}
		return var20;
	}
}
