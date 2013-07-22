package net.minecraft.src;

import java.util.List;

public class BlockCloth extends Block
{
	private Icon[] iconArray;
	
	public BlockCloth()
	{
		super(35, Material.cloth);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return iconArray[par2 % iconArray.length];
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 0; var4 < 16; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[16];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon("cloth_" + var2);
		}
	}
	
	public static int getBlockFromDye(int par0)
	{
		return ~par0 & 15;
	}
	
	public static int getDyeFromBlock(int par0)
	{
		return ~par0 & 15;
	}
}
