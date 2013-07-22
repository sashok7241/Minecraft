package net.minecraft.src;

import java.util.Random;

public class BlockTorch extends Block
{
	protected BlockTorch(int p_i9097_1_)
	{
		super(p_i9097_1_, Material.circuits);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.isBlockNormalCubeDefault(p_71930_2_ - 1, p_71930_3_, p_71930_4_, true) ? true : p_71930_1_.isBlockNormalCubeDefault(p_71930_2_ + 1, p_71930_3_, p_71930_4_, true) ? true : p_71930_1_.isBlockNormalCubeDefault(p_71930_2_, p_71930_3_, p_71930_4_ - 1, true) ? true : p_71930_1_.isBlockNormalCubeDefault(p_71930_2_, p_71930_3_, p_71930_4_ + 1, true) ? true : canPlaceTorchOn(p_71930_1_, p_71930_2_, p_71930_3_ - 1, p_71930_4_);
	}
	
	private boolean canPlaceTorchOn(World p_72125_1_, int p_72125_2_, int p_72125_3_, int p_72125_4_)
	{
		if(p_72125_1_.doesBlockHaveSolidTopSurface(p_72125_2_, p_72125_3_, p_72125_4_)) return true;
		else
		{
			int var5 = p_72125_1_.getBlockId(p_72125_2_, p_72125_3_, p_72125_4_);
			return var5 == Block.fence.blockID || var5 == Block.netherFence.blockID || var5 == Block.glass.blockID || var5 == Block.cobblestoneWall.blockID;
		}
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World p_71878_1_, int p_71878_2_, int p_71878_3_, int p_71878_4_, Vec3 p_71878_5_, Vec3 p_71878_6_)
	{
		int var7 = p_71878_1_.getBlockMetadata(p_71878_2_, p_71878_3_, p_71878_4_) & 7;
		float var8 = 0.15F;
		if(var7 == 1)
		{
			setBlockBounds(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
		} else if(var7 == 2)
		{
			setBlockBounds(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
		} else if(var7 == 3)
		{
			setBlockBounds(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
		} else if(var7 == 4)
		{
			setBlockBounds(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
		} else
		{
			var8 = 0.1F;
			setBlockBounds(0.5F - var8, 0.0F, 0.5F - var8, 0.5F + var8, 0.6F, 0.5F + var8);
		}
		return super.collisionRayTrace(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_, p_71878_5_, p_71878_6_);
	}
	
	protected boolean dropTorchIfCantStay(World p_72126_1_, int p_72126_2_, int p_72126_3_, int p_72126_4_)
	{
		if(!canPlaceBlockAt(p_72126_1_, p_72126_2_, p_72126_3_, p_72126_4_))
		{
			if(p_72126_1_.getBlockId(p_72126_2_, p_72126_3_, p_72126_4_) == blockID)
			{
				dropBlockAsItem(p_72126_1_, p_72126_2_, p_72126_3_, p_72126_4_, p_72126_1_.getBlockMetadata(p_72126_2_, p_72126_3_, p_72126_4_), 0);
				p_72126_1_.setBlockToAir(p_72126_2_, p_72126_3_, p_72126_4_);
			}
			return false;
		} else return true;
	}
	
	protected boolean func_94397_d(World p_94397_1_, int p_94397_2_, int p_94397_3_, int p_94397_4_, int p_94397_5_)
	{
		if(dropTorchIfCantStay(p_94397_1_, p_94397_2_, p_94397_3_, p_94397_4_))
		{
			int var6 = p_94397_1_.getBlockMetadata(p_94397_2_, p_94397_3_, p_94397_4_);
			boolean var7 = false;
			if(!p_94397_1_.isBlockNormalCubeDefault(p_94397_2_ - 1, p_94397_3_, p_94397_4_, true) && var6 == 1)
			{
				var7 = true;
			}
			if(!p_94397_1_.isBlockNormalCubeDefault(p_94397_2_ + 1, p_94397_3_, p_94397_4_, true) && var6 == 2)
			{
				var7 = true;
			}
			if(!p_94397_1_.isBlockNormalCubeDefault(p_94397_2_, p_94397_3_, p_94397_4_ - 1, true) && var6 == 3)
			{
				var7 = true;
			}
			if(!p_94397_1_.isBlockNormalCubeDefault(p_94397_2_, p_94397_3_, p_94397_4_ + 1, true) && var6 == 4)
			{
				var7 = true;
			}
			if(!canPlaceTorchOn(p_94397_1_, p_94397_2_, p_94397_3_ - 1, p_94397_4_) && var6 == 5)
			{
				var7 = true;
			}
			if(var7)
			{
				dropBlockAsItem(p_94397_1_, p_94397_2_, p_94397_3_, p_94397_4_, p_94397_1_.getBlockMetadata(p_94397_2_, p_94397_3_, p_94397_4_), 0);
				p_94397_1_.setBlockToAir(p_94397_2_, p_94397_3_, p_94397_4_);
				return true;
			} else return false;
		} else return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getRenderType()
	{
		return 2;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(p_71861_1_.getBlockMetadata(p_71861_2_, p_71861_3_, p_71861_4_) == 0)
		{
			if(p_71861_1_.isBlockNormalCubeDefault(p_71861_2_ - 1, p_71861_3_, p_71861_4_, true))
			{
				p_71861_1_.setBlockMetadataWithNotify(p_71861_2_, p_71861_3_, p_71861_4_, 1, 2);
			} else if(p_71861_1_.isBlockNormalCubeDefault(p_71861_2_ + 1, p_71861_3_, p_71861_4_, true))
			{
				p_71861_1_.setBlockMetadataWithNotify(p_71861_2_, p_71861_3_, p_71861_4_, 2, 2);
			} else if(p_71861_1_.isBlockNormalCubeDefault(p_71861_2_, p_71861_3_, p_71861_4_ - 1, true))
			{
				p_71861_1_.setBlockMetadataWithNotify(p_71861_2_, p_71861_3_, p_71861_4_, 3, 2);
			} else if(p_71861_1_.isBlockNormalCubeDefault(p_71861_2_, p_71861_3_, p_71861_4_ + 1, true))
			{
				p_71861_1_.setBlockMetadataWithNotify(p_71861_2_, p_71861_3_, p_71861_4_, 4, 2);
			} else if(canPlaceTorchOn(p_71861_1_, p_71861_2_, p_71861_3_ - 1, p_71861_4_))
			{
				p_71861_1_.setBlockMetadataWithNotify(p_71861_2_, p_71861_3_, p_71861_4_, 5, 2);
			}
		}
		dropTorchIfCantStay(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		int var10 = p_85104_9_;
		if(p_85104_5_ == 1 && canPlaceTorchOn(p_85104_1_, p_85104_2_, p_85104_3_ - 1, p_85104_4_))
		{
			var10 = 5;
		}
		if(p_85104_5_ == 2 && p_85104_1_.isBlockNormalCubeDefault(p_85104_2_, p_85104_3_, p_85104_4_ + 1, true))
		{
			var10 = 4;
		}
		if(p_85104_5_ == 3 && p_85104_1_.isBlockNormalCubeDefault(p_85104_2_, p_85104_3_, p_85104_4_ - 1, true))
		{
			var10 = 3;
		}
		if(p_85104_5_ == 4 && p_85104_1_.isBlockNormalCubeDefault(p_85104_2_ + 1, p_85104_3_, p_85104_4_, true))
		{
			var10 = 2;
		}
		if(p_85104_5_ == 5 && p_85104_1_.isBlockNormalCubeDefault(p_85104_2_ - 1, p_85104_3_, p_85104_4_, true))
		{
			var10 = 1;
		}
		return var10;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		func_94397_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		double var7 = par2 + 0.5F;
		double var9 = par3 + 0.7F;
		double var11 = par4 + 0.5F;
		double var13 = 0.2199999988079071D;
		double var15 = 0.27000001072883606D;
		if(var6 == 1)
		{
			par1World.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
		} else if(var6 == 2)
		{
			par1World.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
		} else if(var6 == 3)
		{
			par1World.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
		} else if(var6 == 4)
		{
			par1World.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
		} else
		{
			par1World.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle("flame", var7, var9, var11, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		super.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
		if(p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_) == 0)
		{
			onBlockAdded(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
		}
	}
}
