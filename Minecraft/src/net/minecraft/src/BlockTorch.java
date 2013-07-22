package net.minecraft.src;

import java.util.Random;

public class BlockTorch extends Block
{
	protected BlockTorch(int par1)
	{
		super(par1, Material.circuits);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) ? true : par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) ? true : par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) ? true : par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) ? true : canPlaceTorchOn(par1World, par2, par3 - 1, par4);
	}
	
	private boolean canPlaceTorchOn(World par1World, int par2, int par3, int par4)
	{
		if(par1World.doesBlockHaveSolidTopSurface(par2, par3, par4)) return true;
		else
		{
			int var5 = par1World.getBlockId(par2, par3, par4);
			return var5 == Block.fence.blockID || var5 == Block.netherFence.blockID || var5 == Block.glass.blockID || var5 == Block.cobblestoneWall.blockID;
		}
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		int var7 = par1World.getBlockMetadata(par2, par3, par4) & 7;
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
		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}
	
	protected boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
	{
		if(!canPlaceBlockAt(par1World, par2, par3, par4))
		{
			if(par1World.getBlockId(par2, par3, par4) == blockID)
			{
				dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
			}
			return false;
		} else return true;
	}
	
	protected boolean func_94397_d(World par1World, int par2, int par3, int par4, int par5)
	{
		if(dropTorchIfCantStay(par1World, par2, par3, par4))
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			boolean var7 = false;
			if(!par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) && var6 == 1)
			{
				var7 = true;
			}
			if(!par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) && var6 == 2)
			{
				var7 = true;
			}
			if(!par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) && var6 == 3)
			{
				var7 = true;
			}
			if(!par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) && var6 == 4)
			{
				var7 = true;
			}
			if(!canPlaceTorchOn(par1World, par2, par3 - 1, par4) && var6 == 5)
			{
				var7 = true;
			}
			if(var7)
			{
				dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
				return true;
			} else return false;
		} else return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(par1World.getBlockMetadata(par2, par3, par4) == 0)
		{
			if(par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
			} else if(par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
			} else if(par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
			} else if(par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
			} else if(canPlaceTorchOn(par1World, par2, par3 - 1, par4))
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
			}
		}
		dropTorchIfCantStay(par1World, par2, par3, par4);
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		int var10 = par9;
		if(par5 == 1 && canPlaceTorchOn(par1World, par2, par3 - 1, par4))
		{
			var10 = 5;
		}
		if(par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
		{
			var10 = 4;
		}
		if(par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
		{
			var10 = 3;
		}
		if(par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
		{
			var10 = 2;
		}
		if(par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
		{
			var10 = 1;
		}
		return var10;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		func_94397_d(par1World, par2, par3, par4, par5);
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
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.updateTick(par1World, par2, par3, par4, par5Random);
		if(par1World.getBlockMetadata(par2, par3, par4) == 0)
		{
			onBlockAdded(par1World, par2, par3, par4);
		}
	}
}
