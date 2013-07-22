package net.minecraft.src;

import java.util.Random;

public class BlockLadder extends Block
{
	protected BlockLadder(int p_i9067_1_)
	{
		super(p_i9067_1_, Material.circuits);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.isBlockNormalCube(p_71930_2_ - 1, p_71930_3_, p_71930_4_) ? true : p_71930_1_.isBlockNormalCube(p_71930_2_ + 1, p_71930_3_, p_71930_4_) ? true : p_71930_1_.isBlockNormalCube(p_71930_2_, p_71930_3_, p_71930_4_ - 1) ? true : p_71930_1_.isBlockNormalCube(p_71930_2_, p_71930_3_, p_71930_4_ + 1);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
	}
	
	@Override public int getRenderType()
	{
		return 8;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		int var10 = p_85104_9_;
		if((p_85104_9_ == 0 || p_85104_5_ == 2) && p_85104_1_.isBlockNormalCube(p_85104_2_, p_85104_3_, p_85104_4_ + 1))
		{
			var10 = 2;
		}
		if((var10 == 0 || p_85104_5_ == 3) && p_85104_1_.isBlockNormalCube(p_85104_2_, p_85104_3_, p_85104_4_ - 1))
		{
			var10 = 3;
		}
		if((var10 == 0 || p_85104_5_ == 4) && p_85104_1_.isBlockNormalCube(p_85104_2_ + 1, p_85104_3_, p_85104_4_))
		{
			var10 = 4;
		}
		if((var10 == 0 || p_85104_5_ == 5) && p_85104_1_.isBlockNormalCube(p_85104_2_ - 1, p_85104_3_, p_85104_4_))
		{
			var10 = 5;
		}
		return var10;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
		boolean var7 = false;
		if(var6 == 2 && p_71863_1_.isBlockNormalCube(p_71863_2_, p_71863_3_, p_71863_4_ + 1))
		{
			var7 = true;
		}
		if(var6 == 3 && p_71863_1_.isBlockNormalCube(p_71863_2_, p_71863_3_, p_71863_4_ - 1))
		{
			var7 = true;
		}
		if(var6 == 4 && p_71863_1_.isBlockNormalCube(p_71863_2_ + 1, p_71863_3_, p_71863_4_))
		{
			var7 = true;
		}
		if(var6 == 5 && p_71863_1_.isBlockNormalCube(p_71863_2_ - 1, p_71863_3_, p_71863_4_))
		{
			var7 = true;
		}
		if(!var7)
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var6, 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
		super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		updateLadderBounds(p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_));
	}
	
	public void updateLadderBounds(int p_85107_1_)
	{
		float var3 = 0.125F;
		if(p_85107_1_ == 2)
		{
			setBlockBounds(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
		}
		if(p_85107_1_ == 3)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
		}
		if(p_85107_1_ == 4)
		{
			setBlockBounds(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		if(p_85107_1_ == 5)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
		}
	}
}
