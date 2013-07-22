package net.minecraft.src;

import java.util.Random;

public class BlockObsidian extends BlockStone
{
	public BlockObsidian(int p_i9075_1_)
	{
		super(p_i9075_1_);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.obsidian.blockID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
}
