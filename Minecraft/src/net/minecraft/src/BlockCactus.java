package net.minecraft.src;

import java.util.Random;

public class BlockCactus extends Block
{
	private Icon cactusTopIcon;
	private Icon cactusBottomIcon;
	
	protected BlockCactus(int p_i9043_1_)
	{
		super(p_i9043_1_, Material.cactus);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		if(p_71854_1_.getBlockMaterial(p_71854_2_ - 1, p_71854_3_, p_71854_4_).isSolid()) return false;
		else if(p_71854_1_.getBlockMaterial(p_71854_2_ + 1, p_71854_3_, p_71854_4_).isSolid()) return false;
		else if(p_71854_1_.getBlockMaterial(p_71854_2_, p_71854_3_, p_71854_4_ - 1).isSolid()) return false;
		else if(p_71854_1_.getBlockMaterial(p_71854_2_, p_71854_3_, p_71854_4_ + 1).isSolid()) return false;
		else
		{
			int var5 = p_71854_1_.getBlockId(p_71854_2_, p_71854_3_ - 1, p_71854_4_);
			return var5 == Block.cactus.blockID || var5 == Block.sand.blockID;
		}
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return !super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) ? false : canBlockStay(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(p_71872_2_ + var5, p_71872_3_, p_71872_4_ + var5, p_71872_2_ + 1 - var5, p_71872_3_ + 1 - var5, p_71872_4_ + 1 - var5);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? cactusTopIcon : par1 == 0 ? cactusBottomIcon : blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 13;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1, par4 + 1 - var5);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		p_71869_5_.attackEntityFrom(DamageSource.cactus, 1);
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!canBlockStay(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			p_71863_1_.destroyBlock(p_71863_2_, p_71863_3_, p_71863_4_, true);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("cactus_side");
		cactusTopIcon = par1IconRegister.registerIcon("cactus_top");
		cactusBottomIcon = par1IconRegister.registerIcon("cactus_bottom");
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
					onNeighborBlockChange(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_, blockID);
				} else
				{
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var7 + 1, 4);
				}
			}
		}
	}
}
