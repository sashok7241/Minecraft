package net.minecraft.src;

import java.util.Random;

class StructureScatteredFeatureStones extends StructurePieceBlockSelector
{
	private StructureScatteredFeatureStones()
	{
	}
	
	StructureScatteredFeatureStones(ComponentScatteredFeaturePieces2 p_i3834_1_)
	{
		this();
	}
	
	@Override public void selectBlocks(Random p_75062_1_, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_)
	{
		if(p_75062_1_.nextFloat() < 0.4F)
		{
			selectedBlockId = Block.cobblestone.blockID;
		} else
		{
			selectedBlockId = Block.cobblestoneMossy.blockID;
		}
	}
}
