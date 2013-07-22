package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block
{
	public BlockStone(int p_i9092_1_)
	{
		super(p_i9092_1_, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.cobblestone.blockID;
	}
}
