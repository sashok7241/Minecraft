package net.minecraft.src;

import java.util.List;

public class BlockWood extends Block
{
	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };
	private Icon[] iconArray;
	
	public BlockWood(int par1)
	{
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 < 0 || par2 >= iconArray.length)
		{
			par2 = 0;
		}
		return iconArray[par2];
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[woodType.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(func_111023_E() + "_" + woodType[var2]);
		}
	}
}
