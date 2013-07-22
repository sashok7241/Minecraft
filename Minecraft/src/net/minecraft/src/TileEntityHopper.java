package net.minecraft.src;

import java.util.List;

public class TileEntityHopper extends TileEntity implements Hopper
{
	private ItemStack[] hopperItemStacks = new ItemStack[5];
	private String inventoryName;
	private int transferCooldown = -1;
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(hopperItemStacks[p_70298_1_] != null)
		{
			ItemStack var3;
			if(hopperItemStacks[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = hopperItemStacks[p_70298_1_];
				hopperItemStacks[p_70298_1_] = null;
				return var3;
			} else
			{
				var3 = hopperItemStacks[p_70298_1_].splitStack(p_70298_2_);
				if(hopperItemStacks[p_70298_1_].stackSize == 0)
				{
					hopperItemStacks[p_70298_1_] = null;
				}
				return var3;
			}
		} else return null;
	}
	
	public boolean func_98045_j()
	{
		if(worldObj != null && !worldObj.isRemote)
		{
			if(!isCoolingDown() && BlockHopper.getIsBlockNotPoweredFromMetadata(getBlockMetadata()))
			{
				boolean var1 = insertItemToInventory() | suckItemsIntoHopper(this);
				if(var1)
				{
					setTransferCooldown(8);
					onInventoryChanged();
					return true;
				}
			}
			return false;
		} else return false;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? inventoryName : "container.hopper";
	}
	
	private IInventory getOutputInventory()
	{
		int var1 = BlockHopper.getDirectionFromMetadata(getBlockMetadata());
		return getInventoryAtLocation(getWorldObj(), xCoord + Facing.offsetsXForSide[var1], yCoord + Facing.offsetsYForSide[var1], zCoord + Facing.offsetsZForSide[var1]);
	}
	
	@Override public int getSizeInventory()
	{
		return hopperItemStacks.length;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return hopperItemStacks[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(hopperItemStacks[p_70304_1_] != null)
		{
			ItemStack var2 = hopperItemStacks[p_70304_1_];
			hopperItemStacks[p_70304_1_] = null;
			return var2;
		} else return null;
	}
	
	@Override public double getXPos()
	{
		return xCoord;
	}
	
	@Override public double getYPos()
	{
		return yCoord;
	}
	
	@Override public double getZPos()
	{
		return zCoord;
	}
	
	private boolean insertItemToInventory()
	{
		IInventory var1 = getOutputInventory();
		if(var1 == null) return false;
		else
		{
			for(int var2 = 0; var2 < getSizeInventory(); ++var2)
			{
				if(getStackInSlot(var2) != null)
				{
					ItemStack var3 = getStackInSlot(var2).copy();
					ItemStack var4 = insertStack(var1, decrStackSize(var2, 1), Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(getBlockMetadata())]);
					if(var4 == null || var4.stackSize == 0)
					{
						var1.onInventoryChanged();
						return true;
					}
					setInventorySlotContents(var2, var3);
				}
			}
			return false;
		}
	}
	
	public boolean isCoolingDown()
	{
		return transferCooldown > 0;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return inventoryName != null && inventoryName.length() > 0;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : p_70300_1_.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void onInventoryChanged()
	{
		super.onInventoryChanged();
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		NBTTagList var2 = p_70307_1_.getTagList("Items");
		hopperItemStacks = new ItemStack[getSizeInventory()];
		if(p_70307_1_.hasKey("CustomName"))
		{
			inventoryName = p_70307_1_.getString("CustomName");
		}
		transferCooldown = p_70307_1_.getInteger("TransferCooldown");
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if(var5 >= 0 && var5 < hopperItemStacks.length)
			{
				hopperItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}
	
	public void setInventoryName(String p_96115_1_)
	{
		inventoryName = p_96115_1_;
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		hopperItemStacks[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
		{
			p_70299_2_.stackSize = getInventoryStackLimit();
		}
	}
	
	public void setTransferCooldown(int p_98046_1_)
	{
		transferCooldown = p_98046_1_;
	}
	
	@Override public void updateEntity()
	{
		if(worldObj != null && !worldObj.isRemote)
		{
			--transferCooldown;
			if(!isCoolingDown())
			{
				setTransferCooldown(0);
				func_98045_j();
			}
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < hopperItemStacks.length; ++var3)
		{
			if(hopperItemStacks[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				hopperItemStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		p_70310_1_.setTag("Items", var2);
		p_70310_1_.setInteger("TransferCooldown", transferCooldown);
		if(isInvNameLocalized())
		{
			p_70310_1_.setString("CustomName", inventoryName);
		}
	}
	
	private static boolean areItemStacksEqualItem(ItemStack p_94114_1_, ItemStack p_94114_2_)
	{
		return p_94114_1_.itemID != p_94114_2_.itemID ? false : p_94114_1_.getItemDamage() != p_94114_2_.getItemDamage() ? false : p_94114_1_.stackSize > p_94114_1_.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(p_94114_1_, p_94114_2_);
	}
	
	private static boolean canExtractItemFromInventory(IInventory p_102013_0_, ItemStack p_102013_1_, int p_102013_2_, int p_102013_3_)
	{
		return !(p_102013_0_ instanceof ISidedInventory) || ((ISidedInventory) p_102013_0_).canExtractItem(p_102013_2_, p_102013_1_, p_102013_3_);
	}
	
	private static boolean canInsertItemToInventory(IInventory p_102015_0_, ItemStack p_102015_1_, int p_102015_2_, int p_102015_3_)
	{
		return !p_102015_0_.isItemValidForSlot(p_102015_2_, p_102015_1_) ? false : !(p_102015_0_ instanceof ISidedInventory) || ((ISidedInventory) p_102015_0_).canInsertItem(p_102015_2_, p_102015_1_, p_102015_3_);
	}
	
	private static boolean func_102012_a(Hopper p_102012_0_, IInventory p_102012_1_, int p_102012_2_, int p_102012_3_)
	{
		ItemStack var4 = p_102012_1_.getStackInSlot(p_102012_2_);
		if(var4 != null && canExtractItemFromInventory(p_102012_1_, var4, p_102012_2_, p_102012_3_))
		{
			ItemStack var5 = var4.copy();
			ItemStack var6 = insertStack(p_102012_0_, p_102012_1_.decrStackSize(p_102012_2_, 1), -1);
			if(var6 == null || var6.stackSize == 0)
			{
				p_102012_1_.onInventoryChanged();
				return true;
			}
			p_102012_1_.setInventorySlotContents(p_102012_2_, var5);
		}
		return false;
	}
	
	private static ItemStack func_102014_c(IInventory p_102014_0_, ItemStack p_102014_1_, int p_102014_2_, int p_102014_3_)
	{
		ItemStack var4 = p_102014_0_.getStackInSlot(p_102014_2_);
		if(canInsertItemToInventory(p_102014_0_, p_102014_1_, p_102014_2_, p_102014_3_))
		{
			boolean var5 = false;
			if(var4 == null)
			{
				p_102014_0_.setInventorySlotContents(p_102014_2_, p_102014_1_);
				p_102014_1_ = null;
				var5 = true;
			} else if(areItemStacksEqualItem(var4, p_102014_1_))
			{
				int var6 = p_102014_1_.getMaxStackSize() - var4.stackSize;
				int var7 = Math.min(p_102014_1_.stackSize, var6);
				p_102014_1_.stackSize -= var7;
				var4.stackSize += var7;
				var5 = var7 > 0;
			}
			if(var5)
			{
				if(p_102014_0_ instanceof TileEntityHopper)
				{
					((TileEntityHopper) p_102014_0_).setTransferCooldown(8);
				}
				p_102014_0_.onInventoryChanged();
			}
		}
		return p_102014_1_;
	}
	
	public static boolean func_96114_a(IInventory p_96114_0_, EntityItem p_96114_1_)
	{
		boolean var2 = false;
		if(p_96114_1_ == null) return false;
		else
		{
			ItemStack var3 = p_96114_1_.getEntityItem().copy();
			ItemStack var4 = insertStack(p_96114_0_, var3, -1);
			if(var4 != null && var4.stackSize != 0)
			{
				p_96114_1_.setEntityItemStack(var4);
			} else
			{
				var2 = true;
				p_96114_1_.setDead();
			}
			return var2;
		}
	}
	
	public static EntityItem func_96119_a(World p_96119_0_, double p_96119_1_, double p_96119_3_, double p_96119_5_)
	{
		List var7 = p_96119_0_.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(p_96119_1_, p_96119_3_, p_96119_5_, p_96119_1_ + 1.0D, p_96119_3_ + 1.0D, p_96119_5_ + 1.0D), IEntitySelector.selectAnything);
		return var7.size() > 0 ? (EntityItem) var7.get(0) : null;
	}
	
	public static IInventory getInventoryAboveHopper(Hopper p_96118_0_)
	{
		return getInventoryAtLocation(p_96118_0_.getWorldObj(), p_96118_0_.getXPos(), p_96118_0_.getYPos() + 1.0D, p_96118_0_.getZPos());
	}
	
	public static IInventory getInventoryAtLocation(World p_96117_0_, double p_96117_1_, double p_96117_3_, double p_96117_5_)
	{
		IInventory var7 = null;
		int var8 = MathHelper.floor_double(p_96117_1_);
		int var9 = MathHelper.floor_double(p_96117_3_);
		int var10 = MathHelper.floor_double(p_96117_5_);
		TileEntity var11 = p_96117_0_.getBlockTileEntity(var8, var9, var10);
		if(var11 != null && var11 instanceof IInventory)
		{
			var7 = (IInventory) var11;
			if(var7 instanceof TileEntityChest)
			{
				int var12 = p_96117_0_.getBlockId(var8, var9, var10);
				Block var13 = Block.blocksList[var12];
				if(var13 instanceof BlockChest)
				{
					var7 = ((BlockChest) var13).getInventory(p_96117_0_, var8, var9, var10);
				}
			}
		}
		if(var7 == null)
		{
			List var14 = p_96117_0_.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(p_96117_1_, p_96117_3_, p_96117_5_, p_96117_1_ + 1.0D, p_96117_3_ + 1.0D, p_96117_5_ + 1.0D), IEntitySelector.selectInventories);
			if(var14 != null && var14.size() > 0)
			{
				var7 = (IInventory) var14.get(p_96117_0_.rand.nextInt(var14.size()));
			}
		}
		return var7;
	}
	
	public static ItemStack insertStack(IInventory p_94117_1_, ItemStack p_94117_2_, int p_94117_3_)
	{
		if(p_94117_1_ instanceof ISidedInventory && p_94117_3_ > -1)
		{
			ISidedInventory var6 = (ISidedInventory) p_94117_1_;
			int[] var7 = var6.getAccessibleSlotsFromSide(p_94117_3_);
			for(int var5 = 0; var5 < var7.length && p_94117_2_ != null && p_94117_2_.stackSize > 0; ++var5)
			{
				p_94117_2_ = func_102014_c(p_94117_1_, p_94117_2_, var7[var5], p_94117_3_);
			}
		} else
		{
			int var3 = p_94117_1_.getSizeInventory();
			for(int var4 = 0; var4 < var3 && p_94117_2_ != null && p_94117_2_.stackSize > 0; ++var4)
			{
				p_94117_2_ = func_102014_c(p_94117_1_, p_94117_2_, var4, p_94117_3_);
			}
		}
		if(p_94117_2_ != null && p_94117_2_.stackSize == 0)
		{
			p_94117_2_ = null;
		}
		return p_94117_2_;
	}
	
	public static boolean suckItemsIntoHopper(Hopper p_96116_0_)
	{
		IInventory var1 = getInventoryAboveHopper(p_96116_0_);
		if(var1 != null)
		{
			byte var2 = 0;
			if(var1 instanceof ISidedInventory && var2 > -1)
			{
				ISidedInventory var7 = (ISidedInventory) var1;
				int[] var8 = var7.getAccessibleSlotsFromSide(var2);
				for(int element : var8)
				{
					if(func_102012_a(p_96116_0_, var1, element, var2)) return true;
				}
			} else
			{
				int var3 = var1.getSizeInventory();
				for(int var4 = 0; var4 < var3; ++var4)
				{
					if(func_102012_a(p_96116_0_, var1, var4, var2)) return true;
				}
			}
		} else
		{
			EntityItem var6 = func_96119_a(p_96116_0_.getWorldObj(), p_96116_0_.getXPos(), p_96116_0_.getYPos() + 1.0D, p_96116_0_.getZPos());
			if(var6 != null) return func_96114_a(p_96116_0_, var6);
		}
		return false;
	}
}
