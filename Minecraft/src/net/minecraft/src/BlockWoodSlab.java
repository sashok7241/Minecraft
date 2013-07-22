package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockWoodSlab extends BlockHalfSlab
{
	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };
	
	public BlockWoodSlab(int par1, boolean par2)
	{
		super(par1, par2, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(Block.woodSingleSlab.blockID, 2, par1 & 7);
	}
	
	@Override public String getFullSlabName(int par1)
	{
		if(par1 < 0 || par1 >= woodType.length)
		{
			par1 = 0;
		}
		return super.getUnlocalizedName() + "." + woodType[par1];
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getIcon(par1, par2 & 7);
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if(par1 != Block.woodDoubleSlab.blockID)
		{
			for(int var4 = 0; var4 < 4; ++var4)
			{
				par3List.add(new ItemStack(par1, 1, var4));
			}
		}
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.woodSingleSlab.blockID;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
}
