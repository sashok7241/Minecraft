package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockStep extends BlockHalfSlab
{
	public static final String[] blockStepTypes = new String[] { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
	private Icon theIcon;
	
	public BlockStep(int p_i4000_1_, boolean p_i4000_2_)
	{
		super(p_i4000_1_, p_i4000_2_, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return new ItemStack(Block.stoneSingleSlab.blockID, 2, p_71880_1_ & 7);
	}
	
	@Override public String getFullSlabName(int p_72240_1_)
	{
		if(p_72240_1_ < 0 || p_72240_1_ >= blockStepTypes.length)
		{
			p_72240_1_ = 0;
		}
		return super.getUnlocalizedName() + "." + blockStepTypes[p_72240_1_];
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = par2 & 7;
		if(isDoubleSlab && (par2 & 8) != 0)
		{
			par1 = 1;
		}
		return var3 == 0 ? par1 != 1 && par1 != 0 ? theIcon : blockIcon : var3 == 1 ? Block.sandStone.getBlockTextureFromSide(par1) : var3 == 2 ? Block.planks.getBlockTextureFromSide(par1) : var3 == 3 ? Block.cobblestone.getBlockTextureFromSide(par1) : var3 == 4 ? Block.brick.getBlockTextureFromSide(par1) : var3 == 5 ? Block.stoneBrick.getIcon(par1, 0) : var3 == 6 ? Block.netherBrick.getBlockTextureFromSide(1) : var3 == 7 ? Block.blockNetherQuartz.getBlockTextureFromSide(par1) : blockIcon;
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if(par1 != Block.stoneDoubleSlab.blockID)
		{
			for(int var4 = 0; var4 <= 7; ++var4)
			{
				if(var4 != 2)
				{
					par3List.add(new ItemStack(par1, 1, var4));
				}
			}
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.stoneSingleSlab.blockID;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("stoneslab_top");
		theIcon = par1IconRegister.registerIcon("stoneslab_side");
	}
}
