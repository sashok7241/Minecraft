package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class StructureMineshaftPieces
{
	private static final WeightedRandomChestContent[] mineshaftChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5), new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5), new WeightedRandomChestContent(Item.dyePowder.itemID, 4, 4, 9, 5), new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 2, 3), new WeightedRandomChestContent(Item.coal.itemID, 0, 3, 8, 10), new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Block.rail.blockID, 0, 4, 8, 1), new WeightedRandomChestContent(Item.melonSeeds.itemID, 0, 2, 4, 10), new WeightedRandomChestContent(Item.pumpkinSeeds.itemID, 0, 2, 4, 10) };
	
	static WeightedRandomChestContent[] func_78816_a()
	{
		return mineshaftChestContents;
	}
	
	static StructureComponent getNextComponent(StructureComponent p_78814_0_, List p_78814_1_, Random p_78814_2_, int p_78814_3_, int p_78814_4_, int p_78814_5_, int p_78814_6_, int p_78814_7_)
	{
		return getNextMineShaftComponent(p_78814_0_, p_78814_1_, p_78814_2_, p_78814_3_, p_78814_4_, p_78814_5_, p_78814_6_, p_78814_7_);
	}
	
	private static StructureComponent getNextMineShaftComponent(StructureComponent p_78817_0_, List p_78817_1_, Random p_78817_2_, int p_78817_3_, int p_78817_4_, int p_78817_5_, int p_78817_6_, int p_78817_7_)
	{
		if(p_78817_7_ > 8) return null;
		else if(Math.abs(p_78817_3_ - p_78817_0_.getBoundingBox().minX) <= 80 && Math.abs(p_78817_5_ - p_78817_0_.getBoundingBox().minZ) <= 80)
		{
			StructureComponent var8 = getRandomComponent(p_78817_1_, p_78817_2_, p_78817_3_, p_78817_4_, p_78817_5_, p_78817_6_, p_78817_7_ + 1);
			if(var8 != null)
			{
				p_78817_1_.add(var8);
				var8.buildComponent(p_78817_0_, p_78817_1_, p_78817_2_);
			}
			return var8;
		} else return null;
	}
	
	private static StructureComponent getRandomComponent(List p_78815_0_, Random p_78815_1_, int p_78815_2_, int p_78815_3_, int p_78815_4_, int p_78815_5_, int p_78815_6_)
	{
		int var7 = p_78815_1_.nextInt(100);
		StructureBoundingBox var8;
		if(var7 >= 80)
		{
			var8 = ComponentMineshaftCross.findValidPlacement(p_78815_0_, p_78815_1_, p_78815_2_, p_78815_3_, p_78815_4_, p_78815_5_);
			if(var8 != null) return new ComponentMineshaftCross(p_78815_6_, p_78815_1_, var8, p_78815_5_);
		} else if(var7 >= 70)
		{
			var8 = ComponentMineshaftStairs.findValidPlacement(p_78815_0_, p_78815_1_, p_78815_2_, p_78815_3_, p_78815_4_, p_78815_5_);
			if(var8 != null) return new ComponentMineshaftStairs(p_78815_6_, p_78815_1_, var8, p_78815_5_);
		} else
		{
			var8 = ComponentMineshaftCorridor.findValidPlacement(p_78815_0_, p_78815_1_, p_78815_2_, p_78815_3_, p_78815_4_, p_78815_5_);
			if(var8 != null) return new ComponentMineshaftCorridor(p_78815_6_, p_78815_1_, var8, p_78815_5_);
		}
		return null;
	}
}
