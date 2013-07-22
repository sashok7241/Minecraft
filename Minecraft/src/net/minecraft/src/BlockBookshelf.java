package net.minecraft.src;

import java.util.Random;

public class BlockBookshelf extends Block
{
	public BlockBookshelf(int p_i9039_1_)
	{
		super(p_i9039_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 != 1 && par1 != 0 ? super.getIcon(par1, par2) : Block.planks.getBlockTextureFromSide(par1);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.book.itemID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 3;
	}
}
