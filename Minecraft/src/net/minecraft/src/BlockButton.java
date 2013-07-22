package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class BlockButton extends Block
{
	private final boolean sensible;
	
	protected BlockButton(int par1, boolean par2)
	{
		super(par1, Material.circuits);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabRedstone);
		sensible = par2;
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if((par6 & 8) > 0)
		{
			int var7 = par6 & 7;
			func_82536_d(par1World, par2, par3, par4, var7);
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockNormalCube(par2 - 1, par3, par4) ? true : par1World.isBlockNormalCube(par2 + 1, par3, par4) ? true : par1World.isBlockNormalCube(par2, par3, par4 - 1) ? true : par1World.isBlockNormalCube(par2, par3, par4 + 1);
	}
	
	@Override public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		return par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1) ? true : par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1) ? true : par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4) ? true : par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	private void func_82534_e(int par1)
	{
		int var2 = par1 & 7;
		boolean var3 = (par1 & 8) > 0;
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
	
	private void func_82535_o(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 7;
		boolean var7 = (var5 & 8) != 0;
		func_82534_e(var5);
		List var9 = par1World.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + maxY, par4 + maxZ));
		boolean var8 = !var9.isEmpty();
		if(var8 && !var7)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 8, 3);
			func_82536_d(par1World, par2, par3, par4, var6);
			par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "random.click", 0.3F, 0.6F);
		}
		if(!var8 && var7)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 3);
			func_82536_d(par1World, par2, par3, par4, var6);
			par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "random.click", 0.3F, 0.5F);
		}
		if(var8)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
		}
	}
	
	private void func_82536_d(World par1World, int par2, int par3, int par4, int par5)
	{
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
		if(par5 == 1)
		{
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
		} else if(par5 == 2)
		{
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
		} else if(par5 == 3)
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
		} else if(par5 == 4)
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		} else
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	
	private int getOrientation(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockNormalCube(par2 - 1, par3, par4) ? 1 : par1World.isBlockNormalCube(par2 + 1, par3, par4) ? 2 : par1World.isBlockNormalCube(par2, par3, par4 - 1) ? 3 : par1World.isBlockNormalCube(par2, par3, par4 + 1) ? 4 : 1;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if((var6 & 8) == 0) return 0;
		else
		{
			int var7 = var6 & 7;
			return var7 == 5 && par5 == 1 ? 15 : var7 == 4 && par5 == 2 ? 15 : var7 == 3 && par5 == 3 ? 15 : var7 == 2 && par5 == 4 ? 15 : var7 == 1 && par5 == 5 ? 15 : 0;
		}
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0 ? 15 : 0;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		int var10 = par1World.getBlockMetadata(par2, par3, par4);
		int var11 = var10 & 7;
		int var12 = 8 - (var10 & 8);
		if(var12 == 0) return true;
		else
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 + var12, 3);
			par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "random.click", 0.3F, 0.6F);
			func_82536_d(par1World, par2, par3, par4, var11);
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			return true;
		}
	}
	
	@Override public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		int var10 = par1World.getBlockMetadata(par2, par3, par4);
		int var11 = var10 & 8;
		var10 &= 7;
		if(par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1))
		{
			var10 = 4;
		} else if(par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1))
		{
			var10 = 3;
		} else if(par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4))
		{
			var10 = 2;
		} else if(par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4))
		{
			var10 = 1;
		} else
		{
			var10 = getOrientation(par1World, par2, par3, par4);
		}
		return var10 + var11;
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		if(!par1World.isRemote)
		{
			if(sensible)
			{
				if((par1World.getBlockMetadata(par2, par3, par4) & 8) == 0)
				{
					func_82535_o(par1World, par2, par3, par4);
				}
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(redundantCanPlaceBlockAt(par1World, par2, par3, par4))
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4) & 7;
			boolean var7 = false;
			if(!par1World.isBlockNormalCube(par2 - 1, par3, par4) && var6 == 1)
			{
				var7 = true;
			}
			if(!par1World.isBlockNormalCube(par2 + 1, par3, par4) && var6 == 2)
			{
				var7 = true;
			}
			if(!par1World.isBlockNormalCube(par2, par3, par4 - 1) && var6 == 3)
			{
				var7 = true;
			}
			if(!par1World.isBlockNormalCube(par2, par3, par4 + 1) && var6 == 4)
			{
				var7 = true;
			}
			if(var7)
			{
				dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}
	
	private boolean redundantCanPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		if(!canPlaceBlockAt(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		func_82534_e(var5);
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.1875F;
		float var2 = 0.125F;
		float var3 = 0.125F;
		setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
	}
	
	@Override public int tickRate(World par1World)
	{
		return sensible ? 30 : 20;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			if((var6 & 8) != 0)
			{
				if(sensible)
				{
					func_82535_o(par1World, par2, par3, par4);
				} else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & 7, 3);
					int var7 = var6 & 7;
					func_82536_d(par1World, par2, par3, par4, var7);
					par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "random.click", 0.3F, 0.5F);
					par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
				}
			}
		}
	}
}
