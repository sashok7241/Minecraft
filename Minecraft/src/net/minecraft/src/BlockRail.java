package net.minecraft.src;

public class BlockRail extends BlockRailBase
{
	private Icon theIcon;
	
	protected BlockRail(int p_i9083_1_)
	{
		super(p_i9083_1_, false);
	}
	
	@Override protected void func_94358_a(World p_94358_1_, int p_94358_2_, int p_94358_3_, int p_94358_4_, int p_94358_5_, int p_94358_6_, int p_94358_7_)
	{
		if(p_94358_7_ > 0 && Block.blocksList[p_94358_7_].canProvidePower() && new BlockBaseRailLogic(this, p_94358_1_, p_94358_2_, p_94358_3_, p_94358_4_).getNumberOfAdjacentTracks() == 3)
		{
			refreshTrackShape(p_94358_1_, p_94358_2_, p_94358_3_, p_94358_4_, false);
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par2 >= 6 ? theIcon : blockIcon;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon("rail_turn");
	}
}
