package net.minecraft.src;

import java.util.Random;

public class WorldGenSand extends WorldGenerator
{
	private int sandID;
	private int radius;
	
	public WorldGenSand(int p_i3797_1_, int p_i3797_2_)
	{
		sandID = p_i3797_2_;
		radius = p_i3797_1_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		if(p_76484_1_.getBlockMaterial(p_76484_3_, p_76484_4_, p_76484_5_) != Material.water) return false;
		else
		{
			int var6 = p_76484_2_.nextInt(radius - 2) + 2;
			byte var7 = 2;
			for(int var8 = p_76484_3_ - var6; var8 <= p_76484_3_ + var6; ++var8)
			{
				for(int var9 = p_76484_5_ - var6; var9 <= p_76484_5_ + var6; ++var9)
				{
					int var10 = var8 - p_76484_3_;
					int var11 = var9 - p_76484_5_;
					if(var10 * var10 + var11 * var11 <= var6 * var6)
					{
						for(int var12 = p_76484_4_ - var7; var12 <= p_76484_4_ + var7; ++var12)
						{
							int var13 = p_76484_1_.getBlockId(var8, var12, var9);
							if(var13 == Block.dirt.blockID || var13 == Block.grass.blockID)
							{
								p_76484_1_.setBlock(var8, var12, var9, sandID, 0, 2);
							}
						}
					}
				}
			}
			return true;
		}
	}
}
