package net.minecraft.src;

import java.util.Random;

public class WorldGenHugeTrees extends WorldGenerator
{
	private final int baseHeight;
	private final int woodMetadata;
	private final int leavesMetadata;
	
	public WorldGenHugeTrees(boolean p_i3795_1_, int p_i3795_2_, int p_i3795_3_, int p_i3795_4_)
	{
		super(p_i3795_1_);
		baseHeight = p_i3795_2_;
		woodMetadata = p_i3795_3_;
		leavesMetadata = p_i3795_4_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var6 = p_76484_2_.nextInt(3) + baseHeight;
		boolean var7 = true;
		if(p_76484_4_ >= 1 && p_76484_4_ + var6 + 1 <= 256)
		{
			int var8;
			int var10;
			int var11;
			int var12;
			for(var8 = p_76484_4_; var8 <= p_76484_4_ + 1 + var6; ++var8)
			{
				byte var9 = 2;
				if(var8 == p_76484_4_)
				{
					var9 = 1;
				}
				if(var8 >= p_76484_4_ + 1 + var6 - 2)
				{
					var9 = 2;
				}
				for(var10 = p_76484_3_ - var9; var10 <= p_76484_3_ + var9 && var7; ++var10)
				{
					for(var11 = p_76484_5_ - var9; var11 <= p_76484_5_ + var9 && var7; ++var11)
					{
						if(var8 >= 0 && var8 < 256)
						{
							var12 = p_76484_1_.getBlockId(var10, var8, var11);
							if(var12 != 0 && var12 != Block.leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != Block.wood.blockID && var12 != Block.sapling.blockID)
							{
								var7 = false;
							}
						} else
						{
							var7 = false;
						}
					}
				}
			}
			if(!var7) return false;
			else
			{
				var8 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
				if((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && p_76484_4_ < 256 - var6 - 1)
				{
					p_76484_1_.setBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.dirt.blockID, 0, 2);
					p_76484_1_.setBlock(p_76484_3_ + 1, p_76484_4_ - 1, p_76484_5_, Block.dirt.blockID, 0, 2);
					p_76484_1_.setBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_ + 1, Block.dirt.blockID, 0, 2);
					p_76484_1_.setBlock(p_76484_3_ + 1, p_76484_4_ - 1, p_76484_5_ + 1, Block.dirt.blockID, 0, 2);
					growLeaves(p_76484_1_, p_76484_3_, p_76484_5_, p_76484_4_ + var6, 2, p_76484_2_);
					for(int var14 = p_76484_4_ + var6 - 2 - p_76484_2_.nextInt(4); var14 > p_76484_4_ + var6 / 2; var14 -= 2 + p_76484_2_.nextInt(4))
					{
						float var15 = p_76484_2_.nextFloat() * (float) Math.PI * 2.0F;
						var11 = p_76484_3_ + (int) (0.5F + MathHelper.cos(var15) * 4.0F);
						var12 = p_76484_5_ + (int) (0.5F + MathHelper.sin(var15) * 4.0F);
						growLeaves(p_76484_1_, var11, var12, var14, 0, p_76484_2_);
						for(int var13 = 0; var13 < 5; ++var13)
						{
							var11 = p_76484_3_ + (int) (1.5F + MathHelper.cos(var15) * var13);
							var12 = p_76484_5_ + (int) (1.5F + MathHelper.sin(var15) * var13);
							setBlockAndMetadata(p_76484_1_, var11, var14 - 3 + var13 / 2, var12, Block.wood.blockID, woodMetadata);
						}
					}
					for(var10 = 0; var10 < var6; ++var10)
					{
						var11 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + var10, p_76484_5_);
						if(var11 == 0 || var11 == Block.leaves.blockID)
						{
							setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var10, p_76484_5_, Block.wood.blockID, woodMetadata);
							if(var10 > 0)
							{
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_ + var10, p_76484_5_))
								{
									setBlockAndMetadata(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + var10, p_76484_5_, Block.vine.blockID, 8);
								}
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + var10, p_76484_5_ - 1))
								{
									setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var10, p_76484_5_ - 1, Block.vine.blockID, 1);
								}
							}
						}
						if(var10 < var6 - 1)
						{
							var11 = p_76484_1_.getBlockId(p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_);
							if(var11 == 0 || var11 == Block.leaves.blockID)
							{
								setBlockAndMetadata(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_, Block.wood.blockID, woodMetadata);
								if(var10 > 0)
								{
									if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 2, p_76484_4_ + var10, p_76484_5_))
									{
										setBlockAndMetadata(p_76484_1_, p_76484_3_ + 2, p_76484_4_ + var10, p_76484_5_, Block.vine.blockID, 2);
									}
									if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_ - 1))
									{
										setBlockAndMetadata(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_ - 1, Block.vine.blockID, 1);
									}
								}
							}
							var11 = p_76484_1_.getBlockId(p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_ + 1);
							if(var11 == 0 || var11 == Block.leaves.blockID)
							{
								setBlockAndMetadata(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_ + 1, Block.wood.blockID, woodMetadata);
								if(var10 > 0)
								{
									if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 2, p_76484_4_ + var10, p_76484_5_ + 1))
									{
										setBlockAndMetadata(p_76484_1_, p_76484_3_ + 2, p_76484_4_ + var10, p_76484_5_ + 1, Block.vine.blockID, 2);
									}
									if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_ + 2))
									{
										setBlockAndMetadata(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + var10, p_76484_5_ + 2, Block.vine.blockID, 4);
									}
								}
							}
							var11 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + var10, p_76484_5_ + 1);
							if(var11 == 0 || var11 == Block.leaves.blockID)
							{
								setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var10, p_76484_5_ + 1, Block.wood.blockID, woodMetadata);
								if(var10 > 0)
								{
									if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_ + var10, p_76484_5_ + 1))
									{
										setBlockAndMetadata(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + var10, p_76484_5_ + 1, Block.vine.blockID, 8);
									}
									if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + var10, p_76484_5_ + 2))
									{
										setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var10, p_76484_5_ + 2, Block.vine.blockID, 4);
									}
								}
							}
						}
					}
					return true;
				} else return false;
			}
		} else return false;
	}
	
	private void growLeaves(World p_76519_1_, int p_76519_2_, int p_76519_3_, int p_76519_4_, int p_76519_5_, Random p_76519_6_)
	{
		byte var7 = 2;
		for(int var8 = p_76519_4_ - var7; var8 <= p_76519_4_; ++var8)
		{
			int var9 = var8 - p_76519_4_;
			int var10 = p_76519_5_ + 1 - var9;
			for(int var11 = p_76519_2_ - var10; var11 <= p_76519_2_ + var10 + 1; ++var11)
			{
				int var12 = var11 - p_76519_2_;
				for(int var13 = p_76519_3_ - var10; var13 <= p_76519_3_ + var10 + 1; ++var13)
				{
					int var14 = var13 - p_76519_3_;
					if((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && (var12 <= 0 && var14 <= 0 || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1)) && (p_76519_6_.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1)))
					{
						int var15 = p_76519_1_.getBlockId(var11, var8, var13);
						if(var15 == 0 || var15 == Block.leaves.blockID)
						{
							setBlockAndMetadata(p_76519_1_, var11, var8, var13, Block.leaves.blockID, leavesMetadata);
						}
					}
				}
			}
		}
	}
}
