package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class BlockChest extends BlockContainer
{
	private final Random random = new Random();
	public final int isTrapped;
	
	protected BlockChest(int p_i9045_1_, int p_i9045_2_)
	{
		super(p_i9045_1_, Material.wood);
		isTrapped = p_i9045_2_;
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		TileEntityChest var7 = (TileEntityChest) p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
		if(var7 != null)
		{
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
			{
				ItemStack var9 = var7.getStackInSlot(var8);
				if(var9 != null)
				{
					float var10 = random.nextFloat() * 0.8F + 0.1F;
					float var11 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem var14;
					for(float var12 = random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; p_71852_1_.spawnEntityInWorld(var14))
					{
						int var13 = random.nextInt(21) + 10;
						if(var13 > var9.stackSize)
						{
							var13 = var9.stackSize;
						}
						var9.stackSize -= var13;
						var14 = new EntityItem(p_71852_1_, p_71852_2_ + var10, p_71852_3_ + var11, p_71852_4_ + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						float var15 = 0.05F;
						var14.motionX = (float) random.nextGaussian() * var15;
						var14.motionY = (float) random.nextGaussian() * var15 + 0.2F;
						var14.motionZ = (float) random.nextGaussian() * var15;
						if(var9.hasTagCompound())
						{
							var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
						}
					}
				}
			}
			p_71852_1_.func_96440_m(p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_);
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		int var5 = 0;
		if(p_71930_1_.getBlockId(p_71930_2_ - 1, p_71930_3_, p_71930_4_) == blockID)
		{
			++var5;
		}
		if(p_71930_1_.getBlockId(p_71930_2_ + 1, p_71930_3_, p_71930_4_) == blockID)
		{
			++var5;
		}
		if(p_71930_1_.getBlockId(p_71930_2_, p_71930_3_, p_71930_4_ - 1) == blockID)
		{
			++var5;
		}
		if(p_71930_1_.getBlockId(p_71930_2_, p_71930_3_, p_71930_4_ + 1) == blockID)
		{
			++var5;
		}
		return var5 > 1 ? false : isThereANeighborChest(p_71930_1_, p_71930_2_ - 1, p_71930_3_, p_71930_4_) ? false : isThereANeighborChest(p_71930_1_, p_71930_2_ + 1, p_71930_3_, p_71930_4_) ? false : isThereANeighborChest(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_ - 1) ? false : !isThereANeighborChest(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_ + 1);
	}
	
	@Override public boolean canProvidePower()
	{
		return isTrapped == 1;
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		TileEntityChest var2 = new TileEntityChest();
		return var2;
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		return Container.calcRedstoneFromInventory(getInventory(p_94328_1_, p_94328_2_, p_94328_3_, p_94328_4_));
	}
	
	public IInventory getInventory(World p_94442_1_, int p_94442_2_, int p_94442_3_, int p_94442_4_)
	{
		Object var5 = p_94442_1_.getBlockTileEntity(p_94442_2_, p_94442_3_, p_94442_4_);
		if(var5 == null) return null;
		else if(p_94442_1_.isBlockNormalCube(p_94442_2_, p_94442_3_ + 1, p_94442_4_)) return null;
		else if(isOcelotBlockingChest(p_94442_1_, p_94442_2_, p_94442_3_, p_94442_4_)) return null;
		else if(p_94442_1_.getBlockId(p_94442_2_ - 1, p_94442_3_, p_94442_4_) == blockID && (p_94442_1_.isBlockNormalCube(p_94442_2_ - 1, p_94442_3_ + 1, p_94442_4_) || isOcelotBlockingChest(p_94442_1_, p_94442_2_ - 1, p_94442_3_, p_94442_4_))) return null;
		else if(p_94442_1_.getBlockId(p_94442_2_ + 1, p_94442_3_, p_94442_4_) == blockID && (p_94442_1_.isBlockNormalCube(p_94442_2_ + 1, p_94442_3_ + 1, p_94442_4_) || isOcelotBlockingChest(p_94442_1_, p_94442_2_ + 1, p_94442_3_, p_94442_4_))) return null;
		else if(p_94442_1_.getBlockId(p_94442_2_, p_94442_3_, p_94442_4_ - 1) == blockID && (p_94442_1_.isBlockNormalCube(p_94442_2_, p_94442_3_ + 1, p_94442_4_ - 1) || isOcelotBlockingChest(p_94442_1_, p_94442_2_, p_94442_3_, p_94442_4_ - 1))) return null;
		else if(p_94442_1_.getBlockId(p_94442_2_, p_94442_3_, p_94442_4_ + 1) == blockID && (p_94442_1_.isBlockNormalCube(p_94442_2_, p_94442_3_ + 1, p_94442_4_ + 1) || isOcelotBlockingChest(p_94442_1_, p_94442_2_, p_94442_3_, p_94442_4_ + 1))) return null;
		else
		{
			if(p_94442_1_.getBlockId(p_94442_2_ - 1, p_94442_3_, p_94442_4_) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (TileEntityChest) p_94442_1_.getBlockTileEntity(p_94442_2_ - 1, p_94442_3_, p_94442_4_), (IInventory) var5);
			}
			if(p_94442_1_.getBlockId(p_94442_2_ + 1, p_94442_3_, p_94442_4_) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (IInventory) var5, (TileEntityChest) p_94442_1_.getBlockTileEntity(p_94442_2_ + 1, p_94442_3_, p_94442_4_));
			}
			if(p_94442_1_.getBlockId(p_94442_2_, p_94442_3_, p_94442_4_ - 1) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (TileEntityChest) p_94442_1_.getBlockTileEntity(p_94442_2_, p_94442_3_, p_94442_4_ - 1), (IInventory) var5);
			}
			if(p_94442_1_.getBlockId(p_94442_2_, p_94442_3_, p_94442_4_ + 1) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (IInventory) var5, (TileEntityChest) p_94442_1_.getBlockTileEntity(p_94442_2_, p_94442_3_, p_94442_4_ + 1));
			}
			return (IInventory) var5;
		}
	}
	
	@Override public int getRenderType()
	{
		return 22;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		return p_71855_5_ == 1 ? isProvidingWeakPower(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_) : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		if(!canProvidePower()) return 0;
		else
		{
			int var6 = ((TileEntityChest) p_71865_1_.getBlockTileEntity(p_71865_2_, p_71865_3_, p_71865_4_)).numUsingPlayers;
			return MathHelper.clamp_int(var6, 0, 15);
		}
	}
	
	private boolean isThereANeighborChest(World p_72291_1_, int p_72291_2_, int p_72291_3_, int p_72291_4_)
	{
		return p_72291_1_.getBlockId(p_72291_2_, p_72291_3_, p_72291_4_) != blockID ? false : p_72291_1_.getBlockId(p_72291_2_ - 1, p_72291_3_, p_72291_4_) == blockID ? true : p_72291_1_.getBlockId(p_72291_2_ + 1, p_72291_3_, p_72291_4_) == blockID ? true : p_72291_1_.getBlockId(p_72291_2_, p_72291_3_, p_72291_4_ - 1) == blockID ? true : p_72291_1_.getBlockId(p_72291_2_, p_72291_3_, p_72291_4_ + 1) == blockID;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			IInventory var10 = getInventory(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				p_71903_5_.displayGUIChest(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		unifyAdjacentChests(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		int var5 = p_71861_1_.getBlockId(p_71861_2_, p_71861_3_, p_71861_4_ - 1);
		int var6 = p_71861_1_.getBlockId(p_71861_2_, p_71861_3_, p_71861_4_ + 1);
		int var7 = p_71861_1_.getBlockId(p_71861_2_ - 1, p_71861_3_, p_71861_4_);
		int var8 = p_71861_1_.getBlockId(p_71861_2_ + 1, p_71861_3_, p_71861_4_);
		if(var5 == blockID)
		{
			unifyAdjacentChests(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ - 1);
		}
		if(var6 == blockID)
		{
			unifyAdjacentChests(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ + 1);
		}
		if(var7 == blockID)
		{
			unifyAdjacentChests(p_71861_1_, p_71861_2_ - 1, p_71861_3_, p_71861_4_);
		}
		if(var8 == blockID)
		{
			unifyAdjacentChests(p_71861_1_, p_71861_2_ + 1, p_71861_3_, p_71861_4_);
		}
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = p_71860_1_.getBlockId(p_71860_2_, p_71860_3_, p_71860_4_ - 1);
		int var8 = p_71860_1_.getBlockId(p_71860_2_, p_71860_3_, p_71860_4_ + 1);
		int var9 = p_71860_1_.getBlockId(p_71860_2_ - 1, p_71860_3_, p_71860_4_);
		int var10 = p_71860_1_.getBlockId(p_71860_2_ + 1, p_71860_3_, p_71860_4_);
		byte var11 = 0;
		int var12 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(var12 == 0)
		{
			var11 = 2;
		}
		if(var12 == 1)
		{
			var11 = 5;
		}
		if(var12 == 2)
		{
			var11 = 3;
		}
		if(var12 == 3)
		{
			var11 = 4;
		}
		if(var7 != blockID && var8 != blockID && var9 != blockID && var10 != blockID)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var11, 3);
		} else
		{
			if((var7 == blockID || var8 == blockID) && (var11 == 4 || var11 == 5))
			{
				if(var7 == blockID)
				{
					p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_ - 1, var11, 3);
				} else
				{
					p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_ + 1, var11, 3);
				}
				p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var11, 3);
			}
			if((var9 == blockID || var10 == blockID) && (var11 == 2 || var11 == 3))
			{
				if(var9 == blockID)
				{
					p_71860_1_.setBlockMetadataWithNotify(p_71860_2_ - 1, p_71860_3_, p_71860_4_, var11, 3);
				} else
				{
					p_71860_1_.setBlockMetadataWithNotify(p_71860_2_ + 1, p_71860_3_, p_71860_4_, var11, 3);
				}
				p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var11, 3);
			}
		}
		if(p_71860_6_.hasDisplayName())
		{
			((TileEntityChest) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_)).setChestGuiName(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
		TileEntityChest var6 = (TileEntityChest) p_71863_1_.getBlockTileEntity(p_71863_2_, p_71863_3_, p_71863_4_);
		if(var6 != null)
		{
			var6.updateContainingBlockInfo();
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("wood");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		if(p_71902_1_.getBlockId(p_71902_2_, p_71902_3_, p_71902_4_ - 1) == blockID)
		{
			setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
		} else if(p_71902_1_.getBlockId(p_71902_2_, p_71902_3_, p_71902_4_ + 1) == blockID)
		{
			setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
		} else if(p_71902_1_.getBlockId(p_71902_2_ - 1, p_71902_3_, p_71902_4_) == blockID)
		{
			setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		} else if(p_71902_1_.getBlockId(p_71902_2_ + 1, p_71902_3_, p_71902_4_) == blockID)
		{
			setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
		} else
		{
			setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		}
	}
	
	public void unifyAdjacentChests(World p_72290_1_, int p_72290_2_, int p_72290_3_, int p_72290_4_)
	{
		if(!p_72290_1_.isRemote)
		{
			int var5 = p_72290_1_.getBlockId(p_72290_2_, p_72290_3_, p_72290_4_ - 1);
			int var6 = p_72290_1_.getBlockId(p_72290_2_, p_72290_3_, p_72290_4_ + 1);
			int var7 = p_72290_1_.getBlockId(p_72290_2_ - 1, p_72290_3_, p_72290_4_);
			int var8 = p_72290_1_.getBlockId(p_72290_2_ + 1, p_72290_3_, p_72290_4_);
			boolean var9 = true;
			int var10;
			int var11;
			boolean var12;
			byte var13;
			int var14;
			if(var5 != blockID && var6 != blockID)
			{
				if(var7 != blockID && var8 != blockID)
				{
					var13 = 3;
					if(Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])
					{
						var13 = 3;
					}
					if(Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])
					{
						var13 = 2;
					}
					if(Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])
					{
						var13 = 5;
					}
					if(Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])
					{
						var13 = 4;
					}
				} else
				{
					var10 = p_72290_1_.getBlockId(var7 == blockID ? p_72290_2_ - 1 : p_72290_2_ + 1, p_72290_3_, p_72290_4_ - 1);
					var11 = p_72290_1_.getBlockId(var7 == blockID ? p_72290_2_ - 1 : p_72290_2_ + 1, p_72290_3_, p_72290_4_ + 1);
					var13 = 3;
					var12 = true;
					if(var7 == blockID)
					{
						var14 = p_72290_1_.getBlockMetadata(p_72290_2_ - 1, p_72290_3_, p_72290_4_);
					} else
					{
						var14 = p_72290_1_.getBlockMetadata(p_72290_2_ + 1, p_72290_3_, p_72290_4_);
					}
					if(var14 == 2)
					{
						var13 = 2;
					}
					if((Block.opaqueCubeLookup[var5] || Block.opaqueCubeLookup[var10]) && !Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var11])
					{
						var13 = 3;
					}
					if((Block.opaqueCubeLookup[var6] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var10])
					{
						var13 = 2;
					}
				}
			} else
			{
				var10 = p_72290_1_.getBlockId(p_72290_2_ - 1, p_72290_3_, var5 == blockID ? p_72290_4_ - 1 : p_72290_4_ + 1);
				var11 = p_72290_1_.getBlockId(p_72290_2_ + 1, p_72290_3_, var5 == blockID ? p_72290_4_ - 1 : p_72290_4_ + 1);
				var13 = 5;
				var12 = true;
				if(var5 == blockID)
				{
					var14 = p_72290_1_.getBlockMetadata(p_72290_2_, p_72290_3_, p_72290_4_ - 1);
				} else
				{
					var14 = p_72290_1_.getBlockMetadata(p_72290_2_, p_72290_3_, p_72290_4_ + 1);
				}
				if(var14 == 4)
				{
					var13 = 4;
				}
				if((Block.opaqueCubeLookup[var7] || Block.opaqueCubeLookup[var10]) && !Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var11])
				{
					var13 = 5;
				}
				if((Block.opaqueCubeLookup[var8] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var10])
				{
					var13 = 4;
				}
			}
			p_72290_1_.setBlockMetadataWithNotify(p_72290_2_, p_72290_3_, p_72290_4_, var13, 3);
		}
	}
	
	private static boolean isOcelotBlockingChest(World p_72292_0_, int p_72292_1_, int p_72292_2_, int p_72292_3_)
	{
		Iterator var4 = p_72292_0_.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB(p_72292_1_, p_72292_2_ + 1, p_72292_3_, p_72292_1_ + 1, p_72292_2_ + 2, p_72292_3_ + 1)).iterator();
		EntityOcelot var6;
		do
		{
			if(!var4.hasNext()) return false;
			EntityOcelot var5 = (EntityOcelot) var4.next();
			var6 = var5;
		} while(!var6.isSitting());
		return true;
	}
}
