package net.minecraft.src;

import java.util.Random;

public class BlockSign extends BlockContainer
{
	private Class signEntityClass;
	private boolean isFreestanding;
	
	protected BlockSign(int p_i3993_1_, Class p_i3993_2_, boolean p_i3993_3_)
	{
		super(p_i3993_1_, Material.wood);
		isFreestanding = p_i3993_3_;
		signEntityClass = p_i3993_2_;
		float var4 = 0.25F;
		float var5 = 1.0F;
		setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var5, 0.5F + var4);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		try
		{
			return (TileEntity) signEntityClass.newInstance();
		} catch(Exception var3)
		{
			throw new RuntimeException(var3);
		}
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getBlockTextureFromSide(par1);
	}
	
	@Override public int getRenderType()
	{
		return -1;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.sign.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.sign.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		boolean var6 = false;
		if(isFreestanding)
		{
			if(!p_71863_1_.getBlockMaterial(p_71863_2_, p_71863_3_ - 1, p_71863_4_).isSolid())
			{
				var6 = true;
			}
		} else
		{
			int var7 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
			var6 = true;
			if(var7 == 2 && p_71863_1_.getBlockMaterial(p_71863_2_, p_71863_3_, p_71863_4_ + 1).isSolid())
			{
				var6 = false;
			}
			if(var7 == 3 && p_71863_1_.getBlockMaterial(p_71863_2_, p_71863_3_, p_71863_4_ - 1).isSolid())
			{
				var6 = false;
			}
			if(var7 == 4 && p_71863_1_.getBlockMaterial(p_71863_2_ + 1, p_71863_3_, p_71863_4_).isSolid())
			{
				var6 = false;
			}
			if(var7 == 5 && p_71863_1_.getBlockMaterial(p_71863_2_ - 1, p_71863_3_, p_71863_4_).isSolid())
			{
				var6 = false;
			}
		}
		if(var6)
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
		super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		if(!isFreestanding)
		{
			int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
			float var6 = 0.28125F;
			float var7 = 0.78125F;
			float var8 = 0.0F;
			float var9 = 1.0F;
			float var10 = 0.125F;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			if(var5 == 2)
			{
				setBlockBounds(var8, var6, 1.0F - var10, var9, var7, 1.0F);
			}
			if(var5 == 3)
			{
				setBlockBounds(var8, var6, 0.0F, var9, var7, var10);
			}
			if(var5 == 4)
			{
				setBlockBounds(1.0F - var10, var6, var8, 1.0F, var7, var9);
			}
			if(var5 == 5)
			{
				setBlockBounds(0.0F, var6, var8, var10, var7, var9);
			}
		}
	}
}
