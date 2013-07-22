package net.minecraft.src;

public class BlockPoweredOre extends BlockOreStorage
{
	public BlockPoweredOre(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return 15;
	}
}
