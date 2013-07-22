package net.minecraft.src;

import java.util.Random;

public class BlockFlowing extends BlockFluid
{
	int numAdjacentSources = 0;
	boolean[] isOptimalFlowDirection = new boolean[4];
	int[] flowCost = new int[4];
	
	protected BlockFlowing(int p_i3965_1_, Material p_i3965_2_)
	{
		super(p_i3965_1_, p_i3965_2_);
	}
	
	private boolean blockBlocksFlow(World p_72208_1_, int p_72208_2_, int p_72208_3_, int p_72208_4_)
	{
		int var5 = p_72208_1_.getBlockId(p_72208_2_, p_72208_3_, p_72208_4_);
		if(var5 != Block.doorWood.blockID && var5 != Block.doorIron.blockID && var5 != Block.signPost.blockID && var5 != Block.ladder.blockID && var5 != Block.reed.blockID)
		{
			if(var5 == 0) return false;
			else
			{
				Material var6 = Block.blocksList[var5].blockMaterial;
				return var6 == Material.portal ? true : var6.blocksMovement();
			}
		} else return true;
	}
	
	private int calculateFlowCost(World p_72209_1_, int p_72209_2_, int p_72209_3_, int p_72209_4_, int p_72209_5_, int p_72209_6_)
	{
		int var7 = 1000;
		for(int var8 = 0; var8 < 4; ++var8)
		{
			if((var8 != 0 || p_72209_6_ != 1) && (var8 != 1 || p_72209_6_ != 0) && (var8 != 2 || p_72209_6_ != 3) && (var8 != 3 || p_72209_6_ != 2))
			{
				int var9 = p_72209_2_;
				int var11 = p_72209_4_;
				if(var8 == 0)
				{
					var9 = p_72209_2_ - 1;
				}
				if(var8 == 1)
				{
					++var9;
				}
				if(var8 == 2)
				{
					var11 = p_72209_4_ - 1;
				}
				if(var8 == 3)
				{
					++var11;
				}
				if(!blockBlocksFlow(p_72209_1_, var9, p_72209_3_, var11) && (p_72209_1_.getBlockMaterial(var9, p_72209_3_, var11) != blockMaterial || p_72209_1_.getBlockMetadata(var9, p_72209_3_, var11) != 0))
				{
					if(!blockBlocksFlow(p_72209_1_, var9, p_72209_3_ - 1, var11)) return p_72209_5_;
					if(p_72209_5_ < 4)
					{
						int var12 = calculateFlowCost(p_72209_1_, var9, p_72209_3_, var11, p_72209_5_ + 1, var8);
						if(var12 < var7)
						{
							var7 = var12;
						}
					}
				}
			}
		}
		return var7;
	}
	
	private void flowIntoBlock(World p_72210_1_, int p_72210_2_, int p_72210_3_, int p_72210_4_, int p_72210_5_)
	{
		if(liquidCanDisplaceBlock(p_72210_1_, p_72210_2_, p_72210_3_, p_72210_4_))
		{
			int var6 = p_72210_1_.getBlockId(p_72210_2_, p_72210_3_, p_72210_4_);
			if(var6 > 0)
			{
				if(blockMaterial == Material.lava)
				{
					triggerLavaMixEffects(p_72210_1_, p_72210_2_, p_72210_3_, p_72210_4_);
				} else
				{
					Block.blocksList[var6].dropBlockAsItem(p_72210_1_, p_72210_2_, p_72210_3_, p_72210_4_, p_72210_1_.getBlockMetadata(p_72210_2_, p_72210_3_, p_72210_4_), 0);
				}
			}
			p_72210_1_.setBlock(p_72210_2_, p_72210_3_, p_72210_4_, blockID, p_72210_5_, 3);
		}
	}
	
	@Override public boolean func_82506_l()
	{
		return false;
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return blockMaterial != Material.lava;
	}
	
	private boolean[] getOptimalFlowDirections(World p_72206_1_, int p_72206_2_, int p_72206_3_, int p_72206_4_)
	{
		int var5;
		int var6;
		for(var5 = 0; var5 < 4; ++var5)
		{
			flowCost[var5] = 1000;
			var6 = p_72206_2_;
			int var8 = p_72206_4_;
			if(var5 == 0)
			{
				var6 = p_72206_2_ - 1;
			}
			if(var5 == 1)
			{
				++var6;
			}
			if(var5 == 2)
			{
				var8 = p_72206_4_ - 1;
			}
			if(var5 == 3)
			{
				++var8;
			}
			if(!blockBlocksFlow(p_72206_1_, var6, p_72206_3_, var8) && (p_72206_1_.getBlockMaterial(var6, p_72206_3_, var8) != blockMaterial || p_72206_1_.getBlockMetadata(var6, p_72206_3_, var8) != 0))
			{
				if(blockBlocksFlow(p_72206_1_, var6, p_72206_3_ - 1, var8))
				{
					flowCost[var5] = calculateFlowCost(p_72206_1_, var6, p_72206_3_, var8, 1, var5);
				} else
				{
					flowCost[var5] = 0;
				}
			}
		}
		var5 = flowCost[0];
		for(var6 = 1; var6 < 4; ++var6)
		{
			if(flowCost[var6] < var5)
			{
				var5 = flowCost[var6];
			}
		}
		for(var6 = 0; var6 < 4; ++var6)
		{
			isOptimalFlowDirection[var6] = flowCost[var6] == var5;
		}
		return isOptimalFlowDirection;
	}
	
	protected int getSmallestFlowDecay(World p_72211_1_, int p_72211_2_, int p_72211_3_, int p_72211_4_, int p_72211_5_)
	{
		int var6 = getFlowDecay(p_72211_1_, p_72211_2_, p_72211_3_, p_72211_4_);
		if(var6 < 0) return p_72211_5_;
		else
		{
			if(var6 == 0)
			{
				++numAdjacentSources;
			}
			if(var6 >= 8)
			{
				var6 = 0;
			}
			return p_72211_5_ >= 0 && var6 >= p_72211_5_ ? p_72211_5_ : var6;
		}
	}
	
	private boolean liquidCanDisplaceBlock(World p_72207_1_, int p_72207_2_, int p_72207_3_, int p_72207_4_)
	{
		Material var5 = p_72207_1_.getBlockMaterial(p_72207_2_, p_72207_3_, p_72207_4_);
		return var5 == blockMaterial ? false : var5 == Material.lava ? false : !blockBlocksFlow(p_72207_1_, p_72207_2_, p_72207_3_, p_72207_4_);
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		if(p_71861_1_.getBlockId(p_71861_2_, p_71861_3_, p_71861_4_) == blockID)
		{
			p_71861_1_.scheduleBlockUpdate(p_71861_2_, p_71861_3_, p_71861_4_, blockID, tickRate(p_71861_1_));
		}
	}
	
	private void updateFlow(World p_72205_1_, int p_72205_2_, int p_72205_3_, int p_72205_4_)
	{
		int var5 = p_72205_1_.getBlockMetadata(p_72205_2_, p_72205_3_, p_72205_4_);
		p_72205_1_.setBlock(p_72205_2_, p_72205_3_, p_72205_4_, blockID + 1, var5, 2);
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		int var6 = getFlowDecay(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
		byte var7 = 1;
		if(blockMaterial == Material.lava && !p_71847_1_.provider.isHellWorld)
		{
			var7 = 2;
		}
		boolean var8 = true;
		int var10;
		if(var6 > 0)
		{
			byte var9 = -100;
			numAdjacentSources = 0;
			int var12 = getSmallestFlowDecay(p_71847_1_, p_71847_2_ - 1, p_71847_3_, p_71847_4_, var9);
			var12 = getSmallestFlowDecay(p_71847_1_, p_71847_2_ + 1, p_71847_3_, p_71847_4_, var12);
			var12 = getSmallestFlowDecay(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ - 1, var12);
			var12 = getSmallestFlowDecay(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ + 1, var12);
			var10 = var12 + var7;
			if(var10 >= 8 || var12 < 0)
			{
				var10 = -1;
			}
			if(getFlowDecay(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 0)
			{
				int var11 = getFlowDecay(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_);
				if(var11 >= 8)
				{
					var10 = var11;
				} else
				{
					var10 = var11 + 8;
				}
			}
			if(numAdjacentSources >= 2 && blockMaterial == Material.water)
			{
				if(p_71847_1_.getBlockMaterial(p_71847_2_, p_71847_3_ - 1, p_71847_4_).isSolid())
				{
					var10 = 0;
				} else if(p_71847_1_.getBlockMaterial(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == blockMaterial && p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == 0)
				{
					var10 = 0;
				}
			}
			if(blockMaterial == Material.lava && var6 < 8 && var10 < 8 && var10 > var6 && p_71847_5_.nextInt(4) != 0)
			{
				var10 = var6;
				var8 = false;
			}
			if(var10 == var6)
			{
				if(var8)
				{
					updateFlow(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
				}
			} else
			{
				var6 = var10;
				if(var10 < 0)
				{
					p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
				} else
				{
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var10, 2);
					p_71847_1_.scheduleBlockUpdate(p_71847_2_, p_71847_3_, p_71847_4_, blockID, tickRate(p_71847_1_));
					p_71847_1_.notifyBlocksOfNeighborChange(p_71847_2_, p_71847_3_, p_71847_4_, blockID);
				}
			}
		} else
		{
			updateFlow(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
		}
		if(liquidCanDisplaceBlock(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_))
		{
			if(blockMaterial == Material.lava && p_71847_1_.getBlockMaterial(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == Material.water)
			{
				p_71847_1_.setBlock(p_71847_2_, p_71847_3_ - 1, p_71847_4_, Block.stone.blockID);
				triggerLavaMixEffects(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_);
				return;
			}
			if(var6 >= 8)
			{
				flowIntoBlock(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_, var6);
			} else
			{
				flowIntoBlock(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_, var6 + 8);
			}
		} else if(var6 >= 0 && (var6 == 0 || blockBlocksFlow(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_)))
		{
			boolean[] var13 = getOptimalFlowDirections(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
			var10 = var6 + var7;
			if(var6 >= 8)
			{
				var10 = 1;
			}
			if(var10 >= 8) return;
			if(var13[0])
			{
				flowIntoBlock(p_71847_1_, p_71847_2_ - 1, p_71847_3_, p_71847_4_, var10);
			}
			if(var13[1])
			{
				flowIntoBlock(p_71847_1_, p_71847_2_ + 1, p_71847_3_, p_71847_4_, var10);
			}
			if(var13[2])
			{
				flowIntoBlock(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ - 1, var10);
			}
			if(var13[3])
			{
				flowIntoBlock(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ + 1, var10);
			}
		}
	}
}
