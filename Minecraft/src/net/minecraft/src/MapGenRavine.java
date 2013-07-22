package net.minecraft.src;

import java.util.Random;

public class MapGenRavine extends MapGenBase
{
	private float[] field_75046_d = new float[1024];
	
	protected void generateRavine(long p_75045_1_, int p_75045_3_, int p_75045_4_, byte[] p_75045_5_, double p_75045_6_, double p_75045_8_, double p_75045_10_, float p_75045_12_, float p_75045_13_, float p_75045_14_, int p_75045_15_, int p_75045_16_, double p_75045_17_)
	{
		Random var19 = new Random(p_75045_1_);
		double var20 = p_75045_3_ * 16 + 8;
		double var22 = p_75045_4_ * 16 + 8;
		float var24 = 0.0F;
		float var25 = 0.0F;
		if(p_75045_16_ <= 0)
		{
			int var26 = range * 16 - 16;
			p_75045_16_ = var26 - var19.nextInt(var26 / 4);
		}
		boolean var54 = false;
		if(p_75045_15_ == -1)
		{
			p_75045_15_ = p_75045_16_ / 2;
			var54 = true;
		}
		float var27 = 1.0F;
		for(int var28 = 0; var28 < 128; ++var28)
		{
			if(var28 == 0 || var19.nextInt(3) == 0)
			{
				var27 = 1.0F + var19.nextFloat() * var19.nextFloat() * 1.0F;
			}
			field_75046_d[var28] = var27 * var27;
		}
		for(; p_75045_15_ < p_75045_16_; ++p_75045_15_)
		{
			double var53 = 1.5D + MathHelper.sin(p_75045_15_ * (float) Math.PI / p_75045_16_) * p_75045_12_ * 1.0F;
			double var30 = var53 * p_75045_17_;
			var53 *= var19.nextFloat() * 0.25D + 0.75D;
			var30 *= var19.nextFloat() * 0.25D + 0.75D;
			float var32 = MathHelper.cos(p_75045_14_);
			float var33 = MathHelper.sin(p_75045_14_);
			p_75045_6_ += MathHelper.cos(p_75045_13_) * var32;
			p_75045_8_ += var33;
			p_75045_10_ += MathHelper.sin(p_75045_13_) * var32;
			p_75045_14_ *= 0.7F;
			p_75045_14_ += var25 * 0.05F;
			p_75045_13_ += var24 * 0.05F;
			var25 *= 0.8F;
			var24 *= 0.5F;
			var25 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0F;
			var24 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0F;
			if(var54 || var19.nextInt(4) != 0)
			{
				double var34 = p_75045_6_ - var20;
				double var36 = p_75045_10_ - var22;
				double var38 = p_75045_16_ - p_75045_15_;
				double var40 = p_75045_12_ + 2.0F + 16.0F;
				if(var34 * var34 + var36 * var36 - var38 * var38 > var40 * var40) return;
				if(p_75045_6_ >= var20 - 16.0D - var53 * 2.0D && p_75045_10_ >= var22 - 16.0D - var53 * 2.0D && p_75045_6_ <= var20 + 16.0D + var53 * 2.0D && p_75045_10_ <= var22 + 16.0D + var53 * 2.0D)
				{
					int var56 = MathHelper.floor_double(p_75045_6_ - var53) - p_75045_3_ * 16 - 1;
					int var35 = MathHelper.floor_double(p_75045_6_ + var53) - p_75045_3_ * 16 + 1;
					int var55 = MathHelper.floor_double(p_75045_8_ - var30) - 1;
					int var37 = MathHelper.floor_double(p_75045_8_ + var30) + 1;
					int var57 = MathHelper.floor_double(p_75045_10_ - var53) - p_75045_4_ * 16 - 1;
					int var39 = MathHelper.floor_double(p_75045_10_ + var53) - p_75045_4_ * 16 + 1;
					if(var56 < 0)
					{
						var56 = 0;
					}
					if(var35 > 16)
					{
						var35 = 16;
					}
					if(var55 < 1)
					{
						var55 = 1;
					}
					if(var37 > 120)
					{
						var37 = 120;
					}
					if(var57 < 0)
					{
						var57 = 0;
					}
					if(var39 > 16)
					{
						var39 = 16;
					}
					boolean var58 = false;
					int var41;
					int var44;
					for(var41 = var56; !var58 && var41 < var35; ++var41)
					{
						for(int var42 = var57; !var58 && var42 < var39; ++var42)
						{
							for(int var43 = var37 + 1; !var58 && var43 >= var55 - 1; --var43)
							{
								var44 = (var41 * 16 + var42) * 128 + var43;
								if(var43 >= 0 && var43 < 128)
								{
									if(p_75045_5_[var44] == Block.waterMoving.blockID || p_75045_5_[var44] == Block.waterStill.blockID)
									{
										var58 = true;
									}
									if(var43 != var55 - 1 && var41 != var56 && var41 != var35 - 1 && var42 != var57 && var42 != var39 - 1)
									{
										var43 = var55;
									}
								}
							}
						}
					}
					if(!var58)
					{
						for(var41 = var56; var41 < var35; ++var41)
						{
							double var59 = (var41 + p_75045_3_ * 16 + 0.5D - p_75045_6_) / var53;
							for(var44 = var57; var44 < var39; ++var44)
							{
								double var45 = (var44 + p_75045_4_ * 16 + 0.5D - p_75045_10_) / var53;
								int var47 = (var41 * 16 + var44) * 128 + var37;
								boolean var48 = false;
								if(var59 * var59 + var45 * var45 < 1.0D)
								{
									for(int var49 = var37 - 1; var49 >= var55; --var49)
									{
										double var50 = (var49 + 0.5D - p_75045_8_) / var30;
										if((var59 * var59 + var45 * var45) * field_75046_d[var49] + var50 * var50 / 6.0D < 1.0D)
										{
											byte var52 = p_75045_5_[var47];
											if(var52 == Block.grass.blockID)
											{
												var48 = true;
											}
											if(var52 == Block.stone.blockID || var52 == Block.dirt.blockID || var52 == Block.grass.blockID)
											{
												if(var49 < 10)
												{
													p_75045_5_[var47] = (byte) Block.lavaMoving.blockID;
												} else
												{
													p_75045_5_[var47] = 0;
													if(var48 && p_75045_5_[var47 - 1] == Block.dirt.blockID)
													{
														p_75045_5_[var47 - 1] = worldObj.getBiomeGenForCoords(var41 + p_75045_3_ * 16, var44 + p_75045_4_ * 16).topBlock;
													}
												}
											}
										}
										--var47;
									}
								}
							}
						}
						if(var54)
						{
							break;
						}
					}
				}
			}
		}
	}
	
	@Override protected void recursiveGenerate(World p_75037_1_, int p_75037_2_, int p_75037_3_, int p_75037_4_, int p_75037_5_, byte[] p_75037_6_)
	{
		if(rand.nextInt(50) == 0)
		{
			double var7 = p_75037_2_ * 16 + rand.nextInt(16);
			double var9 = rand.nextInt(rand.nextInt(40) + 8) + 20;
			double var11 = p_75037_3_ * 16 + rand.nextInt(16);
			byte var13 = 1;
			for(int var14 = 0; var14 < var13; ++var14)
			{
				float var15 = rand.nextFloat() * (float) Math.PI * 2.0F;
				float var16 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var17 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
				generateRavine(rand.nextLong(), p_75037_4_, p_75037_5_, p_75037_6_, var7, var9, var11, var17, var15, var16, 0, 0, 3.0D);
			}
		}
	}
}
