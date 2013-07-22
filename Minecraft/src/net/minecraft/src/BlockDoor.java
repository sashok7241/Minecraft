package net.minecraft.src;

import java.util.Random;

public class BlockDoor extends Block
{
	private static final String[] doorIconNames = new String[] { "doorWood_lower", "doorWood_upper", "doorIron_lower", "doorIron_upper" };
	private final int doorTypeForIcon;
	private Icon[] iconArray;
	
	protected BlockDoor(int p_i3939_1_, Material p_i3939_2_)
	{
		super(p_i3939_1_, p_i3939_2_);
		if(p_i3939_2_ == Material.iron)
		{
			doorTypeForIcon = 2;
		} else
		{
			doorTypeForIcon = 0;
		}
		float var3 = 0.5F;
		float var4 = 1.0F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_3_ >= 255 ? false : p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_) && super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) && super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_ + 1, p_71930_4_);
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World p_71878_1_, int p_71878_2_, int p_71878_3_, int p_71878_4_, Vec3 p_71878_5_, Vec3 p_71878_6_)
	{
		setBlockBoundsBasedOnState(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_);
		return super.collisionRayTrace(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_, p_71878_5_, p_71878_6_);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		int var5 = getFullMetadata(p_71918_1_, p_71918_2_, p_71918_3_, p_71918_4_);
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
			return iconArray[doorTypeForIcon + (var9 ? doorIconNames.length : 0) + (var10 ? 1 : 0)];
		} else return iconArray[doorTypeForIcon];
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
	}
	
	public int getDoorOrientation(IBlockAccess p_72235_1_, int p_72235_2_, int p_72235_3_, int p_72235_4_)
	{
		return getFullMetadata(p_72235_1_, p_72235_2_, p_72235_3_, p_72235_4_) & 3;
	}
	
	public int getFullMetadata(IBlockAccess p_72234_1_, int p_72234_2_, int p_72234_3_, int p_72234_4_)
	{
		int var5 = p_72234_1_.getBlockMetadata(p_72234_2_, p_72234_3_, p_72234_4_);
		boolean var6 = (var5 & 8) != 0;
		int var7;
		int var8;
		if(var6)
		{
			var7 = p_72234_1_.getBlockMetadata(p_72234_2_, p_72234_3_ - 1, p_72234_4_);
			var8 = var5;
		} else
		{
			var7 = var5;
			var8 = p_72234_1_.getBlockMetadata(p_72234_2_, p_72234_3_ + 1, p_72234_4_);
		}
		boolean var9 = (var8 & 1) != 0;
		return var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return iconArray[doorTypeForIcon];
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
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return (p_71885_1_ & 8) != 0 ? 0 : blockMaterial == Material.iron ? Item.doorIron.itemID : Item.doorWood.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return blockMaterial == Material.iron ? Item.doorIron.itemID : Item.doorWood.itemID;
	}
	
	public boolean isDoorOpen(IBlockAccess p_72233_1_, int p_72233_2_, int p_72233_3_, int p_72233_4_)
	{
		return (getFullMetadata(p_72233_1_, p_72233_2_, p_72233_3_, p_72233_4_) & 4) != 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(blockMaterial == Material.iron) return true;
		else
		{
			int var10 = getFullMetadata(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
			int var11 = var10 & 7;
			var11 ^= 4;
			if((var10 & 8) == 0)
			{
				p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var11, 2);
				p_71903_1_.markBlockRangeForRenderUpdate(p_71903_2_, p_71903_3_, p_71903_4_, p_71903_2_, p_71903_3_, p_71903_4_);
			} else
			{
				p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_ - 1, p_71903_4_, var11, 2);
				p_71903_1_.markBlockRangeForRenderUpdate(p_71903_2_, p_71903_3_ - 1, p_71903_4_, p_71903_2_, p_71903_3_, p_71903_4_);
			}
			p_71903_1_.playAuxSFXAtEntity(p_71903_5_, 1003, p_71903_2_, p_71903_3_, p_71903_4_, 0);
			return true;
		}
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
	}
	
	@Override public void onBlockHarvested(World p_71846_1_, int p_71846_2_, int p_71846_3_, int p_71846_4_, int p_71846_5_, EntityPlayer p_71846_6_)
	{
		if(p_71846_6_.capabilities.isCreativeMode && (p_71846_5_ & 8) != 0 && p_71846_1_.getBlockId(p_71846_2_, p_71846_3_ - 1, p_71846_4_) == blockID)
		{
			p_71846_1_.setBlockToAir(p_71846_2_, p_71846_3_ - 1, p_71846_4_);
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
		if((var6 & 8) == 0)
		{
			boolean var7 = false;
			if(p_71863_1_.getBlockId(p_71863_2_, p_71863_3_ + 1, p_71863_4_) != blockID)
			{
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
				var7 = true;
			}
			if(!p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_ - 1, p_71863_4_))
			{
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
				var7 = true;
				if(p_71863_1_.getBlockId(p_71863_2_, p_71863_3_ + 1, p_71863_4_) == blockID)
				{
					p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_ + 1, p_71863_4_);
				}
			}
			if(var7)
			{
				if(!p_71863_1_.isRemote)
				{
					dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var6, 0);
				}
			} else
			{
				boolean var8 = p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_) || p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_ + 1, p_71863_4_);
				if((var8 || p_71863_5_ > 0 && Block.blocksList[p_71863_5_].canProvidePower()) && p_71863_5_ != blockID)
				{
					onPoweredBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var8);
				}
			}
		} else
		{
			if(p_71863_1_.getBlockId(p_71863_2_, p_71863_3_ - 1, p_71863_4_) != blockID)
			{
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			}
			if(p_71863_5_ > 0 && p_71863_5_ != blockID)
			{
				onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_ - 1, p_71863_4_, p_71863_5_);
			}
		}
	}
	
	public void onPoweredBlockChange(World p_72231_1_, int p_72231_2_, int p_72231_3_, int p_72231_4_, boolean p_72231_5_)
	{
		int var6 = getFullMetadata(p_72231_1_, p_72231_2_, p_72231_3_, p_72231_4_);
		boolean var7 = (var6 & 4) != 0;
		if(var7 != p_72231_5_)
		{
			int var8 = var6 & 7;
			var8 ^= 4;
			if((var6 & 8) == 0)
			{
				p_72231_1_.setBlockMetadataWithNotify(p_72231_2_, p_72231_3_, p_72231_4_, var8, 2);
				p_72231_1_.markBlockRangeForRenderUpdate(p_72231_2_, p_72231_3_, p_72231_4_, p_72231_2_, p_72231_3_, p_72231_4_);
			} else
			{
				p_72231_1_.setBlockMetadataWithNotify(p_72231_2_, p_72231_3_ - 1, p_72231_4_, var8, 2);
				p_72231_1_.markBlockRangeForRenderUpdate(p_72231_2_, p_72231_3_ - 1, p_72231_4_, p_72231_2_, p_72231_3_, p_72231_4_);
			}
			p_72231_1_.playAuxSFXAtEntity((EntityPlayer) null, 1003, p_72231_2_, p_72231_3_, p_72231_4_, 0);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[doorIconNames.length * 2];
		for(int var2 = 0; var2 < doorIconNames.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(doorIconNames[var2]);
			iconArray[var2 + doorIconNames.length] = new IconFlipped(iconArray[var2], true, false);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		setDoorRotation(getFullMetadata(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_));
	}
	
	private void setDoorRotation(int p_72232_1_)
	{
		float var2 = 0.1875F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		int var3 = p_72232_1_ & 3;
		boolean var4 = (p_72232_1_ & 4) != 0;
		boolean var5 = (p_72232_1_ & 16) != 0;
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
