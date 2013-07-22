package net.minecraft.src;

import java.util.Random;

public class BlockDeadBush extends BlockFlower
{
	protected BlockDeadBush(int p_i9050_1_)
	{
		super(p_i9050_1_, Material.vine);
		float var2 = 0.4F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.8F, 0.5F + var2);
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return p_72263_1_ == Block.sand.blockID;
	}
	
	@Override public void harvestBlock(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
	{
		if(!p_71893_1_.isRemote && p_71893_2_.getCurrentEquippedItem() != null && p_71893_2_.getCurrentEquippedItem().itemID == Item.shears.itemID)
		{
			p_71893_2_.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(Block.deadBush, 1, p_71893_6_));
		} else
		{
			super.harvestBlock(p_71893_1_, p_71893_2_, p_71893_3_, p_71893_4_, p_71893_5_, p_71893_6_);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return -1;
	}
}
