package net.minecraft.src;

import java.util.Random;

public class BlockStationary extends BlockFluid
{
	protected BlockStationary(int p_i3966_1_, Material p_i3966_2_)
	{
		super(p_i3966_1_, p_i3966_2_);
		setTickRandomly(false);
		if(p_i3966_2_ == Material.lava)
		{
			setTickRandomly(true);
		}
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return blockMaterial != Material.lava;
	}
	
	private boolean isFlammable(World p_72216_1_, int p_72216_2_, int p_72216_3_, int p_72216_4_)
	{
		return p_72216_1_.getBlockMaterial(p_72216_2_, p_72216_3_, p_72216_4_).getCanBurn();
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
		if(p_71863_1_.getBlockId(p_71863_2_, p_71863_3_, p_71863_4_) == blockID)
		{
			setNotStationary(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	private void setNotStationary(World p_72215_1_, int p_72215_2_, int p_72215_3_, int p_72215_4_)
	{
		int var5 = p_72215_1_.getBlockMetadata(p_72215_2_, p_72215_3_, p_72215_4_);
		p_72215_1_.setBlock(p_72215_2_, p_72215_3_, p_72215_4_, blockID - 1, var5, 2);
		p_72215_1_.scheduleBlockUpdate(p_72215_2_, p_72215_3_, p_72215_4_, blockID - 1, tickRate(p_72215_1_));
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(blockMaterial == Material.lava)
		{
			int var6 = p_71847_5_.nextInt(3);
			int var7;
			int var8;
			for(var7 = 0; var7 < var6; ++var7)
			{
				p_71847_2_ += p_71847_5_.nextInt(3) - 1;
				++p_71847_3_;
				p_71847_4_ += p_71847_5_.nextInt(3) - 1;
				var8 = p_71847_1_.getBlockId(p_71847_2_, p_71847_3_, p_71847_4_);
				if(var8 == 0)
				{
					if(isFlammable(p_71847_1_, p_71847_2_ - 1, p_71847_3_, p_71847_4_) || isFlammable(p_71847_1_, p_71847_2_ + 1, p_71847_3_, p_71847_4_) || isFlammable(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ - 1) || isFlammable(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ + 1) || isFlammable(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_) || isFlammable(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_))
					{
						p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.fire.blockID);
						return;
					}
				} else if(Block.blocksList[var8].blockMaterial.blocksMovement()) return;
			}
			if(var6 == 0)
			{
				var7 = p_71847_2_;
				var8 = p_71847_4_;
				for(int var9 = 0; var9 < 3; ++var9)
				{
					p_71847_2_ = var7 + p_71847_5_.nextInt(3) - 1;
					p_71847_4_ = var8 + p_71847_5_.nextInt(3) - 1;
					if(p_71847_1_.isAirBlock(p_71847_2_, p_71847_3_ + 1, p_71847_4_) && isFlammable(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_))
					{
						p_71847_1_.setBlock(p_71847_2_, p_71847_3_ + 1, p_71847_4_, Block.fire.blockID);
					}
				}
			}
		}
	}
}
