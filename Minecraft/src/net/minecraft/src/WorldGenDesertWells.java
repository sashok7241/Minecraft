package net.minecraft.src;

import java.util.Random;

public class WorldGenDesertWells extends WorldGenerator
{
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		while(p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_) && p_76484_4_ > 2)
		{
			--p_76484_4_;
		}
		int var6 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_);
		if(var6 != Block.sand.blockID) return false;
		else
		{
			int var7;
			int var8;
			for(var7 = -2; var7 <= 2; ++var7)
			{
				for(var8 = -2; var8 <= 2; ++var8)
				{
					if(p_76484_1_.isAirBlock(p_76484_3_ + var7, p_76484_4_ - 1, p_76484_5_ + var8) && p_76484_1_.isAirBlock(p_76484_3_ + var7, p_76484_4_ - 2, p_76484_5_ + var8)) return false;
				}
			}
			for(var7 = -1; var7 <= 0; ++var7)
			{
				for(var8 = -2; var8 <= 2; ++var8)
				{
					for(int var9 = -2; var9 <= 2; ++var9)
					{
						p_76484_1_.setBlock(p_76484_3_ + var8, p_76484_4_ + var7, p_76484_5_ + var9, Block.sandStone.blockID, 0, 2);
					}
				}
			}
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, Block.waterMoving.blockID, 0, 2);
			p_76484_1_.setBlock(p_76484_3_ - 1, p_76484_4_, p_76484_5_, Block.waterMoving.blockID, 0, 2);
			p_76484_1_.setBlock(p_76484_3_ + 1, p_76484_4_, p_76484_5_, Block.waterMoving.blockID, 0, 2);
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_ - 1, Block.waterMoving.blockID, 0, 2);
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_ + 1, Block.waterMoving.blockID, 0, 2);
			for(var7 = -2; var7 <= 2; ++var7)
			{
				for(var8 = -2; var8 <= 2; ++var8)
				{
					if(var7 == -2 || var7 == 2 || var8 == -2 || var8 == 2)
					{
						p_76484_1_.setBlock(p_76484_3_ + var7, p_76484_4_ + 1, p_76484_5_ + var8, Block.sandStone.blockID, 0, 2);
					}
				}
			}
			p_76484_1_.setBlock(p_76484_3_ + 2, p_76484_4_ + 1, p_76484_5_, Block.stoneSingleSlab.blockID, 1, 2);
			p_76484_1_.setBlock(p_76484_3_ - 2, p_76484_4_ + 1, p_76484_5_, Block.stoneSingleSlab.blockID, 1, 2);
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_ + 1, p_76484_5_ + 2, Block.stoneSingleSlab.blockID, 1, 2);
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_ + 1, p_76484_5_ - 2, Block.stoneSingleSlab.blockID, 1, 2);
			for(var7 = -1; var7 <= 1; ++var7)
			{
				for(var8 = -1; var8 <= 1; ++var8)
				{
					if(var7 == 0 && var8 == 0)
					{
						p_76484_1_.setBlock(p_76484_3_ + var7, p_76484_4_ + 4, p_76484_5_ + var8, Block.sandStone.blockID, 0, 2);
					} else
					{
						p_76484_1_.setBlock(p_76484_3_ + var7, p_76484_4_ + 4, p_76484_5_ + var8, Block.stoneSingleSlab.blockID, 1, 2);
					}
				}
			}
			for(var7 = 1; var7 <= 3; ++var7)
			{
				p_76484_1_.setBlock(p_76484_3_ - 1, p_76484_4_ + var7, p_76484_5_ - 1, Block.sandStone.blockID, 0, 2);
				p_76484_1_.setBlock(p_76484_3_ - 1, p_76484_4_ + var7, p_76484_5_ + 1, Block.sandStone.blockID, 0, 2);
				p_76484_1_.setBlock(p_76484_3_ + 1, p_76484_4_ + var7, p_76484_5_ - 1, Block.sandStone.blockID, 0, 2);
				p_76484_1_.setBlock(p_76484_3_ + 1, p_76484_4_ + var7, p_76484_5_ + 1, Block.sandStone.blockID, 0, 2);
			}
			return true;
		}
	}
}
