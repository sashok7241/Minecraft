package net.minecraft.src;

public class BlockRail extends BlockRailBase
{
	private Icon theIcon;
	
	protected BlockRail(int par1)
	{
		super(par1, false);
	}
	
	@Override protected void func_94358_a(World par1World, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		if(par7 > 0 && Block.blocksList[par7].canProvidePower() && new BlockBaseRailLogic(this, par1World, par2, par3, par4).getNumberOfAdjacentTracks() == 3)
		{
			refreshTrackShape(par1World, par2, par3, par4, false);
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par2 >= 6 ? theIcon : blockIcon;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon(func_111023_E() + "_turned");
	}
}
