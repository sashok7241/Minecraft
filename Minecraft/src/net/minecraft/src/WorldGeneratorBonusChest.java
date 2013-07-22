package net.minecraft.src;

import java.util.Random;

public class WorldGeneratorBonusChest extends WorldGenerator
{
	private final WeightedRandomChestContent[] theBonusChestGenerator;
	private final int itemsToGenerateInBonusChest;
	
	public WorldGeneratorBonusChest(WeightedRandomChestContent[] p_i3786_1_, int p_i3786_2_)
	{
		theBonusChestGenerator = p_i3786_1_;
		itemsToGenerateInBonusChest = p_i3786_2_;
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		int var12;
		for(boolean var6 = false; ((var12 = p_76484_1_.getBlockId(p_76484_3_, p_76484_4_, p_76484_5_)) == 0 || var12 == Block.leaves.blockID) && p_76484_4_ > 1; --p_76484_4_)
		{
			;
		}
		if(p_76484_4_ < 1) return false;
		else
		{
			++p_76484_4_;
			for(int var7 = 0; var7 < 4; ++var7)
			{
				int var8 = p_76484_3_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
				int var9 = p_76484_4_ + p_76484_2_.nextInt(3) - p_76484_2_.nextInt(3);
				int var10 = p_76484_5_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
				if(p_76484_1_.isAirBlock(var8, var9, var10) && p_76484_1_.doesBlockHaveSolidTopSurface(var8, var9 - 1, var10))
				{
					p_76484_1_.setBlock(var8, var9, var10, Block.chest.blockID, 0, 2);
					TileEntityChest var11 = (TileEntityChest) p_76484_1_.getBlockTileEntity(var8, var9, var10);
					if(var11 != null && var11 != null)
					{
						WeightedRandomChestContent.generateChestContents(p_76484_2_, theBonusChestGenerator, var11, itemsToGenerateInBonusChest);
					}
					if(p_76484_1_.isAirBlock(var8 - 1, var9, var10) && p_76484_1_.doesBlockHaveSolidTopSurface(var8 - 1, var9 - 1, var10))
					{
						p_76484_1_.setBlock(var8 - 1, var9, var10, Block.torchWood.blockID, 0, 2);
					}
					if(p_76484_1_.isAirBlock(var8 + 1, var9, var10) && p_76484_1_.doesBlockHaveSolidTopSurface(var8 - 1, var9 - 1, var10))
					{
						p_76484_1_.setBlock(var8 + 1, var9, var10, Block.torchWood.blockID, 0, 2);
					}
					if(p_76484_1_.isAirBlock(var8, var9, var10 - 1) && p_76484_1_.doesBlockHaveSolidTopSurface(var8 - 1, var9 - 1, var10))
					{
						p_76484_1_.setBlock(var8, var9, var10 - 1, Block.torchWood.blockID, 0, 2);
					}
					if(p_76484_1_.isAirBlock(var8, var9, var10 + 1) && p_76484_1_.doesBlockHaveSolidTopSurface(var8 - 1, var9 - 1, var10))
					{
						p_76484_1_.setBlock(var8, var9, var10 + 1, Block.torchWood.blockID, 0, 2);
					}
					return true;
				}
			}
			return false;
		}
	}
}
