package net.minecraft.src;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator
{
	private int liquidBlockId;
	
	public WorldGenLiquids(int p_i3799_1_)
	{
		liquidBlockId = p_i3799_1_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		if(p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ + 1, p_76484_5_) != Block.stone.blockID) return false;
		else if(p_76484_1_.getBlockId(p_76484_3_, p_76484_4_ - 1, p_76484_5_) != Block.stone.blockID) return false;
		else if(p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_) != 0 && p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_) != Block.stone.blockID) return false;
		else
		{
			int var6 = 0;
			if(p_76484_1_.getBlockId(p_76484_3_ - 1, p_76484_4_, p_76484_5_) == Block.stone.blockID)
			{
				++var6;
			}
			if(p_76484_1_.getBlockId(p_76484_3_ + 1, p_76484_4_, p_76484_5_) == Block.stone.blockID)
			{
				++var6;
			}
			if(p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_ - 1) == Block.stone.blockID)
			{
				++var6;
			}
			if(p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_ + 1) == Block.stone.blockID)
			{
				++var6;
			}
			int var7 = 0;
			if(p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_, p_76484_5_))
			{
				++var7;
			}
			if(p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_, p_76484_5_))
			{
				++var7;
			}
			if(p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_ - 1))
			{
				++var7;
			}
			if(p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_ + 1))
			{
				++var7;
			}
			if(var6 == 3 && var7 == 1)
			{
				p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, liquidBlockId, 0, 2);
				p_76484_1_.scheduledUpdatesAreImmediate = true;
				Block.blocksList[liquidBlockId].updateTick(p_76484_1_, p_76484_3_, p_76484_4_, p_76484_5_, p_76484_2_);
				p_76484_1_.scheduledUpdatesAreImmediate = false;
			}
			return true;
		}
	}
}
