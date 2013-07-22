package net.minecraft.src;

import java.util.List;

public class BlockLilyPad extends BlockFlower
{
	protected BlockLilyPad(int p_i9099_1_)
	{
		super(p_i9099_1_);
		float var2 = 0.5F;
		float var3 = 0.015625F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		if(p_71871_7_ == null || !(p_71871_7_ instanceof EntityBoat))
		{
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		return p_71854_3_ >= 0 && p_71854_3_ < 256 ? p_71854_1_.getBlockMaterial(p_71854_2_, p_71854_3_ - 1, p_71854_4_) == Material.water && p_71854_1_.getBlockMetadata(p_71854_2_, p_71854_3_ - 1, p_71854_4_) == 0 : false;
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return p_72263_1_ == Block.waterStill.blockID;
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 2129968;
	}
	
	@Override public int getBlockColor()
	{
		return 2129968;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return AxisAlignedBB.getAABBPool().getAABB(p_71872_2_ + minX, p_71872_3_ + minY, p_71872_4_ + minZ, p_71872_2_ + maxX, p_71872_3_ + maxY, p_71872_4_ + maxZ);
	}
	
	@Override public int getRenderColor(int par1)
	{
		return 2129968;
	}
	
	@Override public int getRenderType()
	{
		return 23;
	}
}
