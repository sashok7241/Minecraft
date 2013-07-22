package net.minecraft.src;

import java.util.Random;

class StructureScatteredFeatureStones extends StructurePieceBlockSelector
{
	private StructureScatteredFeatureStones()
	{
	}
	
	StructureScatteredFeatureStones(ComponentScatteredFeaturePieces2 par1ComponentScatteredFeaturePieces2)
	{
		this();
	}
	
	@Override public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5)
	{
		if(par1Random.nextFloat() < 0.4F)
		{
			selectedBlockId = Block.cobblestone.blockID;
		} else
		{
			selectedBlockId = Block.cobblestoneMossy.blockID;
		}
	}
}
