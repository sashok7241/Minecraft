package net.minecraft.src;

import java.util.Random;

public class BlockGlass extends BlockBreakable
{
	public BlockGlass(int p_i9057_1_, Material p_i9057_2_, boolean p_i9057_3_)
	{
		super(p_i9057_1_, "glass", p_i9057_2_, p_i9057_3_);
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
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
