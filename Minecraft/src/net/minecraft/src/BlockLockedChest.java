package net.minecraft.src;

import java.util.Random;

public class BlockLockedChest extends Block
{
	protected BlockLockedChest(int par1)
	{
		super(par1, Material.wood);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return true;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		par1World.setBlockToAir(par2, par3, par4);
	}
}
