package net.minecraft.src;

import java.util.Random;

public class BlockDoor extends Block
{
	private Icon[] field_111044_a;
	private Icon[] field_111043_b;
	
	protected BlockDoor(int par1, Material par2Material)
	{
		super(par1, par2Material);
		float var3 = 0.5F;
		float var4 = 1.0F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par3 >= 255 ? false : par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4);
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = getFullMetadata(par1IBlockAccess, par2, par3, par4);
		return (var5 & 4) != 0;
	}
	
	@Override public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par5 != 1 && par5 != 0)
		{
			int var6 = getFullMetadata(par1IBlockAccess, par2, par3, par4);
			int var7 = var6 & 3;
			boolean var8 = (var6 & 4) != 0;
			boolean var9 = false;
			boolean var10 = (var6 & 8) != 0;
			if(var8)
			{
				if(var7 == 0 && par5 == 2)
				{
					var9 = !var9;
				} else if(var7 == 1 && par5 == 5)
				{
					var9 = !var9;
				} else if(var7 == 2 && par5 == 3)
				{
					var9 = !var9;
				} else if(var7 == 3 && par5 == 4)
				{
					var9 = !var9;
				}
			} else
			{
				if(var7 == 0 && par5 == 5)
				{
					var9 = !var9;
				} else if(var7 == 1 && par5 == 3)
				{
					var9 = !var9;
				} else if(var7 == 2 && par5 == 4)
				{
					var9 = !var9;
				} else if(var7 == 3 && par5 == 2)
				{
					var9 = !var9;
				}
				if((var6 & 16) != 0)
				{
					var9 = !var9;
				}
			}
			return var10 ? field_111044_a[var9 ? 1 : 0] : field_111043_b[var9 ? 1 : 0];
		} else return field_111043_b[0];
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	public int getDoorOrientation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return getFullMetadata(par1IBlockAccess, par2, par3, par4) & 3;
	}
	
	public int getFullMetadata(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		boolean var6 = (var5 & 8) != 0;
		int var7;
		int var8;
		if(var6)
		{
			var7 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
			var8 = var5;
		} else
		{
			var7 = var5;
			var8 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
		}
		boolean var9 = (var8 & 1) != 0;
		return var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return field_111043_b[0];
	}
	
	@Override public int getMobilityFlag()
	{
		return 1;
	}
	
	@Override public int getRenderType()
	{
		return 7;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return (par1 & 8) != 0 ? 0 : blockMaterial == Material.iron ? Item.doorIron.itemID : Item.doorWood.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return blockMaterial == Material.iron ? Item.doorIron.itemID : Item.doorWood.itemID;
	}
	
	public boolean isDoorOpen(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return (getFullMetadata(par1IBlockAccess, par2, par3, par4) & 4) != 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(blockMaterial == Material.iron) return true;
		else
		{
			int var10 = getFullMetadata(par1World, par2, par3, par4);
			int var11 = var10 & 7;
			var11 ^= 4;
			if((var10 & 8) == 0)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 2);
				par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			} else
			{
				par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var11, 2);
				par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
			}
			par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
			return true;
		}
	}
	
	@Override public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
	}
	
	@Override public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
		if(par6EntityPlayer.capabilities.isCreativeMode && (par5 & 8) != 0 && par1World.getBlockId(par2, par3 - 1, par4) == blockID)
		{
			par1World.setBlockToAir(par2, par3 - 1, par4);
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		if((var6 & 8) == 0)
		{
			boolean var7 = false;
			if(par1World.getBlockId(par2, par3 + 1, par4) != blockID)
			{
				par1World.setBlockToAir(par2, par3, par4);
				var7 = true;
			}
			if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
			{
				par1World.setBlockToAir(par2, par3, par4);
				var7 = true;
				if(par1World.getBlockId(par2, par3 + 1, par4) == blockID)
				{
					par1World.setBlockToAir(par2, par3 + 1, par4);
				}
			}
			if(var7)
			{
				if(!par1World.isRemote)
				{
					dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
				}
			} else
			{
				boolean var8 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
				if((var8 || par5 > 0 && Block.blocksList[par5].canProvidePower()) && par5 != blockID)
				{
					onPoweredBlockChange(par1World, par2, par3, par4, var8);
				}
			}
		} else
		{
			if(par1World.getBlockId(par2, par3 - 1, par4) != blockID)
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
			if(par5 > 0 && par5 != blockID)
			{
				onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
			}
		}
	}
	
	public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
	{
		int var6 = getFullMetadata(par1World, par2, par3, par4);
		boolean var7 = (var6 & 4) != 0;
		if(var7 != par5)
		{
			int var8 = var6 & 7;
			var8 ^= 4;
			if((var6 & 8) == 0)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var8, 2);
				par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
			} else
			{
				par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var8, 2);
				par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
			}
			par1World.playAuxSFXAtEntity((EntityPlayer) null, 1003, par2, par3, par4, 0);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_111044_a = new Icon[2];
		field_111043_b = new Icon[2];
		field_111044_a[0] = par1IconRegister.registerIcon(func_111023_E() + "_upper");
		field_111043_b[0] = par1IconRegister.registerIcon(func_111023_E() + "_lower");
		field_111044_a[1] = new IconFlipped(field_111044_a[0], true, false);
		field_111043_b[1] = new IconFlipped(field_111043_b[0], true, false);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		setDoorRotation(getFullMetadata(par1IBlockAccess, par2, par3, par4));
	}
	
	private void setDoorRotation(int par1)
	{
		float var2 = 0.1875F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		int var3 = par1 & 3;
		boolean var4 = (par1 & 4) != 0;
		boolean var5 = (par1 & 16) != 0;
		if(var3 == 0)
		{
			if(var4)
			{
				if(!var5)
				{
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
				} else
				{
					setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
				}
			} else
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
			}
		} else if(var3 == 1)
		{
			if(var4)
			{
				if(!var5)
				{
					setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				} else
				{
					setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
				}
			} else
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
			}
		} else if(var3 == 2)
		{
			if(var4)
			{
				if(!var5)
				{
					setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
				} else
				{
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
				}
			} else
			{
				setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		} else if(var3 == 3)
		{
			if(var4)
			{
				if(!var5)
				{
					setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
				} else
				{
					setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				}
			} else
			{
				setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
			}
		}
	}
}
