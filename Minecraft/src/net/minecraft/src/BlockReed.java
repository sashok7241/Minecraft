package net.minecraft.src;

import java.util.Random;

public class BlockReed extends Block
{
	protected BlockReed(int p_i9087_1_)
	{
		super(p_i9087_1_, Material.plants);
		float var2 = 0.375F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 1.0F, 0.5F + var2);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		return canPlaceBlockAt(p_71854_1_, p_71854_2_, p_71854_3_, p_71854_4_);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		int var5 = p_71930_1_.getBlockId(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
		return var5 == blockID ? true : var5 != Block.grass.blockID && var5 != Block.dirt.blockID && var5 != Block.sand.blockID ? false : p_71930_1_.getBlockMaterial(p_71930_2_ - 1, p_71930_3_ - 1, p_71930_4_) == Material.water ? true : p_71930_1_.getBlockMaterial(p_71930_2_ + 1, p_71930_3_ - 1, p_71930_4_) == Material.water ? true : p_71930_1_.getBlockMaterial(p_71930_2_, p_71930_3_ - 1, p_71930_4_ - 1) == Material.water ? true : p_71930_1_.getBlockMaterial(p_71930_2_, p_71930_3_ - 1, p_71930_4_ + 1) == Material.water;
	}
	
	protected final void checkBlockCoordValid(World p_72167_1_, int p_72167_2_, int p_72167_3_, int p_72167_4_)
	{
		if(!canBlockStay(p_72167_1_, p_72167_2_, p_72167_3_, p_72167_4_))
		{
			dropBlockAsItem(p_72167_1_, p_72167_2_, p_72167_3_, p_72167_4_, p_72167_1_.getBlockMetadata(p_72167_2_, p_72167_3_, p_72167_4_), 0);
			p_72167_1_.setBlockToAir(p_72167_2_, p_72167_3_, p_72167_4_);
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
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.reed.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.reed.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		checkBlockCoordValid(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(p_71847_1_.isAirBlock(p_71847_2_, p_71847_3_ + 1, p_71847_4_))
		{
			int var6;
			for(var6 = 1; p_71847_1_.getBlockId(p_71847_2_, p_71847_3_ - var6, p_71847_4_) == blockID; ++var6)
			{
				;
			}
			if(var6 < 3)
			{
				int var7 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
				if(var7 == 15)
				{
					p_71847_1_.setBlock(p_71847_2_, p_71847_3_ + 1, p_71847_4_, blockID);
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, 0, 4);
				} else
				{
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var7 + 1, 4);
				}
			}
		}
	}
}
