package net.minecraft.src;

import java.util.Random;

public class WorldGenSpikes extends WorldGenerator
{
	private int replaceID;
	
	public WorldGenSpikes(int p_i3798_1_)
	{
		replaceID = p_i3798_1_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		if(p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_) && p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ - 1, p_76484_5_) == replaceID)
		{
			int var6 = p_76484_2_.nextInt(32) + 6;
			int var7 = p_76484_2_.nextInt(4) + 1;
			int var8;
			int var9;
			int var10;
			int var11;
			for(var8 = p_76484_3_ - var7; var8 <= p_76484_3_ + var7; ++var8)
			{
				for(var9 = p_76484_5_ - var7; var9 <= p_76484_5_ + var7; ++var9)
				{
					var10 = var8 - p_76484_3_;
					var11 = var9 - p_76484_5_;
					if(var10 * var10 + var11 * var11 <= var7 * var7 + 1 && p_76484_1_.getBlockId(var8, p_76484_4_ - 1, var9) != replaceID) return false;
				}
			}
			for(var8 = p_76484_4_; var8 < p_76484_4_ + var6 && var8 < 128; ++var8)
			{
				for(var9 = p_76484_3_ - var7; var9 <= p_76484_3_ + var7; ++var9)
				{
					for(var10 = p_76484_5_ - var7; var10 <= p_76484_5_ + var7; ++var10)
					{
						var11 = var9 - p_76484_3_;
						int var12 = var10 - p_76484_5_;
						if(var11 * var11 + var12 * var12 <= var7 * var7 + 1)
						{
							p_76484_1_.setBlock(var9, var8, var10, Block.obsidian.blockID, 0, 2);
						}
					}
				}
			}
			EntityEnderCrystal var13 = new EntityEnderCrystal(p_76484_1_);
			var13.setLocationAndAngles(p_76484_3_ + 0.5F, p_76484_4_ + var6, p_76484_5_ + 0.5F, p_76484_2_.nextFloat() * 360.0F, 0.0F);
			p_76484_1_.spawnEntityInWorld(var13);
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_ + var6, p_76484_5_, Block.bedrock.blockID, 0, 2);
			return true;
		} else return false;
	}
}
