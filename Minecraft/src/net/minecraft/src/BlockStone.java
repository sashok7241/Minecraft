package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block
{
	public BlockStone(int par1)
	{
		super(par1, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.cobblestone.blockID;
	}
}
