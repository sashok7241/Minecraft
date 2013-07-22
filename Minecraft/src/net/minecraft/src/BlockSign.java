package net.minecraft.src;

import java.util.Random;

public class BlockSign extends BlockContainer
{
	private Class signEntityClass;
	private boolean isFreestanding;
	
	protected BlockSign(int par1, Class par2Class, boolean par3)
	{
		super(par1, Material.wood);
		isFreestanding = par3;
		signEntityClass = par2Class;
		float var4 = 0.25F;
		float var5 = 1.0F;
		setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var5, 0.5F + var4);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		try
		{
			return (TileEntity) signEntityClass.newInstance();
		} catch(Exception var3)
		{
			throw new RuntimeException(var3);
		}
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
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
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		boolean var6 = false;
		if(isFreestanding)
		{
			if(!par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid())
			{
				var6 = true;
			}
		} else
		{
			int var7 = par1World.getBlockMetadata(par2, par3, par4);
			var6 = true;
			if(var7 == 2 && par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid())
			{
				var6 = false;
			}
			if(var7 == 3 && par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid())
			{
				var6 = false;
			}
			if(var7 == 4 && par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid())
			{
				var6 = false;
			}
			if(var7 == 5 && par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid())
			{
				var6 = false;
			}
		}
		if(var6)
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
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
		if(!isFreestanding)
		{
			int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
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
