package net.minecraft.src;

import java.util.Random;

class StructureStrongholdStones extends StructurePieceBlockSelector
{
	private StructureStrongholdStones()
	{
	}
	
	StructureStrongholdStones(StructureStrongholdPieceWeight2 p_i3849_1_)
	{
		this();
	}
	
	@Override public void selectBlocks(Random p_75062_1_, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_)
	{
		if(p_75062_5_)
		{
			selectedBlockId = Block.stoneBrick.blockID;
			float var6 = p_75062_1_.nextFloat();
			if(var6 < 0.2F)
			{
				selectedBlockMetaData = 2;
			} else if(var6 < 0.5F)
			{
				selectedBlockMetaData = 1;
			} else if(var6 < 0.55F)
			{
				selectedBlockId = Block.silverfish.blockID;
				selectedBlockMetaData = 2;
			} else
			{
				selectedBlockMetaData = 0;
			}
		} else
		{
			selectedBlockId = 0;
			selectedBlockMetaData = 0;
		}
	}
}
