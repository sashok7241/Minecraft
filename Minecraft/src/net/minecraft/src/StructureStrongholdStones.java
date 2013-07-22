package net.minecraft.src;

import java.util.Random;

class StructureStrongholdStones extends StructurePieceBlockSelector
{
	private StructureStrongholdStones()
	{
	}
	
	StructureStrongholdStones(StructureStrongholdPieceWeight2 par1StructureStrongholdPieceWeight2)
	{
		this();
	}
	
	@Override public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5)
	{
		if(par5)
		{
			selectedBlockId = Block.stoneBrick.blockID;
			float var6 = par1Random.nextFloat();
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
