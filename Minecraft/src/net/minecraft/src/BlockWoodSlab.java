package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockWoodSlab extends BlockHalfSlab
{
	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };
	
	public BlockWoodSlab(int p_i4022_1_, boolean p_i4022_2_)
	{
		super(p_i4022_1_, p_i4022_2_, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return new ItemStack(Block.woodSingleSlab.blockID, 2, p_71880_1_ & 7);
	}
	
	@Override public String getFullSlabName(int p_72240_1_)
	{
		if(p_72240_1_ < 0 || p_72240_1_ >= woodType.length)
		{
			p_72240_1_ = 0;
		}
		return super.getUnlocalizedName() + "." + woodType[p_72240_1_];
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getIcon(par1, par2 & 7);
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if(par1 != Block.woodDoubleSlab.blockID)
		{
			for(int var4 = 0; var4 < 4; ++var4)
			{
				par3List.add(new ItemStack(par1, 1, var4));
			}
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.woodSingleSlab.blockID;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
}
