package net.minecraft.src;

public class BlockPoweredOre extends BlockOreStorage
{
	public BlockPoweredOre(int p_i9013_1_)
	{
		super(p_i9013_1_);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		return 15;
	}
}
