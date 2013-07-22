package net.minecraft.src;

import java.util.Random;

public class BlockObsidian extends BlockStone
{
	public BlockObsidian(int par1)
	{
		super(par1);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.obsidian.blockID;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 1;
	}
}
