package net.minecraft.src;

public class BlockHay extends BlockRotatedPillar
{
	public BlockHay(int par1)
	{
		super(par1, Material.grass);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override protected Icon func_111048_c(int par1)
	{
		return blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 31;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_111051_a = par1IconRegister.registerIcon(func_111023_E() + "_top");
		blockIcon = par1IconRegister.registerIcon(func_111023_E() + "_side");
	}
}
