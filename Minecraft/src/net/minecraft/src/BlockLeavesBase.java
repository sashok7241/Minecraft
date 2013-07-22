package net.minecraft.src;

public class BlockLeavesBase extends Block
{
	protected boolean graphicsLevel;
	
	protected BlockLeavesBase(int p_i9098_1_, Material p_i9098_2_, boolean p_i9098_3_)
	{
		super(p_i9098_1_, p_i9098_2_);
		graphicsLevel = p_i9098_3_;
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
