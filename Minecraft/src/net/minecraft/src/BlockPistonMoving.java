package net.minecraft.src;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
	public BlockPistonMoving(int par1)
	{
		super(par1, Material.piston);
		setHardness(-1.0F);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
		if(var7 instanceof TileEntityPiston)
		{
			((TileEntityPiston) var7).clearPistonTileEntity();
		} else
		{
			super.breakBlock(par1World, par2, par3, par4, par5, par6);
		}
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return false;
	}
	
	@Override public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		return false;
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return null;
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if(!par1World.isRemote)
		{
			TileEntityPiston var8 = getTileEntityAtLocation(par1World, par2, par3, par4);
			if(var8 != null)
			{
				Block.blocksList[var8.getStoredBlockID()].dropBlockAsItem(par1World, par2, par3, par4, var8.getBlockMetadata(), 0);
			}
		}
	}
	
	public AxisAlignedBB getAxisAlignedBB(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if(par5 != 0 && par5 != blockID)
		{
			AxisAlignedBB var8 = Block.blocksList[par5].getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
			if(var8 == null) return null;
			else
			{
				if(Facing.offsetsXForSide[par7] < 0)
				{
					var8.minX -= Facing.offsetsXForSide[par7] * par6;
				} else
				{
					var8.maxX -= Facing.offsetsXForSide[par7] * par6;
				}
				if(Facing.offsetsYForSide[par7] < 0)
				{
					var8.minY -= Facing.offsetsYForSide[par7] * par6;
				} else
				{
					var8.maxY -= Facing.offsetsYForSide[par7] * par6;
				}
				if(Facing.offsetsZForSide[par7] < 0)
				{
					var8.minZ -= Facing.offsetsZForSide[par7] * par6;
				} else
				{
					var8.maxZ -= Facing.offsetsZForSide[par7] * par6;
				}
				return var8;
			}
		} else return null;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		TileEntityPiston var5 = getTileEntityAtLocation(par1World, par2, par3, par4);
		if(var5 == null) return null;
		else
		{
			float var6 = var5.getProgress(0.0F);
			if(var5.isExtending())
			{
				var6 = 1.0F - var6;
			}
			return getAxisAlignedBB(par1World, par2, par3, par4, var5.getStoredBlockID(), var6, var5.getPistonOrientation());
		}
	}
	
	@Override public int getRenderType()
	{
		return -1;
	}
	
	private TileEntityPiston getTileEntityAtLocation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		TileEntity var5 = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		return var5 instanceof TileEntityPiston ? (TileEntityPiston) var5 : null;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
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
		if(!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null)
		{
			par1World.setBlockToAir(par2, par3, par4);
			return true;
		} else return false;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null)
		{
			;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("piston_top");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		TileEntityPiston var5 = getTileEntityAtLocation(par1IBlockAccess, par2, par3, par4);
		if(var5 != null)
		{
			Block var6 = Block.blocksList[var5.getStoredBlockID()];
			if(var6 == null || var6 == this) return;
			var6.setBlockBoundsBasedOnState(par1IBlockAccess, par2, par3, par4);
			float var7 = var5.getProgress(0.0F);
			if(var5.isExtending())
			{
				var7 = 1.0F - var7;
			}
			int var8 = var5.getPistonOrientation();
			minX = var6.getBlockBoundsMinX() - Facing.offsetsXForSide[var8] * var7;
			minY = var6.getBlockBoundsMinY() - Facing.offsetsYForSide[var8] * var7;
			minZ = var6.getBlockBoundsMinZ() - Facing.offsetsZForSide[var8] * var7;
			maxX = var6.getBlockBoundsMaxX() - Facing.offsetsXForSide[var8] * var7;
			maxY = var6.getBlockBoundsMaxY() - Facing.offsetsYForSide[var8] * var7;
			maxZ = var6.getBlockBoundsMaxZ() - Facing.offsetsZForSide[var8] * var7;
		}
	}
	
	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4)
	{
		return new TileEntityPiston(par0, par1, par2, par3, par4);
	}
}
