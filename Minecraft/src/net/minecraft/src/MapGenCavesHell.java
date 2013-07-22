package net.minecraft.src;

import java.util.Random;

public class MapGenCavesHell extends MapGenBase
{
	protected void generateCaveNode(long p_75043_1_, int p_75043_3_, int p_75043_4_, byte[] p_75043_5_, double p_75043_6_, double p_75043_8_, double p_75043_10_, float p_75043_12_, float p_75043_13_, float p_75043_14_, int p_75043_15_, int p_75043_16_, double p_75043_17_)
	{
		double var19 = p_75043_3_ * 16 + 8;
		double var21 = p_75043_4_ * 16 + 8;
		float var23 = 0.0F;
		float var24 = 0.0F;
		Random var25 = new Random(p_75043_1_);
		if(p_75043_16_ <= 0)
		{
			int var26 = range * 16 - 16;
			p_75043_16_ = var26 - var25.nextInt(var26 / 4);
		}
		boolean var53 = false;
		if(p_75043_15_ == -1)
		{
			p_75043_15_ = p_75043_16_ / 2;
			var53 = true;
		}
		int var27 = var25.nextInt(p_75043_16_ / 2) + p_75043_16_ / 4;
		for(boolean var28 = var25.nextInt(6) == 0; p_75043_15_ < p_75043_16_; ++p_75043_15_)
		{
			double var29 = 1.5D + MathHelper.sin(p_75043_15_ * (float) Math.PI / p_75043_16_) * p_75043_12_ * 1.0F;
			double var31 = var29 * p_75043_17_;
			float var33 = MathHelper.cos(p_75043_14_);
			float var34 = MathHelper.sin(p_75043_14_);
			p_75043_6_ += MathHelper.cos(p_75043_13_) * var33;
			p_75043_8_ += var34;
			p_75043_10_ += MathHelper.sin(p_75043_13_) * var33;
			if(var28)
			{
				p_75043_14_ *= 0.92F;
			} else
			{
				p_75043_14_ *= 0.7F;
			}
			p_75043_14_ += var24 * 0.1F;
			p_75043_13_ += var23 * 0.1F;
			var24 *= 0.9F;
			var23 *= 0.75F;
			var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
			var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;
			if(!var53 && p_75043_15_ == var27 && p_75043_12_ > 1.0F)
			{
				generateCaveNode(var25.nextLong(), p_75043_3_, p_75043_4_, p_75043_5_, p_75043_6_, p_75043_8_, p_75043_10_, var25.nextFloat() * 0.5F + 0.5F, p_75043_13_ - (float) Math.PI / 2F, p_75043_14_ / 3.0F, p_75043_15_, p_75043_16_, 1.0D);
				generateCaveNode(var25.nextLong(), p_75043_3_, p_75043_4_, p_75043_5_, p_75043_6_, p_75043_8_, p_75043_10_, var25.nextFloat() * 0.5F + 0.5F, p_75043_13_ + (float) Math.PI / 2F, p_75043_14_ / 3.0F, p_75043_15_, p_75043_16_, 1.0D);
				return;
			}
			if(var53 || var25.nextInt(4) != 0)
			{
				double var35 = p_75043_6_ - var19;
				double var37 = p_75043_10_ - var21;
				double var39 = p_75043_16_ - p_75043_15_;
				double var41 = p_75043_12_ + 2.0F + 16.0F;
				if(var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) return;
				if(p_75043_6_ >= var19 - 16.0D - var29 * 2.0D && p_75043_10_ >= var21 - 16.0D - var29 * 2.0D && p_75043_6_ <= var19 + 16.0D + var29 * 2.0D && p_75043_10_ <= var21 + 16.0D + var29 * 2.0D)
				{
					int var54 = MathHelper.floor_double(p_75043_6_ - var29) - p_75043_3_ * 16 - 1;
					int var36 = MathHelper.floor_double(p_75043_6_ + var29) - p_75043_3_ * 16 + 1;
					int var56 = MathHelper.floor_double(p_75043_8_ - var31) - 1;
					int var38 = MathHelper.floor_double(p_75043_8_ + var31) + 1;
					int var55 = MathHelper.floor_double(p_75043_10_ - var29) - p_75043_4_ * 16 - 1;
					int var40 = MathHelper.floor_double(p_75043_10_ + var29) - p_75043_4_ * 16 + 1;
					if(var54 < 0)
					{
						var54 = 0;
					}
					if(var36 > 16)
					{
						var36 = 16;
					}
					if(var56 < 1)
					{
						var56 = 1;
					}
					if(var38 > 120)
					{
						var38 = 120;
					}
					if(var55 < 0)
					{
						var55 = 0;
					}
					if(var40 > 16)
					{
						var40 = 16;
					}
					boolean var57 = false;
					int var42;
					int var45;
					for(var42 = var54; !var57 && var42 < var36; ++var42)
					{
						for(int var43 = var55; !var57 && var43 < var40; ++var43)
						{
							for(int var44 = var38 + 1; !var57 && var44 >= var56 - 1; --var44)
							{
								var45 = (var42 * 16 + var43) * 128 + var44;
								if(var44 >= 0 && var44 < 128)
								{
									if(p_75043_5_[var45] == Block.lavaMoving.blockID || p_75043_5_[var45] == Block.lavaStill.blockID)
									{
										var57 = true;
									}
									if(var44 != var56 - 1 && var42 != var54 && var42 != var36 - 1 && var43 != var55 && var43 != var40 - 1)
									{
										var44 = var56;
									}
								}
							}
						}
					}
					if(!var57)
					{
						for(var42 = var54; var42 < var36; ++var42)
						{
							double var58 = (var42 + p_75043_3_ * 16 + 0.5D - p_75043_6_) / var29;
							for(var45 = var55; var45 < var40; ++var45)
							{
								double var46 = (var45 + p_75043_4_ * 16 + 0.5D - p_75043_10_) / var29;
								int var48 = (var42 * 16 + var45) * 128 + var38;
								for(int var49 = var38 - 1; var49 >= var56; --var49)
								{
									double var50 = (var49 + 0.5D - p_75043_8_) / var31;
									if(var50 > -0.7D && var58 * var58 + var50 * var50 + var46 * var46 < 1.0D)
									{
										byte var52 = p_75043_5_[var48];
										if(var52 == Block.netherrack.blockID || var52 == Block.dirt.blockID || var52 == Block.grass.blockID)
										{
											p_75043_5_[var48] = 0;
										}
									}
									--var48;
								}
							}
						}
						if(var53)
						{
							break;
						}
					}
				}
			}
		}
	}
	
	protected void generateLargeCaveNode(long p_75044_1_, int p_75044_3_, int p_75044_4_, byte[] p_75044_5_, double p_75044_6_, double p_75044_8_, double p_75044_10_)
	{
		generateCaveNode(p_75044_1_, p_75044_3_, p_75044_4_, p_75044_5_, p_75044_6_, p_75044_8_, p_75044_10_, 1.0F + rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}
	
	@Override protected void recursiveGenerate(World p_75037_1_, int p_75037_2_, int p_75037_3_, int p_75037_4_, int p_75037_5_, byte[] p_75037_6_)
	{
		int var7 = rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1);
		if(rand.nextInt(5) != 0)
		{
			var7 = 0;
		}
		for(int var8 = 0; var8 < var7; ++var8)
		{
			double var9 = p_75037_2_ * 16 + rand.nextInt(16);
			double var11 = rand.nextInt(128);
			double var13 = p_75037_3_ * 16 + rand.nextInt(16);
			int var15 = 1;
			if(rand.nextInt(4) == 0)
			{
				generateLargeCaveNode(rand.nextLong(), p_75037_4_, p_75037_5_, p_75037_6_, var9, var11, var13);
				var15 += rand.nextInt(4);
			}
			for(int var16 = 0; var16 < var15; ++var16)
			{
				float var17 = rand.nextFloat() * (float) Math.PI * 2.0F;
				float var18 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var19 = rand.nextFloat() * 2.0F + rand.nextFloat();
				generateCaveNode(rand.nextLong(), p_75037_4_, p_75037_5_, p_75037_6_, var9, var11, var13, var19 * 2.0F, var17, var18, 0, 0, 0.5D);
			}
		}
	}
}
