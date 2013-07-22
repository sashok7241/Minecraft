package net.minecraft.src;

import java.util.Random;

public class WorldGenVines extends WorldGenerator
{
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var6 = p_76484_3_;
		for(int var7 = p_76484_5_; p_76484_4_ < 128; ++p_76484_4_)
		{
			if(p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_))
			{
				for(int var8 = 2; var8 <= 5; ++var8)
				{
					if(Block.vine.canPlaceBlockOnSide(p_76484_1_, p_76484_3_, p_76484_4_, p_76484_5_, var8))
					{
						p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, Block.vine.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 2);
						break;
					}
				}
			} else
			{
				p_76484_3_ = var6 + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
				p_76484_5_ = var7 + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
			}
		}
		return true;
	}
}