package net.minecraft.src;

import java.util.Random;

public class BlockMelon extends Block
{
	private Icon theIcon;
	
	protected BlockMelon(int par1)
	{
		super(par1, Material.pumpkin);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 != 1 && par1 != 0 ? blockIcon : theIcon;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.melon.itemID;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 3 + par1Random.nextInt(5);
	}
	
	@Override public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		int var3 = quantityDropped(par2Random) + par2Random.nextInt(1 + par1);
		if(var3 > 9)
		{
			var3 = 9;
		}
		return var3;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(func_111023_E() + "_side");
		theIcon = par1IconRegister.registerIcon(func_111023_E() + "_top");
	}
}
