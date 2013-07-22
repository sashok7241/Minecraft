package net.minecraft.src;

import java.util.Random;

public class BlockDragonEgg extends Block
{
	public BlockDragonEgg(int par1)
	{
		super(par1, Material.dragonEgg);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
	}
	
	private void fallIfPossible(World par1World, int par2, int par3, int par4)
	{
		if(BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
		{
			byte var5 = 32;
			if(!BlockSand.fallInstantly && par1World.checkChunksExist(par2 - var5, par3 - var5, par4 - var5, par2 + var5, par3 + var5, par4 + var5))
			{
				EntityFallingSand var6 = new EntityFallingSand(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, blockID);
				par1World.spawnEntityInWorld(var6);
			} else
			{
				par1World.setBlockToAir(par2, par3, par4);
				while(BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
				{
					--par3;
				}
				if(par3 > 0)
				{
					par1World.setBlock(par2, par3, par4, blockID, 0, 2);
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
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		teleportNearby(par1World, par2, par3, par4);
		return true;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
	}
	
	@Override public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		teleportNearby(par1World, par2, par3, par4);
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}
	
	private void teleportNearby(World par1World, int par2, int par3, int par4)
	{
		if(par1World.getBlockId(par2, par3, par4) == blockID)
		{
			for(int var5 = 0; var5 < 1000; ++var5)
			{
				int var6 = par2 + par1World.rand.nextInt(16) - par1World.rand.nextInt(16);
				int var7 = par3 + par1World.rand.nextInt(8) - par1World.rand.nextInt(8);
				int var8 = par4 + par1World.rand.nextInt(16) - par1World.rand.nextInt(16);
				if(par1World.getBlockId(var6, var7, var8) == 0)
				{
					if(!par1World.isRemote)
					{
						par1World.setBlock(var6, var7, var8, blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
						par1World.setBlockToAir(par2, par3, par4);
					} else
					{
						short var9 = 128;
						for(int var10 = 0; var10 < var9; ++var10)
						{
							double var11 = par1World.rand.nextDouble();
							float var13 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
							float var14 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
							float var15 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
							double var16 = var6 + (par2 - var6) * var11 + (par1World.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
							double var18 = var7 + (par3 - var7) * var11 + par1World.rand.nextDouble() * 1.0D - 0.5D;
							double var20 = var8 + (par4 - var8) * var11 + (par1World.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
							par1World.spawnParticle("portal", var16, var18, var20, var13, var14, var15);
						}
					}
					return;
				}
			}
		}
	}
	
	@Override public int tickRate(World par1World)
	{
		return 5;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		fallIfPossible(par1World, par2, par3, par4);
	}
}
