package net.minecraft.src;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
	public BlockPistonMoving(int p_i4027_1_)
	{
		super(p_i4027_1_, Material.piston);
		setHardness(-1.0F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		TileEntity var7 = p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
		if(var7 instanceof TileEntityPiston)
		{
			((TileEntityPiston) var7).clearPistonTileEntity();
		} else
		{
			super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		}
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return false;
	}
	
	@Override public boolean canPlaceBlockOnSide(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
	{
		return false;
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return null;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		if(!p_71914_1_.isRemote)
		{
			TileEntityPiston var8 = getTileEntityAtLocation(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_);
			if(var8 != null)
			{
				Block.blocksList[var8.getStoredBlockID()].dropBlockAsItem(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, var8.getBlockMetadata(), 0);
			}
		}
	}
	
	public AxisAlignedBB getAxisAlignedBB(World p_72296_1_, int p_72296_2_, int p_72296_3_, int p_72296_4_, int p_72296_5_, float p_72296_6_, int p_72296_7_)
	{
		if(p_72296_5_ != 0 && p_72296_5_ != blockID)
		{
			AxisAlignedBB var8 = Block.blocksList[p_72296_5_].getCollisionBoundingBoxFromPool(p_72296_1_, p_72296_2_, p_72296_3_, p_72296_4_);
			if(var8 == null) return null;
			else
			{
				if(Facing.offsetsXForSide[p_72296_7_] < 0)
				{
					var8.minX -= Facing.offsetsXForSide[p_72296_7_] * p_72296_6_;
				} else
				{
					var8.maxX -= Facing.offsetsXForSide[p_72296_7_] * p_72296_6_;
				}
				if(Facing.offsetsYForSide[p_72296_7_] < 0)
				{
					var8.minY -= Facing.offsetsYForSide[p_72296_7_] * p_72296_6_;
				} else
				{
					var8.maxY -= Facing.offsetsYForSide[p_72296_7_] * p_72296_6_;
				}
				if(Facing.offsetsZForSide[p_72296_7_] < 0)
				{
					var8.minZ -= Facing.offsetsZForSide[p_72296_7_] * p_72296_6_;
				} else
				{
					var8.maxZ -= Facing.offsetsZForSide[p_72296_7_] * p_72296_6_;
				}
				return var8;
			}
		} else return null;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		TileEntityPiston var5 = getTileEntityAtLocation(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		if(var5 == null) return null;
		else
		{
			float var6 = var5.getProgress(0.0F);
			if(var5.isExtending())
			{
				var6 = 1.0F - var6;
			}
			return getAxisAlignedBB(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_, var5.getStoredBlockID(), var6, var5.getPistonOrientation());
		}
	}
	
	@Override public int getRenderType()
	{
		return -1;
	}
	
	private TileEntityPiston getTileEntityAtLocation(IBlockAccess p_72295_1_, int p_72295_2_, int p_72295_3_, int p_72295_4_)
	{
		TileEntity var5 = p_72295_1_.getBlockTileEntity(p_72295_2_, p_72295_3_, p_72295_4_);
		return var5 instanceof TileEntityPiston ? (TileEntityPiston) var5 : null;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
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
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(!p_71903_1_.isRemote && p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_) == null)
		{
			p_71903_1_.setBlockToAir(p_71903_2_, p_71903_3_, p_71903_4_);
			return true;
		} else return false;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote && p_71863_1_.getBlockTileEntity(p_71863_2_, p_71863_3_, p_71863_4_) == null)
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		TileEntityPiston var5 = getTileEntityAtLocation(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_);
		if(var5 != null)
		{
			Block var6 = Block.blocksList[var5.getStoredBlockID()];
			if(var6 == null || var6 == this) return;
			var6.setBlockBoundsBasedOnState(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_);
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
	
	public static TileEntity getTileEntity(int p_72297_0_, int p_72297_1_, int p_72297_2_, boolean p_72297_3_, boolean p_72297_4_)
	{
		return new TileEntityPiston(p_72297_0_, p_72297_1_, p_72297_2_, p_72297_3_, p_72297_4_);
	}
}
