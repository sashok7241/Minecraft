package net.minecraft.src;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator
{
	private int deadBushID;
	
	public WorldGenDeadBush(int p_i3788_1_)
	{
		deadBushID = p_i3788_1_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var11;
		for(boolean var6 = false; ((var11 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_)) == 0 || var11 == Block.leaves.blockID) && p_76484_4_ > 0; --p_76484_4_)
		{
			;
		}
		for(int var7 = 0; var7 < 4; ++var7)
		{
			int var8 = p_76484_3_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
			int var9 = p_76484_4_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
			int var10 = p_76484_5_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
			if(p_76484_1_.isAirBlock(var8, var9, var10) && Block.blocksList[deadBushID].canBlockStay(p_76484_1_, var8, var9, var10))
			{
				p_76484_1_.setBlock(var8, var9, var10, deadBushID, 0, 2);
			}
		}
		return true;
	}
}
