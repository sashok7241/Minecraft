package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class BlockHalfSlab extends Block
{
	protected final boolean isDoubleSlab;
	
	public BlockHalfSlab(int p_i3954_1_, boolean p_i3954_2_, Material p_i3954_3_)
	{
		super(p_i3954_1_, p_i3954_3_);
		isDoubleSlab = p_i3954_2_;
		if(p_i3954_2_)
		{
			opaqueCubeLookup[p_i3954_1_] = true;
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
		setLightOpacity(255);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		setBlockBoundsBasedOnState(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_ & 7;
	}
	
	@Override public int getDamageValue(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
	{
		return super.getDamageValue(p_71873_1_, p_71873_2_, p_71873_3_, p_71873_4_) & 7;
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
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		return isDoubleSlab ? p_85104_9_ : p_85104_5_ != 0 && (p_85104_5_ == 1 || p_85104_7_ <= 0.5D) ? p_85104_9_ : p_85104_9_ | 8;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return isDoubleSlab ? 2 : 1;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return isDoubleSlab;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		if(isDoubleSlab)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			boolean var5 = (p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_) & 8) != 0;
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
