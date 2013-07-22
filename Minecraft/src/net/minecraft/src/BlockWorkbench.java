package net.minecraft.src;

public class BlockWorkbench extends Block
{
	private Icon workbenchIconTop;
	private Icon workbenchIconFront;
	
	protected BlockWorkbench(int p_i4024_1_)
	{
		super(p_i4024_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? workbenchIconTop : par1 == 0 ? Block.planks.getBlockTextureFromSide(par1) : par1 != 2 && par1 != 4 ? blockIcon : workbenchIconFront;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			p_71903_5_.displayGUIWorkbench(p_71903_2_, p_71903_3_, p_71903_4_);
			return true;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("workbench_side");
		workbenchIconTop = par1IconRegister.registerIcon("workbench_top");
		workbenchIconFront = par1IconRegister.registerIcon("workbench_front");
	}
}
