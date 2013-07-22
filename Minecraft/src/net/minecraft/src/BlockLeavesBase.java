package net.minecraft.src;

public class BlockLeavesBase extends Block
{
	protected boolean graphicsLevel;
	
	protected BlockLeavesBase(int par1, Material par2Material, boolean par3)
	{
		super(par1, par2Material);
		graphicsLevel = par3;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
		return !graphicsLevel && var6 == blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
