package net.minecraft.src;

import java.util.Random;

public class WorldGenMinable extends WorldGenerator
{
	private int minableBlockId;
	private int numberOfBlocks;
	private int field_94523_c;
	
	public WorldGenMinable(int p_i3796_1_, int p_i3796_2_)
	{
		this(p_i3796_1_, p_i3796_2_, Block.stone.blockID);
	}
	
	public WorldGenMinable(int p_i9035_1_, int p_i9035_2_, int p_i9035_3_)
	{
		minableBlockId = p_i9035_1_;
		numberOfBlocks = p_i9035_2_;
		field_94523_c = p_i9035_3_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		float var6 = p_76484_2_.nextFloat() * (float) Math.PI;
		double var7 = p_76484_3_ + 8 + MathHelper.sin(var6) * numberOfBlocks / 8.0F;
		double var9 = p_76484_3_ + 8 - MathHelper.sin(var6) * numberOfBlocks / 8.0F;
		double var11 = p_76484_5_ + 8 + MathHelper.cos(var6) * numberOfBlocks / 8.0F;
		double var13 = p_76484_5_ + 8 - MathHelper.cos(var6) * numberOfBlocks / 8.0F;
		double var15 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;
		double var17 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;
		for(int var19 = 0; var19 <= numberOfBlocks; ++var19)
		{
			double var20 = var7 + (var9 - var7) * var19 / numberOfBlocks;
			double var22 = var15 + (var17 - var15) * var19 / numberOfBlocks;
			double var24 = var11 + (var13 - var11) * var19 / numberOfBlocks;
			double var26 = p_76484_2_.nextDouble() * numberOfBlocks / 16.0D;
			double var28 = (MathHelper.sin(var19 * (float) Math.PI / numberOfBlocks) + 1.0F) * var26 + 1.0D;
			double var30 = (MathHelper.sin(var19 * (float) Math.PI / numberOfBlocks) + 1.0F) * var26 + 1.0D;
			int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
			int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
			int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
			int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
			int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
			int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);
			for(int var38 = var32; var38 <= var35; ++var38)
			{
				double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
				if(var39 * var39 < 1.0D)
				{
					for(int var41 = var33; var41 <= var36; ++var41)
					{
						double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
						if(var39 * var39 + var42 * var42 < 1.0D)
						{
							for(int var44 = var34; var44 <= var37; ++var44)
							{
								double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);
								if(var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && p_76484_1_.getBlockId(var38, var41, var44) == field_94523_c)
								{
									p_76484_1_.setBlock(var38, var41, var44, minableBlockId, 0, 2);
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
}
