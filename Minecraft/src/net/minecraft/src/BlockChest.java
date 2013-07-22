package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class BlockChest extends BlockContainer
{
	private final Random random = new Random();
	public final int isTrapped;
	
	protected BlockChest(int par1, int par2)
	{
		super(par1, Material.wood);
		isTrapped = par2;
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityChest var7 = (TileEntityChest) par1World.getBlockTileEntity(par2, par3, par4);
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
					for(float var12 = random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14))
					{
						int var13 = random.nextInt(21) + 10;
						if(var13 > var9.stackSize)
						{
							var13 = var9.stackSize;
						}
						var9.stackSize -= var13;
						var14 = new EntityItem(par1World, par2 + var10, par3 + var11, par4 + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
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
			par1World.func_96440_m(par2, par3, par4, par5);
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = 0;
		if(par1World.getBlockId(par2 - 1, par3, par4) == blockID)
		{
			++var5;
		}
		if(par1World.getBlockId(par2 + 1, par3, par4) == blockID)
		{
			++var5;
		}
		if(par1World.getBlockId(par2, par3, par4 - 1) == blockID)
		{
			++var5;
		}
		if(par1World.getBlockId(par2, par3, par4 + 1) == blockID)
		{
			++var5;
		}
		return var5 > 1 ? false : isThereANeighborChest(par1World, par2 - 1, par3, par4) ? false : isThereANeighborChest(par1World, par2 + 1, par3, par4) ? false : isThereANeighborChest(par1World, par2, par3, par4 - 1) ? false : !isThereANeighborChest(par1World, par2, par3, par4 + 1);
	}
	
	@Override public boolean canProvidePower()
	{
		return isTrapped == 1;
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		TileEntityChest var2 = new TileEntityChest();
		return var2;
	}
	
	@Override public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory(getInventory(par1World, par2, par3, par4));
	}
	
	public IInventory getInventory(World par1World, int par2, int par3, int par4)
	{
		Object var5 = par1World.getBlockTileEntity(par2, par3, par4);
		if(var5 == null) return null;
		else if(par1World.isBlockNormalCube(par2, par3 + 1, par4)) return null;
		else if(isOcelotBlockingChest(par1World, par2, par3, par4)) return null;
		else if(par1World.getBlockId(par2 - 1, par3, par4) == blockID && (par1World.isBlockNormalCube(par2 - 1, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2 - 1, par3, par4))) return null;
		else if(par1World.getBlockId(par2 + 1, par3, par4) == blockID && (par1World.isBlockNormalCube(par2 + 1, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2 + 1, par3, par4))) return null;
		else if(par1World.getBlockId(par2, par3, par4 - 1) == blockID && (par1World.isBlockNormalCube(par2, par3 + 1, par4 - 1) || isOcelotBlockingChest(par1World, par2, par3, par4 - 1))) return null;
		else if(par1World.getBlockId(par2, par3, par4 + 1) == blockID && (par1World.isBlockNormalCube(par2, par3 + 1, par4 + 1) || isOcelotBlockingChest(par1World, par2, par3, par4 + 1))) return null;
		else
		{
			if(par1World.getBlockId(par2 - 1, par3, par4) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (TileEntityChest) par1World.getBlockTileEntity(par2 - 1, par3, par4), (IInventory) var5);
			}
			if(par1World.getBlockId(par2 + 1, par3, par4) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (IInventory) var5, (TileEntityChest) par1World.getBlockTileEntity(par2 + 1, par3, par4));
			}
			if(par1World.getBlockId(par2, par3, par4 - 1) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (TileEntityChest) par1World.getBlockTileEntity(par2, par3, par4 - 1), (IInventory) var5);
			}
			if(par1World.getBlockId(par2, par3, par4 + 1) == blockID)
			{
				var5 = new InventoryLargeChest("container.chestDouble", (IInventory) var5, (TileEntityChest) par1World.getBlockTileEntity(par2, par3, par4 + 1));
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
	
	@Override public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 1 ? isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5) : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(!canProvidePower()) return 0;
		else
		{
			int var6 = ((TileEntityChest) par1IBlockAccess.getBlockTileEntity(par2, par3, par4)).numUsingPlayers;
			return MathHelper.clamp_int(var6, 0, 15);
		}
	}
	
	private boolean isThereANeighborChest(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockId(par2, par3, par4) != blockID ? false : par1World.getBlockId(par2 - 1, par3, par4) == blockID ? true : par1World.getBlockId(par2 + 1, par3, par4) == blockID ? true : par1World.getBlockId(par2, par3, par4 - 1) == blockID ? true : par1World.getBlockId(par2, par3, par4 + 1) == blockID;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			IInventory var10 = getInventory(par1World, par2, par3, par4);
			if(var10 != null)
			{
				par5EntityPlayer.displayGUIChest(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		unifyAdjacentChests(par1World, par2, par3, par4);
		int var5 = par1World.getBlockId(par2, par3, par4 - 1);
		int var6 = par1World.getBlockId(par2, par3, par4 + 1);
		int var7 = par1World.getBlockId(par2 - 1, par3, par4);
		int var8 = par1World.getBlockId(par2 + 1, par3, par4);
		if(var5 == blockID)
		{
			unifyAdjacentChests(par1World, par2, par3, par4 - 1);
		}
		if(var6 == blockID)
		{
			unifyAdjacentChests(par1World, par2, par3, par4 + 1);
		}
		if(var7 == blockID)
		{
			unifyAdjacentChests(par1World, par2 - 1, par3, par4);
		}
		if(var8 == blockID)
		{
			unifyAdjacentChests(par1World, par2 + 1, par3, par4);
		}
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int var7 = par1World.getBlockId(par2, par3, par4 - 1);
		int var8 = par1World.getBlockId(par2, par3, par4 + 1);
		int var9 = par1World.getBlockId(par2 - 1, par3, par4);
		int var10 = par1World.getBlockId(par2 + 1, par3, par4);
		byte var11 = 0;
		int var12 = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
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
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
		} else
		{
			if((var7 == blockID || var8 == blockID) && (var11 == 4 || var11 == 5))
			{
				if(var7 == blockID)
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, var11, 3);
				} else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, var11, 3);
				}
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
			}
			if((var9 == blockID || var10 == blockID) && (var11 == 2 || var11 == 3))
			{
				if(var9 == blockID)
				{
					par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, var11, 3);
				} else
				{
					par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, var11, 3);
				}
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 3);
			}
		}
		if(par6ItemStack.hasDisplayName())
		{
			((TileEntityChest) par1World.getBlockTileEntity(par2, par3, par4)).setChestGuiName(par6ItemStack.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		TileEntityChest var6 = (TileEntityChest) par1World.getBlockTileEntity(par2, par3, par4);
		if(var6 != null)
		{
			var6.updateContainingBlockInfo();
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("planks_oak");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if(par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == blockID)
		{
			setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
		} else if(par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == blockID)
		{
			setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
		} else if(par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == blockID)
		{
			setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		} else if(par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == blockID)
		{
			setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
		} else
		{
			setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		}
	}
	
	public void unifyAdjacentChests(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.isRemote)
		{
			int var5 = par1World.getBlockId(par2, par3, par4 - 1);
			int var6 = par1World.getBlockId(par2, par3, par4 + 1);
			int var7 = par1World.getBlockId(par2 - 1, par3, par4);
			int var8 = par1World.getBlockId(par2 + 1, par3, par4);
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
					var10 = par1World.getBlockId(var7 == blockID ? par2 - 1 : par2 + 1, par3, par4 - 1);
					var11 = par1World.getBlockId(var7 == blockID ? par2 - 1 : par2 + 1, par3, par4 + 1);
					var13 = 3;
					var12 = true;
					if(var7 == blockID)
					{
						var14 = par1World.getBlockMetadata(par2 - 1, par3, par4);
					} else
					{
						var14 = par1World.getBlockMetadata(par2 + 1, par3, par4);
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
				var10 = par1World.getBlockId(par2 - 1, par3, var5 == blockID ? par4 - 1 : par4 + 1);
				var11 = par1World.getBlockId(par2 + 1, par3, var5 == blockID ? par4 - 1 : par4 + 1);
				var13 = 5;
				var12 = true;
				if(var5 == blockID)
				{
					var14 = par1World.getBlockMetadata(par2, par3, par4 - 1);
				} else
				{
					var14 = par1World.getBlockMetadata(par2, par3, par4 + 1);
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
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var13, 3);
		}
	}
	
	private static boolean isOcelotBlockingChest(World par0World, int par1, int par2, int par3)
	{
		Iterator var4 = par0World.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB(par1, par2 + 1, par3, par1 + 1, par2 + 2, par3 + 1)).iterator();
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
