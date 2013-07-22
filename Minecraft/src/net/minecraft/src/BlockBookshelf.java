package net.minecraft.src;

import java.util.Random;

public class BlockBookshelf extends Block
{
	public BlockBookshelf(int par1)
	{
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 != 1 && par1 != 0 ? super.getIcon(par1, par2) : Block.planks.getBlockTextureFromSide(par1);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.book.itemID;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 3;
	}
}
