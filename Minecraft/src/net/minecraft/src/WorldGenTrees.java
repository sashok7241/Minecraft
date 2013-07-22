package net.minecraft.src;

import java.util.Random;

public class WorldGenTrees extends WorldGenerator
{
	private final int minTreeHeight;
	private final boolean vinesGrow;
	private final int metaWood;
	private final int metaLeaves;
	
	public WorldGenTrees(boolean p_i3802_1_)
	{
		this(p_i3802_1_, 4, 0, 0, false);
	}
	
	public WorldGenTrees(boolean p_i3803_1_, int p_i3803_2_, int p_i3803_3_, int p_i3803_4_, boolean p_i3803_5_)
	{
		super(p_i3803_1_);
		minTreeHeight = p_i3803_2_;
		metaWood = p_i3803_3_;
		metaLeaves = p_i3803_4_;
		vinesGrow = p_i3803_5_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var6 = p_76484_2_.nextInt(3) + minTreeHeight;
		boolean var7 = true;
		if(p_76484_4_ >= 1 && p_76484_4_ + var6 + 1 <= 256)
		{
			int var8;
			byte var9;
			int var11;
			int var12;
			for(var8 = p_76484_4_; var8 <= p_76484_4_ + 1 + var6; ++var8)
			{
				var9 = 1;
				if(var8 == p_76484_4_)
				{
					var9 = 0;
				}
				if(var8 >= p_76484_4_ + 1 + var6 - 2)
				{
					var9 = 2;
				}
				for(int var10 = p_76484_3_ - var9; var10 <= p_76484_3_ + var9 && var7; ++var10)
				{
					for(var11 = p_76484_5_ - var9; var11 <= p_76484_5_ + var9 && var7; ++var11)
					{
						if(var8 >= 0 && var8 < 256)
						{
							var12 = p_76484_1_.getBlockId(var10, var8, var11);
							if(var12 != 0 && var12 != Block.leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != Block.wood.blockID)
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
					setBlock(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.dirt.blockID);
					var9 = 3;
					byte var19 = 0;
					int var13;
					int var14;
					int var15;
					for(var11 = p_76484_4_ - var9 + var6; var11 <= p_76484_4_ + var6; ++var11)
					{
						var12 = var11 - (p_76484_4_ + var6);
						var13 = var19 + 1 - var12 / 2;
						for(var14 = p_76484_3_ - var13; var14 <= p_76484_3_ + var13; ++var14)
						{
							var15 = var14 - p_76484_3_;
							for(int var16 = p_76484_5_ - var13; var16 <= p_76484_5_ + var13; ++var16)
							{
								int var17 = var16 - p_76484_5_;
								if(Math.abs(var15) != var13 || Math.abs(var17) != var13 || p_76484_2_.nextInt(2) != 0 && var12 != 0)
								{
									int var18 = p_76484_1_.getBlockId(var14, var11, var16);
									if(var18 == 0 || var18 == Block.leaves.blockID)
									{
										setBlockAndMetadata(p_76484_1_, var14, var11, var16, Block.leaves.blockID, metaLeaves);
									}
								}
							}
						}
					}
					for(var11 = 0; var11 < var6; ++var11)
					{
						var12 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + var11, p_76484_5_);
						if(var12 == 0 || var12 == Block.leaves.blockID)
						{
							setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_, Block.wood.blockID, metaWood);
							if(vinesGrow && var11 > 0)
							{
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_ + var11, p_76484_5_))
								{
									setBlockAndMetadata(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + var11, p_76484_5_, Block.vine.blockID, 8);
								}
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_ + var11, p_76484_5_))
								{
									setBlockAndMetadata(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + var11, p_76484_5_, Block.vine.blockID, 2);
								}
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + var11, p_76484_5_ - 1))
								{
									setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_ - 1, Block.vine.blockID, 1);
								}
								if(p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + var11, p_76484_5_ + 1))
								{
									setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_ + 1, Block.vine.blockID, 4);
								}
							}
						}
					}
					if(vinesGrow)
					{
						for(var11 = p_76484_4_ - 3 + var6; var11 <= p_76484_4_ + var6; ++var11)
						{
							var12 = var11 - (p_76484_4_ + var6);
							var13 = 2 - var12 / 2;
							for(var14 = p_76484_3_ - var13; var14 <= p_76484_3_ + var13; ++var14)
							{
								for(var15 = p_76484_5_ - var13; var15 <= p_76484_5_ + var13; ++var15)
								{
									if(p_76484_1_.getBlockId(var14, var11, var15) == Block.leaves.blockID)
									{
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlockId(var14 - 1, var11, var15) == 0)
										{
											growVines(p_76484_1_, var14 - 1, var11, var15, 8);
										}
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlockId(var14 + 1, var11, var15) == 0)
										{
											growVines(p_76484_1_, var14 + 1, var11, var15, 2);
										}
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlockId(var14, var11, var15 - 1) == 0)
										{
											growVines(p_76484_1_, var14, var11, var15 - 1, 1);
										}
										if(p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlockId(var14, var11, var15 + 1) == 0)
										{
											growVines(p_76484_1_, var14, var11, var15 + 1, 4);
										}
									}
								}
							}
						}
						if(p_76484_2_.nextInt(5) == 0 && var6 > 5)
						{
							for(var11 = 0; var11 < 2; ++var11)
							{
								for(var12 = 0; var12 < 4; ++var12)
								{
									if(p_76484_2_.nextInt(4 - var11) == 0)
									{
										var13 = p_76484_2_.nextInt(3);
										setBlockAndMetadata(p_76484_1_, p_76484_3_ + Direction.offsetX[Direction.rotateOpposite[var12]], p_76484_4_ + var6 - 5 + var11, p_76484_5_ + Direction.offsetZ[Direction.rotateOpposite[var12]], Block.cocoaPlant.blockID, var13 << 2 | var12);
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
	
	private void growVines(World p_76529_1_, int p_76529_2_, int p_76529_3_, int p_76529_4_, int p_76529_5_)
	{
		setBlockAndMetadata(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Block.vine.blockID, p_76529_5_);
		int var6 = 4;
		while(true)
		{
			--p_76529_3_;
			if(p_76529_1_.getBlockId(p_76529_2_, p_76529_3_, p_76529_4_) != 0 || var6 <= 0) return;
			setBlockAndMetadata(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Block.vine.blockID, p_76529_5_);
			--var6;
		}
	}
}
