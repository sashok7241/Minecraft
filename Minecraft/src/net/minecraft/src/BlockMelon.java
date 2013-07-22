package net.minecraft.src;

import java.util.Random;

public class BlockMelon extends Block
{
	private Icon theIcon;
	
	protected BlockMelon(int p_i3968_1_)
	{
		super(p_i3968_1_, Material.pumpkin);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 != 1 && par1 != 0 ? blockIcon : theIcon;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.melon.itemID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 3 + p_71925_1_.nextInt(5);
	}
	
	@Override public int quantityDroppedWithBonus(int p_71910_1_, Random p_71910_2_)
	{
		int var3 = quantityDropped(p_71910_2_) + p_71910_2_.nextInt(1 + p_71910_1_);
		if(var3 > 9)
		{
			var3 = 9;
		}
		return var3;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("melon_side");
		theIcon = par1IconRegister.registerIcon("melon_top");
	}
}
