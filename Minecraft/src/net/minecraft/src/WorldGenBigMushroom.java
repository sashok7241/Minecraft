package net.minecraft.src;

import java.util.Random;

public class WorldGenBigMushroom extends WorldGenerator
{
	private int mushroomType = -1;
	
	public WorldGenBigMushroom()
	{
		super(false);
	}
	
	public WorldGenBigMushroom(int p_i3793_1_)
	{
		super(true);
		mushroomType = p_i3793_1_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var6 = p_76484_2_.nextInt(2);
		if(mushroomType >= 0)
		{
			var6 = mushroomType;
		}
		int var7 = p_76484_2_.nextInt(3) + 4;
		boolean var8 = true;
		if(p_76484_4_ >= 1 && p_76484_4_ + var7 + 1 < 256)
		{
			int var9;
			int var11;
			int var12;
			int var13;
			for(var9 = p_76484_4_; var9 <= p_76484_4_ + 1 + var7; ++var9)
			{
				byte var10 = 3;
				if(var9 <= p_76484_4_ + 3)
				{
					var10 = 0;
				}
				for(var11 = p_76484_3_ - var10; var11 <= p_76484_3_ + var10 && var8; ++var11)
				{
					for(var12 = p_76484_5_ - var10; var12 <= p_76484_5_ + var10 && var8; ++var12)
					{
						if(var9 >= 0 && var9 < 256)
						{
							var13 = p_76484_1_.getBlockId(var11, var9, var12);
							if(var13 != 0 && var13 != Block.leaves.blockID)
							{
								var8 = false;
							}
						} else
						{
							var8 = false;
						}
					}
				}
			}
			if(!var8) return false;
			else
			{
				var9 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
				if(var9 != Block.dirt.blockID && var9 != Block.grass.blockID && var9 != Block.mycelium.blockID) return false;
				else
				{
					int var16 = p_76484_4_ + var7;
					if(var6 == 1)
					{
						var16 = p_76484_4_ + var7 - 3;
					}
					for(var11 = var16; var11 <= p_76484_4_ + var7; ++var11)
					{
						var12 = 1;
						if(var11 < p_76484_4_ + var7)
						{
							++var12;
						}
						if(var6 == 0)
						{
							var12 = 3;
						}
						for(var13 = p_76484_3_ - var12; var13 <= p_76484_3_ + var12; ++var13)
						{
							for(int var14 = p_76484_5_ - var12; var14 <= p_76484_5_ + var12; ++var14)
							{
								int var15 = 5;
								if(var13 == p_76484_3_ - var12)
								{
									--var15;
								}
								if(var13 == p_76484_3_ + var12)
								{
									++var15;
								}
								if(var14 == p_76484_5_ - var12)
								{
									var15 -= 3;
								}
								if(var14 == p_76484_5_ + var12)
								{
									var15 += 3;
								}
								if(var6 == 0 || var11 < p_76484_4_ + var7)
								{
									if((var13 == p_76484_3_ - var12 || var13 == p_76484_3_ + var12) && (var14 == p_76484_5_ - var12 || var14 == p_76484_5_ + var12))
									{
										continue;
									}
									if(var13 == p_76484_3_ - (var12 - 1) && var14 == p_76484_5_ - var12)
									{
										var15 = 1;
									}
									if(var13 == p_76484_3_ - var12 && var14 == p_76484_5_ - (var12 - 1))
									{
										var15 = 1;
									}
									if(var13 == p_76484_3_ + var12 - 1 && var14 == p_76484_5_ - var12)
									{
										var15 = 3;
									}
									if(var13 == p_76484_3_ + var12 && var14 == p_76484_5_ - (var12 - 1))
									{
										var15 = 3;
									}
									if(var13 == p_76484_3_ - (var12 - 1) && var14 == p_76484_5_ + var12)
									{
										var15 = 7;
									}
									if(var13 == p_76484_3_ - var12 && var14 == p_76484_5_ + var12 - 1)
									{
										var15 = 7;
									}
									if(var13 == p_76484_3_ + var12 - 1 && var14 == p_76484_5_ + var12)
									{
										var15 = 9;
									}
									if(var13 == p_76484_3_ + var12 && var14 == p_76484_5_ + var12 - 1)
									{
										var15 = 9;
									}
								}
								if(var15 == 5 && var11 < p_76484_4_ + var7)
								{
									var15 = 0;
								}
								if((var15 != 0 || p_76484_4_ >= p_76484_4_ + var7 - 1) && !Block.opaqueCubeLookup[p_76484_1_.getBlockId(var13, var11, var14)])
								{
									setBlockAndMetadata(p_76484_1_, var13, var11, var14, Block.mushroomCapBrown.blockID + var6, var15);
								}
							}
						}
					}
					for(var11 = 0; var11 < var7; ++var11)
					{
						var12 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + var11, p_76484_5_);
						if(!Block.opaqueCubeLookup[var12])
						{
							setBlockAndMetadata(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_, Block.mushroomCapBrown.blockID + var6, 10);
						}
					}
					return true;
				}
			}
		} else return false;
	}
}
