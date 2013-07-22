package net.minecraft.src;

import java.util.Random;

public class BlockClay extends Block
{
	public BlockClay(int p_i9046_1_)
	{
		super(p_i9046_1_, Material.clay);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.clay.itemID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 4;
	}
}
