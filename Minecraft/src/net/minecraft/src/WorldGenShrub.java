package net.minecraft.src;

import java.util.Random;

public class WorldGenShrub extends WorldGenerator
{
	private int field_76527_a;
	private int field_76526_b;
	
	public WorldGenShrub(int p_i3791_1_, int p_i3791_2_)
	{
		field_76526_b = p_i3791_1_;
		field_76527_a = p_i3791_2_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var15;
		for(boolean var6 = false; ((var15 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_)) == 0 || var15 == Block.leaves.blockID) && p_76484_4_ > 0; --p_76484_4_)
		{
			;
		}
		int var7 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_);
		if(var7 == Block.dirt.blockID || var7 == Block.grass.blockID)
		{
			++p_76484_4_;
			setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_, p_76484_5_, Block.wood.blockID, field_76526_b);
			for(int var8 = p_76484_4_; var8 <= p_76484_4_ + 2; ++var8)
			{
				int var9 = var8 - p_76484_4_;
				int var10 = 2 - var9;
				for(int var11 = p_76484_3_ - var10; var11 <= p_76484_3_ + var10; ++var11)
				{
					int var12 = var11 - p_76484_3_;
					for(int var13 = p_76484_5_ - var10; var13 <= p_76484_5_ + var10; ++var13)
					{
						int var14 = var13 - p_76484_5_;
						if((Math.abs(var12) != var10 || Math.abs(var14) != var10 || p_76484_2_.nextInt(2) != 0) && !Block.opaqueCubeLookup[p_76484_1_.getBlockId(var11, var8, var13)])
						{
							setBlockAndMetadata(p_76484_1_, var11, var8, var13, Block.leaves.blockID, field_76527_a);
						}
					}
				}
			}
		}
		return true;
	}
}
