package net.minecraft.src;

import java.util.Random;

public class BlockFlower extends Block
{
	protected BlockFlower(int p_i9041_1_)
	{
		this(p_i9041_1_, Material.plants);
	}
	
	protected BlockFlower(int p_i9040_1_, Material p_i9040_2_)
	{
		super(p_i9040_1_, p_i9040_2_);
		setTickRandomly(true);
		float var3 = 0.2F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		return (p_71854_1_.getFullBlockLightValue(p_71854_2_, p_71854_3_, p_71854_4_) >= 8 || p_71854_1_.canBlockSeeTheSky(p_71854_2_, p_71854_3_, p_71854_4_)) && canThisPlantGrowOnThisBlockID(p_71854_1_.getBlockId(p_71854_2_, p_71854_3_ - 1, p_71854_4_));
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) && canThisPlantGrowOnThisBlockID(p_71930_1_.getBlockId(p_71930_2_, p_71930_3_ - 1, p_71930_4_));
	}
	
	protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return p_72263_1_ == Block.grass.blockID || p_72263_1_ == Block.dirt.blockID || p_72263_1_ == Block.tilledField.blockID;
	}
	
	protected final void checkFlowerChange(World p_72262_1_, int p_72262_2_, int p_72262_3_, int p_72262_4_)
	{
		if(!canBlockStay(p_72262_1_, p_72262_2_, p_72262_3_, p_72262_4_))
		{
			dropBlockAsItem(p_72262_1_, p_72262_2_, p_72262_3_, p_72262_4_, p_72262_1_.getBlockMetadata(p_72262_2_, p_72262_3_, p_72262_4_), 0);
			p_72262_1_.setBlockToAir(p_72262_2_, p_72262_3_, p_72262_4_);
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getRenderType()
	{
		return 1;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
		checkFlowerChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		checkFlowerChange(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
	}
}
