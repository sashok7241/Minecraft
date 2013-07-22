package net.minecraft.src;

import java.util.Random;

public class BlockGlass extends BlockBreakable
{
	public BlockGlass(int par1, Material par2Material, boolean par3)
	{
		super(par1, "glass", par2Material, par3);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override public int getRenderBlockPass()
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
