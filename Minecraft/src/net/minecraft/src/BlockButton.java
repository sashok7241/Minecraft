package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class BlockButton extends Block
{
	private final boolean sensible;
	
	protected BlockButton(int p_i9042_1_, boolean p_i9042_2_)
	{
		super(p_i9042_1_, Material.circuits);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabRedstone);
		sensible = p_i9042_2_;
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		if((p_71852_6_ & 8) > 0)
		{
			int var7 = p_71852_6_ & 7;
			func_82536_d(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, var7);
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.isBlockNormalCube(p_71930_2_ - 1, p_71930_3_, p_71930_4_) ? true : p_71930_1_.isBlockNormalCube(p_71930_2_ + 1, p_71930_3_, p_71930_4_) ? true : p_71930_1_.isBlockNormalCube(p_71930_2_, p_71930_3_, p_71930_4_ - 1) ? true : p_71930_1_.isBlockNormalCube(p_71930_2_, p_71930_3_, p_71930_4_ + 1);
	}
	
	@Override public boolean canPlaceBlockOnSide(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
	{
		return p_71850_5_ == 2 && p_71850_1_.isBlockNormalCube(p_71850_2_, p_71850_3_, p_71850_4_ + 1) ? true : p_71850_5_ == 3 && p_71850_1_.isBlockNormalCube(p_71850_2_, p_71850_3_, p_71850_4_ - 1) ? true : p_71850_5_ == 4 && p_71850_1_.isBlockNormalCube(p_71850_2_ + 1, p_71850_3_, p_71850_4_) ? true : p_71850_5_ == 5 && p_71850_1_.isBlockNormalCube(p_71850_2_ - 1, p_71850_3_, p_71850_4_);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	private void func_82534_e(int p_82534_1_)
	{
		int var2 = p_82534_1_ & 7;
		boolean var3 = (p_82534_1_ & 8) > 0;
		float var4 = 0.375F;
		float var5 = 0.625F;
		float var6 = 0.1875F;
		float var7 = 0.125F;
		if(var3)
		{
			var7 = 0.0625F;
		}
		if(var2 == 1)
		{
			setBlockBounds(0.0F, var4, 0.5F - var6, var7, var5, 0.5F + var6);
		} else if(var2 == 2)
		{
			setBlockBounds(1.0F - var7, var4, 0.5F - var6, 1.0F, var5, 0.5F + var6);
		} else if(var2 == 3)
		{
			setBlockBounds(0.5F - var6, var4, 0.0F, 0.5F + var6, var5, var7);
		} else if(var2 == 4)
		{
			setBlockBounds(0.5F - var6, var4, 1.0F - var7, 0.5F + var6, var5, 1.0F);
		}
	}
	
	private void func_82535_o(World p_82535_1_, int p_82535_2_, int p_82535_3_, int p_82535_4_)
	{
		int var5 = p_82535_1_.getBlockMetadata(p_82535_2_, p_82535_3_, p_82535_4_);
		int var6 = var5 & 7;
		boolean var7 = (var5 & 8) != 0;
		func_82534_e(var5);
		List var9 = p_82535_1_.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getAABBPool().getAABB(p_82535_2_ + minX, p_82535_3_ + minY, p_82535_4_ + minZ, p_82535_2_ + maxX, p_82535_3_ + maxY, p_82535_4_ + maxZ));
		boolean var8 = !var9.isEmpty();
		if(var8 && !var7)
		{
			p_82535_1_.setBlockMetadataWithNotify(p_82535_2_, p_82535_3_, p_82535_4_, var6 | 8, 3);
			func_82536_d(p_82535_1_, p_82535_2_, p_82535_3_, p_82535_4_, var6);
			p_82535_1_.markBlockRangeForRenderUpdate(p_82535_2_, p_82535_3_, p_82535_4_, p_82535_2_, p_82535_3_, p_82535_4_);
			p_82535_1_.playSoundEffect(p_82535_2_ + 0.5D, p_82535_3_ + 0.5D, p_82535_4_ + 0.5D, "random.click", 0.3F, 0.6F);
		}
		if(!var8 && var7)
		{
			p_82535_1_.setBlockMetadataWithNotify(p_82535_2_, p_82535_3_, p_82535_4_, var6, 3);
			func_82536_d(p_82535_1_, p_82535_2_, p_82535_3_, p_82535_4_, var6);
			p_82535_1_.markBlockRangeForRenderUpdate(p_82535_2_, p_82535_3_, p_82535_4_, p_82535_2_, p_82535_3_, p_82535_4_);
			p_82535_1_.playSoundEffect(p_82535_2_ + 0.5D, p_82535_3_ + 0.5D, p_82535_4_ + 0.5D, "random.click", 0.3F, 0.5F);
		}
		if(var8)
		{
			p_82535_1_.scheduleBlockUpdate(p_82535_2_, p_82535_3_, p_82535_4_, blockID, tickRate(p_82535_1_));
		}
	}
	
	private void func_82536_d(World p_82536_1_, int p_82536_2_, int p_82536_3_, int p_82536_4_, int p_82536_5_)
	{
		p_82536_1_.notifyBlocksOfNeighborChange(p_82536_2_, p_82536_3_, p_82536_4_, blockID);
		if(p_82536_5_ == 1)
		{
			p_82536_1_.notifyBlocksOfNeighborChange(p_82536_2_ - 1, p_82536_3_, p_82536_4_, blockID);
		} else if(p_82536_5_ == 2)
		{
			p_82536_1_.notifyBlocksOfNeighborChange(p_82536_2_ + 1, p_82536_3_, p_82536_4_, blockID);
		} else if(p_82536_5_ == 3)
		{
			p_82536_1_.notifyBlocksOfNeighborChange(p_82536_2_, p_82536_3_, p_82536_4_ - 1, blockID);
		} else if(p_82536_5_ == 4)
		{
			p_82536_1_.notifyBlocksOfNeighborChange(p_82536_2_, p_82536_3_, p_82536_4_ + 1, blockID);
		} else
		{
			p_82536_1_.notifyBlocksOfNeighborChange(p_82536_2_, p_82536_3_ - 1, p_82536_4_, blockID);
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	private int getOrientation(World p_72260_1_, int p_72260_2_, int p_72260_3_, int p_72260_4_)
	{
		return p_72260_1_.isBlockNormalCube(p_72260_2_ - 1, p_72260_3_, p_72260_4_) ? 1 : p_72260_1_.isBlockNormalCube(p_72260_2_ + 1, p_72260_3_, p_72260_4_) ? 2 : p_72260_1_.isBlockNormalCube(p_72260_2_, p_72260_3_, p_72260_4_ - 1) ? 3 : p_72260_1_.isBlockNormalCube(p_72260_2_, p_72260_3_, p_72260_4_ + 1) ? 4 : 1;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		int var6 = p_71855_1_.getBlockMetadata(p_71855_2_, p_71855_3_, p_71855_4_);
		if((var6 & 8) == 0) return 0;
		else
		{
			int var7 = var6 & 7;
			return var7 == 5 && p_71855_5_ == 1 ? 15 : var7 == 4 && p_71855_5_ == 2 ? 15 : var7 == 3 && p_71855_5_ == 3 ? 15 : var7 == 2 && p_71855_5_ == 4 ? 15 : var7 == 1 && p_71855_5_ == 5 ? 15 : 0;
		}
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		return (p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_) & 8) > 0 ? 15 : 0;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		int var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
		int var11 = var10 & 7;
		int var12 = 8 - (var10 & 8);
		if(var12 == 0) return true;
		else
		{
			p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var11 + var12, 3);
			p_71903_1_.markBlockRangeForRenderUpdate(p_71903_2_, p_71903_3_, p_71903_4_, p_71903_2_, p_71903_3_, p_71903_4_);
			p_71903_1_.playSoundEffect(p_71903_2_ + 0.5D, p_71903_3_ + 0.5D, p_71903_4_ + 0.5D, "random.click", 0.3F, 0.6F);
			func_82536_d(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, var11);
			p_71903_1_.scheduleBlockUpdate(p_71903_2_, p_71903_3_, p_71903_4_, blockID, tickRate(p_71903_1_));
			return true;
		}
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		int var10 = p_85104_1_.getBlockMetadata(p_85104_2_, p_85104_3_, p_85104_4_);
		int var11 = var10 & 8;
		var10 &= 7;
		if(p_85104_5_ == 2 && p_85104_1_.isBlockNormalCube(p_85104_2_, p_85104_3_, p_85104_4_ + 1))
		{
			var10 = 4;
		} else if(p_85104_5_ == 3 && p_85104_1_.isBlockNormalCube(p_85104_2_, p_85104_3_, p_85104_4_ - 1))
		{
			var10 = 3;
		} else if(p_85104_5_ == 4 && p_85104_1_.isBlockNormalCube(p_85104_2_ + 1, p_85104_3_, p_85104_4_))
		{
			var10 = 2;
		} else if(p_85104_5_ == 5 && p_85104_1_.isBlockNormalCube(p_85104_2_ - 1, p_85104_3_, p_85104_4_))
		{
			var10 = 1;
		} else
		{
			var10 = getOrientation(p_85104_1_, p_85104_2_, p_85104_3_, p_85104_4_);
		}
		return var10 + var11;
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(!p_71869_1_.isRemote)
		{
			if(sensible)
			{
				if((p_71869_1_.getBlockMetadata(p_71869_2_, p_71869_3_, p_71869_4_) & 8) == 0)
				{
					func_82535_o(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_);
				}
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(redundantCanPlaceBlockAt(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_) & 7;
			boolean var7 = false;
			if(!p_71863_1_.isBlockNormalCube(p_71863_2_ - 1, p_71863_3_, p_71863_4_) && var6 == 1)
			{
				var7 = true;
			}
			if(!p_71863_1_.isBlockNormalCube(p_71863_2_ + 1, p_71863_3_, p_71863_4_) && var6 == 2)
			{
				var7 = true;
			}
			if(!p_71863_1_.isBlockNormalCube(p_71863_2_, p_71863_3_, p_71863_4_ - 1) && var6 == 3)
			{
				var7 = true;
			}
			if(!p_71863_1_.isBlockNormalCube(p_71863_2_, p_71863_3_, p_71863_4_ + 1) && var6 == 4)
			{
				var7 = true;
			}
			if(var7)
			{
				dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			}
		}
	}
	
	private boolean redundantCanPlaceBlockAt(World p_72261_1_, int p_72261_2_, int p_72261_3_, int p_72261_4_)
	{
		if(!canPlaceBlockAt(p_72261_1_, p_72261_2_, p_72261_3_, p_72261_4_))
		{
			dropBlockAsItem(p_72261_1_, p_72261_2_, p_72261_3_, p_72261_4_, p_72261_1_.getBlockMetadata(p_72261_2_, p_72261_3_, p_72261_4_), 0);
			p_72261_1_.setBlockToAir(p_72261_2_, p_72261_3_, p_72261_4_);
			return false;
		} else return true;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
		func_82534_e(var5);
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.1875F;
		float var2 = 0.125F;
		float var3 = 0.125F;
		setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return sensible ? 30 : 20;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			if((var6 & 8) != 0)
			{
				if(sensible)
				{
					func_82535_o(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
				} else
				{
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var6 & 7, 3);
					int var7 = var6 & 7;
					func_82536_d(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, var7);
					p_71847_1_.playSoundEffect(p_71847_2_ + 0.5D, p_71847_3_ + 0.5D, p_71847_4_ + 0.5D, "random.click", 0.3F, 0.5F);
					p_71847_1_.markBlockRangeForRenderUpdate(p_71847_2_, p_71847_3_, p_71847_4_, p_71847_2_, p_71847_3_, p_71847_4_);
				}
			}
		}
	}
}
