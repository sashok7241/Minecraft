package net.minecraft.src;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenerator
{
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var6 = p_76484_2_.nextInt(5) + 7;
		int var7 = var6 - p_76484_2_.nextInt(2) - 3;
		int var8 = var6 - var7;
		int var9 = 1 + p_76484_2_.nextInt(var8 + 1);
		boolean var10 = true;
		if(p_76484_4_ >= 1 && p_76484_4_ + var6 + 1 <= 128)
		{
			int var11;
			int var13;
			int var14;
			int var15;
			int var18;
			for(var11 = p_76484_4_; var11 <= p_76484_4_ + 1 + var6 && var10; ++var11)
			{
				boolean var12 = true;
				if(var11 - p_76484_4_ < var7)
				{
					var18 = 0;
				} else
				{
					var18 = var9;
				}
				for(var13 = p_76484_3_ - var18; var13 <= p_76484_3_ + var18 && var10; ++var13)
				{
					for(var14 = p_76484_5_ - var18; var14 <= p_76484_5_ + var18 && var10; ++var14)
					{
						if(var11 >= 0 && var11 < 128)
						{
							var15 = p_76484_1_.getBlockId(var13, var11, var14);
							if(var15 != 0 && var15 != Block.leaves.blockID)
							{
								var10 = false;
							}
						} else
						{
							var10 = false;
						}
					}
				}
			}
			if(!var10) return false;
			else
			{
				var11 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
				if((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && p_76484_4_ < 128 - var6 - 1)
				{
					setBlock(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.dirt.blockID);
					var18 = 0;
					for(var13 = p_76484_4_ + var6; var13 >= p_76484_4_ + var7; --var13)
					{
						for(var14 = p_76484_3_ - var18; var14 <= p_76484_3_ + var18; ++var14)
						{
							var15 = var14 - p_76484_3_;
							for(int var16 = p_76484_5_ - var18; var16 <= p_76484_5_ + var18; ++var16)
							{
								int var17 = var16 - p_76484_5_;
								if((Math.abs(var15) != var18 || Math.abs(var17) != var18 || var18 <= 0) && !Block.opaqueCubeLookup[p_76484_1_.getBlockId(var14, var13, var16)])
								{
									setBlockAndMetadata(p_76484_1_, var14, var13, var16, Block.leaves.blockID, 1);
								}
							}
						}
						if(var18 >= 1 && var13 == p_76484_4_ + var7 + 1)
						{
							--var18;
						} else if(var18 < var9)
						{
							++var18;
						}
					}
					for(var13 = 0; var13 < var6 - 1; ++var13)
					{
						var14 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + var13, p_76484_5_);
						if(var14 == 0 || var14 == Block.leaves.blockID)
						{
							setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var13, p_76484_5_, Block.wood.blockID, 1);
						}
					}
					return true;
				} else return false;
			}
		} else return false;
	}
}
