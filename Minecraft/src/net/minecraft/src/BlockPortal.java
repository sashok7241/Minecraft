package net.minecraft.src;

import java.util.Random;

public class BlockPortal extends BlockBreakable
{
	public BlockPortal(int p_i9077_1_)
	{
		super(p_i9077_1_, "portal", Material.portal, false);
		setTickRandomly(true);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(p_71869_5_.ridingEntity == null && p_71869_5_.riddenByEntity == null)
		{
			p_71869_5_.setInPortal();
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		byte var6 = 0;
		byte var7 = 1;
		if(p_71863_1_.getBlockId(p_71863_2_ - 1, p_71863_3_, p_71863_4_) == blockID || p_71863_1_.getBlockId(p_71863_2_ + 1, p_71863_3_, p_71863_4_) == blockID)
		{
			var6 = 1;
			var7 = 0;
		}
		int var8;
		for(var8 = p_71863_3_; p_71863_1_.getBlockId(p_71863_2_, var8 - 1, p_71863_4_) == blockID; --var8)
		{
			;
		}
		if(p_71863_1_.getBlockId(p_71863_2_, var8 - 1, p_71863_4_) != Block.obsidian.blockID)
		{
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		} else
		{
			int var9;
			for(var9 = 1; var9 < 4 && p_71863_1_.getBlockId(p_71863_2_, var8 + var9, p_71863_4_) == blockID; ++var9)
			{
				;
			}
			if(var9 == 3 && p_71863_1_.getBlockId(p_71863_2_, var8 + var9, p_71863_4_) == Block.obsidian.blockID)
			{
				boolean var10 = p_71863_1_.getBlockId(p_71863_2_ - 1, p_71863_3_, p_71863_4_) == blockID || p_71863_1_.getBlockId(p_71863_2_ + 1, p_71863_3_, p_71863_4_) == blockID;
				boolean var11 = p_71863_1_.getBlockId(p_71863_2_, p_71863_3_, p_71863_4_ - 1) == blockID || p_71863_1_.getBlockId(p_71863_2_, p_71863_3_, p_71863_4_ + 1) == blockID;
				if(var10 && var11)
				{
					p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
				} else
				{
					if((p_71863_1_.getBlockId(p_71863_2_ + var6, p_71863_3_, p_71863_4_ + var7) != Block.obsidian.blockID || p_71863_1_.getBlockId(p_71863_2_ - var6, p_71863_3_, p_71863_4_ - var7) != blockID) && (p_71863_1_.getBlockId(p_71863_2_ - var6, p_71863_3_, p_71863_4_ - var7) != Block.obsidian.blockID || p_71863_1_.getBlockId(p_71863_2_ + var6, p_71863_3_, p_71863_4_ + var7) != blockID))
					{
						p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
					}
				}
			} else
			{
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			}
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par5Random.nextInt(100) == 0)
		{
			par1World.playSound(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F, false);
		}
		for(int var6 = 0; var6 < 4; ++var6)
		{
			double var7 = par2 + par5Random.nextFloat();
			double var9 = par3 + par5Random.nextFloat();
			double var11 = par4 + par5Random.nextFloat();
			double var13 = 0.0D;
			double var15 = 0.0D;
			double var17 = 0.0D;
			int var19 = par5Random.nextInt(2) * 2 - 1;
			var13 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			var15 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			var17 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			if(par1World.getBlockId(par2 - 1, par3, par4) != blockID && par1World.getBlockId(par2 + 1, par3, par4) != blockID)
			{
				var7 = par2 + 0.5D + 0.25D * var19;
				var13 = par5Random.nextFloat() * 2.0F * var19;
			} else
			{
				var11 = par4 + 0.5D + 0.25D * var19;
				var17 = par5Random.nextFloat() * 2.0F * var19;
			}
			par1World.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		float var5;
		float var6;
		if(p_71902_1_.getBlockId(p_71902_2_ - 1, p_71902_3_, p_71902_4_) != blockID && p_71902_1_.getBlockId(p_71902_2_ + 1, p_71902_3_, p_71902_4_) != blockID)
		{
			var5 = 0.125F;
			var6 = 0.5F;
			setBlockBounds(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
		} else
		{
			var5 = 0.5F;
			var6 = 0.125F;
			setBlockBounds(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
		}
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par1IBlockAccess.getBlockId(par2, par3, par4) == blockID) return false;
		else
		{
			boolean var6 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == blockID && par1IBlockAccess.getBlockId(par2 - 2, par3, par4) != blockID;
			boolean var7 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == blockID && par1IBlockAccess.getBlockId(par2 + 2, par3, par4) != blockID;
			boolean var8 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == blockID && par1IBlockAccess.getBlockId(par2, par3, par4 - 2) != blockID;
			boolean var9 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == blockID && par1IBlockAccess.getBlockId(par2, par3, par4 + 2) != blockID;
			boolean var10 = var6 || var7;
			boolean var11 = var8 || var9;
			return var10 && par5 == 4 ? true : var10 && par5 == 5 ? true : var11 && par5 == 2 ? true : var11 && par5 == 3;
		}
	}
	
	public boolean tryToCreatePortal(World p_72246_1_, int p_72246_2_, int p_72246_3_, int p_72246_4_)
	{
		byte var5 = 0;
		byte var6 = 0;
		if(p_72246_1_.getBlockId(p_72246_2_ - 1, p_72246_3_, p_72246_4_) == Block.obsidian.blockID || p_72246_1_.getBlockId(p_72246_2_ + 1, p_72246_3_, p_72246_4_) == Block.obsidian.blockID)
		{
			var5 = 1;
		}
		if(p_72246_1_.getBlockId(p_72246_2_, p_72246_3_, p_72246_4_ - 1) == Block.obsidian.blockID || p_72246_1_.getBlockId(p_72246_2_, p_72246_3_, p_72246_4_ + 1) == Block.obsidian.blockID)
		{
			var6 = 1;
		}
		if(var5 == var6) return false;
		else
		{
			if(p_72246_1_.getBlockId(p_72246_2_ - var5, p_72246_3_, p_72246_4_ - var6) == 0)
			{
				p_72246_2_ -= var5;
				p_72246_4_ -= var6;
			}
			int var7;
			int var8;
			for(var7 = -1; var7 <= 2; ++var7)
			{
				for(var8 = -1; var8 <= 3; ++var8)
				{
					boolean var9 = var7 == -1 || var7 == 2 || var8 == -1 || var8 == 3;
					if(var7 != -1 && var7 != 2 || var8 != -1 && var8 != 3)
					{
						int var10 = p_72246_1_.getBlockId(p_72246_2_ + var5 * var7, p_72246_3_ + var8, p_72246_4_ + var6 * var7);
						if(var9)
						{
							if(var10 != Block.obsidian.blockID) return false;
						} else if(var10 != 0 && var10 != Block.fire.blockID) return false;
					}
				}
			}
			for(var7 = 0; var7 < 2; ++var7)
			{
				for(var8 = 0; var8 < 3; ++var8)
				{
					p_72246_1_.setBlock(p_72246_2_ + var5 * var7, p_72246_3_ + var8, p_72246_4_ + var6 * var7, Block.portal.blockID, 0, 2);
				}
			}
			return true;
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		super.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
		if(p_71847_1_.provider.isSurfaceWorld() && p_71847_5_.nextInt(2000) < p_71847_1_.difficultySetting)
		{
			int var6;
			for(var6 = p_71847_3_; !p_71847_1_.doesBlockHaveSolidTopSurface(p_71847_2_, var6, p_71847_4_) && var6 > 0; --var6)
			{
				;
			}
			if(var6 > 0 && !p_71847_1_.isBlockNormalCube(p_71847_2_, var6 + 1, p_71847_4_))
			{
				Entity var7 = ItemMonsterPlacer.spawnCreature(p_71847_1_, 57, p_71847_2_ + 0.5D, var6 + 1.1D, p_71847_4_ + 0.5D);
				if(var7 != null)
				{
					var7.timeUntilPortal = var7.getPortalCooldown();
				}
			}
		}
	}
}
