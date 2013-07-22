package net.minecraft.src;

import java.util.List;

public class BlockWood extends Block
{
	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };
	public static final String[] woodTextureTypes = new String[] { "wood", "wood_spruce", "wood_birch", "wood_jungle" };
	private Icon[] iconArray;
	
	public BlockWood(int p_i4023_1_)
	{
		super(p_i4023_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_;
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
		iconArray = new Icon[woodTextureTypes.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(woodTextureTypes[var2]);
		}
	}
}
