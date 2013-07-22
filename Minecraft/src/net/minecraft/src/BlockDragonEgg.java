package net.minecraft.src;

import java.util.Random;

public class BlockDragonEgg extends Block
{
	public BlockDragonEgg(int p_i9053_1_)
	{
		super(p_i9053_1_, Material.dragonEgg);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
	}
	
	private void fallIfPossible(World p_72236_1_, int p_72236_2_, int p_72236_3_, int p_72236_4_)
	{
		if(BlockSand.canFallBelow(p_72236_1_, p_72236_2_, p_72236_3_ - 1, p_72236_4_) && p_72236_3_ >= 0)
		{
			byte var5 = 32;
			if(!BlockSand.fallInstantly && p_72236_1_.checkChunksExist(p_72236_2_ - var5, p_72236_3_ - var5, p_72236_4_ - var5, p_72236_2_ + var5, p_72236_3_ + var5, p_72236_4_ + var5))
			{
				EntityFallingSand var6 = new EntityFallingSand(p_72236_1_, p_72236_2_ + 0.5F, p_72236_3_ + 0.5F, p_72236_4_ + 0.5F, blockID);
				p_72236_1_.spawnEntityInWorld(var6);
			} else
			{
				p_72236_1_.setBlockToAir(p_72236_2_, p_72236_3_, p_72236_4_);
				while(BlockSand.canFallBelow(p_72236_1_, p_72236_2_, p_72236_3_ - 1, p_72236_4_) && p_72236_3_ > 0)
				{
					--p_72236_3_;
				}
				if(p_72236_3_ > 0)
				{
					p_72236_1_.setBlock(p_72236_2_, p_72236_3_, p_72236_4_, blockID, 0, 2);
				}
			}
		}
	}
	
	@Override public int getRenderType()
	{
		return 27;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		teleportNearby(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
		return true;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		p_71861_1_.scheduleBlockUpdate(p_71861_2_, p_71861_3_, p_71861_4_, blockID, tickRate(p_71861_1_));
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
		teleportNearby(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_);
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		p_71863_1_.scheduleBlockUpdate(p_71863_2_, p_71863_3_, p_71863_4_, blockID, tickRate(p_71863_1_));
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}
	
	private void teleportNearby(World p_72237_1_, int p_72237_2_, int p_72237_3_, int p_72237_4_)
	{
		if(p_72237_1_.getBlockId(p_72237_2_, p_72237_3_, p_72237_4_) == blockID)
		{
			for(int var5 = 0; var5 < 1000; ++var5)
			{
				int var6 = p_72237_2_ + p_72237_1_.rand.nextInt(16) - p_72237_1_.rand.nextInt(16);
				int var7 = p_72237_3_ + p_72237_1_.rand.nextInt(8) - p_72237_1_.rand.nextInt(8);
				int var8 = p_72237_4_ + p_72237_1_.rand.nextInt(16) - p_72237_1_.rand.nextInt(16);
				if(p_72237_1_.getBlockId(var6, var7, var8) == 0)
				{
					if(!p_72237_1_.isRemote)
					{
						p_72237_1_.setBlock(var6, var7, var8, blockID, p_72237_1_.getBlockMetadata(p_72237_2_, p_72237_3_, p_72237_4_), 2);
						p_72237_1_.setBlockToAir(p_72237_2_, p_72237_3_, p_72237_4_);
					} else
					{
						short var9 = 128;
						for(int var10 = 0; var10 < var9; ++var10)
						{
							double var11 = p_72237_1_.rand.nextDouble();
							float var13 = (p_72237_1_.rand.nextFloat() - 0.5F) * 0.2F;
							float var14 = (p_72237_1_.rand.nextFloat() - 0.5F) * 0.2F;
							float var15 = (p_72237_1_.rand.nextFloat() - 0.5F) * 0.2F;
							double var16 = var6 + (p_72237_2_ - var6) * var11 + (p_72237_1_.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
							double var18 = var7 + (p_72237_3_ - var7) * var11 + p_72237_1_.rand.nextDouble() * 1.0D - 0.5D;
							double var20 = var8 + (p_72237_4_ - var8) * var11 + (p_72237_1_.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
							p_72237_1_.spawnParticle("portal", var16, var18, var20, var13, var14, var15);
						}
					}
					return;
				}
			}
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 5;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		fallIfPossible(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
	}
}
