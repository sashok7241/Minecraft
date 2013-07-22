package net.minecraft.src;

import java.util.Random;

public class WorldGenTallGrass extends WorldGenerator
{
	private int tallGrassID;
	private int tallGrassMetadata;
	
	public WorldGenTallGrass(int p_i3801_1_, int p_i3801_2_)
	{
		tallGrassID = p_i3801_1_;
		tallGrassMetadata = p_i3801_2_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var11;
		for(boolean var6 = false; ((var11 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_)) == 0 || var11 == Block.leaves.blockID) && p_76484_4_ > 0; --p_76484_4_)
		{
			;
		}
		for(int var7 = 0; var7 < 128; ++var7)
		{
			int var8 = p_76484_3_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
			int var9 = p_76484_4_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
			int var10 = p_76484_5_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
			if(p_76484_1_.isAirBlock(var8, var9, var10) && Block.blocksList[tallGrassID].canBlockStay(p_76484_1_, var8, var9, var10))
			{
				p_76484_1_.setBlock(var8, var9, var10, tallGrassID, tallGrassMetadata, 2);
			}
		}
		return true;
	}
}
