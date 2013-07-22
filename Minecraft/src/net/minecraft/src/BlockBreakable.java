package net.minecraft.src;

public class BlockBreakable extends Block
{
	private boolean localFlag;
	private String breakableBlockIcon;
	
	protected BlockBreakable(int p_i9059_1_, String p_i9059_2_, Material p_i9059_3_, boolean p_i9059_4_)
	{
		super(p_i9059_1_, p_i9059_3_);
		localFlag = p_i9059_4_;
		breakableBlockIcon = p_i9059_2_;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(breakableBlockIcon);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
		return !localFlag && var6 == blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
