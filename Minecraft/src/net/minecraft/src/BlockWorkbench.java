package net.minecraft.src;

public class BlockWorkbench extends Block
{
	private Icon workbenchIconTop;
	private Icon workbenchIconFront;
	
	protected BlockWorkbench(int par1)
	{
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? workbenchIconTop : par1 == 0 ? Block.planks.getBlockTextureFromSide(par1) : par1 != 2 && par1 != 4 ? blockIcon : workbenchIconFront;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			par5EntityPlayer.displayGUIWorkbench(par2, par3, par4);
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
