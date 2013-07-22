package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class BlockHalfSlab extends Block
{
	protected final boolean isDoubleSlab;
	
	public BlockHalfSlab(int par1, boolean par2, Material par3Material)
	{
		super(par1, par3Material);
		isDoubleSlab = par2;
		if(par2)
		{
			opaqueCubeLookup[par1] = true;
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
		setLightOpacity(255);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1 & 7;
	}
	
	@Override public int getDamageValue(World par1World, int par2, int par3, int par4)
	{
		return super.getDamageValue(par1World, par2, par3, par4) & 7;
	}
	
	public abstract String getFullSlabName(int var1);
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return isBlockSingleSlab(blockID) ? blockID : blockID == Block.stoneDoubleSlab.blockID ? Block.stoneSingleSlab.blockID : blockID == Block.woodDoubleSlab.blockID ? Block.woodSingleSlab.blockID : Block.stoneSingleSlab.blockID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return isDoubleSlab;
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		return isDoubleSlab ? par9 : par5 != 0 && (par5 == 1 || par7 <= 0.5D) ? par9 : par9 | 8;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return isDoubleSlab ? 2 : 1;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return isDoubleSlab;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if(isDoubleSlab)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			boolean var5 = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0;
			if(var5)
			{
				setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			} else
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			}
		}
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		if(isDoubleSlab)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(isDoubleSlab) return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		else if(par5 != 1 && par5 != 0 && !super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) return false;
		else
		{
			int var6 = par2 + Facing.offsetsXForSide[Facing.oppositeSide[par5]];
			int var7 = par3 + Facing.offsetsYForSide[Facing.oppositeSide[par5]];
			int var8 = par4 + Facing.offsetsZForSide[Facing.oppositeSide[par5]];
			boolean var9 = (par1IBlockAccess.getBlockMetadata(var6, var7, var8) & 8) != 0;
			return var9 ? par5 == 0 ? true : par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0 : par5 == 1 ? true : par5 == 0 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess.getBlockId(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0;
		}
	}
	
	private static boolean isBlockSingleSlab(int par0)
	{
		return par0 == Block.stoneSingleSlab.blockID || par0 == Block.woodSingleSlab.blockID;
	}
}
