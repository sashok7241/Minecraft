package net.minecraft.src;

import java.util.Random;

public class WorldGenTaiga2 extends WorldGenerator
{
	public WorldGenTaiga2(boolean p_i3800_1_)
	{
		super(p_i3800_1_);
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var6 = p_76484_2_.nextInt(4) + 6;
		int var7 = 1 + p_76484_2_.nextInt(2);
		int var8 = var6 - var7;
		int var9 = 2 + p_76484_2_.nextInt(2);
		boolean var10 = true;
		if(p_76484_4_ >= 1 && p_76484_4_ + var6 + 1 <= 256)
		{
			int var11;
			int var13;
			int var15;
			int var21;
			for(var11 = p_76484_4_; var11 <= p_76484_4_ + 1 + var6 && var10; ++var11)
			{
				boolean var12 = true;
				if(var11 - p_76484_4_ < var7)
				{
					var21 = 0;
				} else
				{
					var21 = var9;
				}
				for(var13 = p_76484_3_ - var21; var13 <= p_76484_3_ + var21 && var10; ++var13)
				{
					for(int var14 = p_76484_5_ - var21; var14 <= p_76484_5_ + var21 && var10; ++var14)
					{
						if(var11 >= 0 && var11 < 256)
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
				if((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && p_76484_4_ < 256 - var6 - 1)
				{
					setBlock(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.dirt.blockID);
					var21 = p_76484_2_.nextInt(2);
					var13 = 1;
					byte var22 = 0;
					int var17;
					int var16;
					for(var15 = 0; var15 <= var8; ++var15)
					{
						var16 = p_76484_4_ + var6 - var15;
						for(var17 = p_76484_3_ - var21; var17 <= p_76484_3_ + var21; ++var17)
						{
							int var18 = var17 - p_76484_3_;
							for(int var19 = p_76484_5_ - var21; var19 <= p_76484_5_ + var21; ++var19)
							{
								int var20 = var19 - p_76484_5_;
								if((Math.abs(var18) != var21 || Math.abs(var20) != var21 || var21 <= 0) && !Block.opaqueCubeLookup[p_76484_1_.getBlockId(var17, var16, var19)])
								{
									setBlockAndMetadata(p_76484_1_, var17, var16, var19, Block.leaves.blockID, 1);
								}
							}
						}
						if(var21 >= var13)
						{
							var21 = var22;
							var22 = 1;
							++var13;
							if(var13 > var9)
							{
								var13 = var9;
							}
						} else
						{
							++var21;
						}
					}
					var15 = p_76484_2_.nextInt(3);
					for(var16 = 0; var16 < var6 - var15; ++var16)
					{
						var17 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + var16, p_76484_5_);
						if(var17 == 0 || var17 == Block.leaves.blockID)
						{
							setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var16, p_76484_5_, Block.wood.blockID, 1);
						}
					}
					return true;
				} else return false;
			}
		} else return false;
	}
}
