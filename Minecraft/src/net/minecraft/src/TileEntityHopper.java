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
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(hopperItemStacks[par1] != null)
		{
			ItemStack var3;
			if(hopperItemStacks[par1].stackSize <= par2)
			{
				var3 = hopperItemStacks[par1];
				hopperItemStacks[par1] = null;
				return var3;
			} else
			{
				var3 = hopperItemStacks[par1].splitStack(par2);
				if(hopperItemStacks[par1].stackSize == 0)
				{
					hopperItemStacks[par1] = null;
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
				boolean var1 = insertItemToInventory();
				var1 = suckItemsIntoHopper(this) || var1;
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
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return hopperItemStacks[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(hopperItemStacks[par1] != null)
		{
			ItemStack var2 = hopperItemStacks[par1];
			hopperItemStacks[par1] = null;
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
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void onInventoryChanged()
	{
		super.onInventoryChanged();
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		hopperItemStacks = new ItemStack[getSizeInventory()];
		if(par1NBTTagCompound.hasKey("CustomName"))
		{
			inventoryName = par1NBTTagCompound.getString("CustomName");
		}
		transferCooldown = par1NBTTagCompound.getInteger("TransferCooldown");
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
	
	public void setInventoryName(String par1Str)
	{
		inventoryName = par1Str;
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		hopperItemStacks[par1] = par2ItemStack;
		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
		{
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
	}
	
	public void setTransferCooldown(int par1)
	{
		transferCooldown = par1;
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
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
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
		par1NBTTagCompound.setTag("Items", var2);
		par1NBTTagCompound.setInteger("TransferCooldown", transferCooldown);
		if(isInvNameLocalized())
		{
			par1NBTTagCompound.setString("CustomName", inventoryName);
		}
	}
	
	private static boolean areItemStacksEqualItem(ItemStack par0ItemStack, ItemStack par1ItemStack)
	{
		return par0ItemStack.itemID != par1ItemStack.itemID ? false : par0ItemStack.getItemDamage() != par1ItemStack.getItemDamage() ? false : par0ItemStack.stackSize > par0ItemStack.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(par0ItemStack, par1ItemStack);
	}
	
	private static boolean canExtractItemFromInventory(IInventory par0IInventory, ItemStack par1ItemStack, int par2, int par3)
	{
		return !(par0IInventory instanceof ISidedInventory) || ((ISidedInventory) par0IInventory).canExtractItem(par2, par1ItemStack, par3);
	}
	
	private static boolean canInsertItemToInventory(IInventory par0IInventory, ItemStack par1ItemStack, int par2, int par3)
	{
		return !par0IInventory.isItemValidForSlot(par2, par1ItemStack) ? false : !(par0IInventory instanceof ISidedInventory) || ((ISidedInventory) par0IInventory).canInsertItem(par2, par1ItemStack, par3);
	}
	
	private static boolean func_102012_a(Hopper par0Hopper, IInventory par1IInventory, int par2, int par3)
	{
		ItemStack var4 = par1IInventory.getStackInSlot(par2);
		if(var4 != null && canExtractItemFromInventory(par1IInventory, var4, par2, par3))
		{
			ItemStack var5 = var4.copy();
			ItemStack var6 = insertStack(par0Hopper, par1IInventory.decrStackSize(par2, 1), -1);
			if(var6 == null || var6.stackSize == 0)
			{
				par1IInventory.onInventoryChanged();
				return true;
			}
			par1IInventory.setInventorySlotContents(par2, var5);
		}
		return false;
	}
	
	private static ItemStack func_102014_c(IInventory par0IInventory, ItemStack par1ItemStack, int par2, int par3)
	{
		ItemStack var4 = par0IInventory.getStackInSlot(par2);
		if(canInsertItemToInventory(par0IInventory, par1ItemStack, par2, par3))
		{
			boolean var5 = false;
			if(var4 == null)
			{
				par0IInventory.setInventorySlotContents(par2, par1ItemStack);
				par1ItemStack = null;
				var5 = true;
			} else if(areItemStacksEqualItem(var4, par1ItemStack))
			{
				int var6 = par1ItemStack.getMaxStackSize() - var4.stackSize;
				int var7 = Math.min(par1ItemStack.stackSize, var6);
				par1ItemStack.stackSize -= var7;
				var4.stackSize += var7;
				var5 = var7 > 0;
			}
			if(var5)
			{
				if(par0IInventory instanceof TileEntityHopper)
				{
					((TileEntityHopper) par0IInventory).setTransferCooldown(8);
					par0IInventory.onInventoryChanged();
				}
				par0IInventory.onInventoryChanged();
			}
		}
		return par1ItemStack;
	}
	
	public static boolean func_96114_a(IInventory par0IInventory, EntityItem par1EntityItem)
	{
		boolean var2 = false;
		if(par1EntityItem == null) return false;
		else
		{
			ItemStack var3 = par1EntityItem.getEntityItem().copy();
			ItemStack var4 = insertStack(par0IInventory, var3, -1);
			if(var4 != null && var4.stackSize != 0)
			{
				par1EntityItem.setEntityItemStack(var4);
			} else
			{
				var2 = true;
				par1EntityItem.setDead();
			}
			return var2;
		}
	}
	
	public static EntityItem func_96119_a(World par0World, double par1, double par3, double par5)
	{
		List var7 = par0World.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5, par1 + 1.0D, par3 + 1.0D, par5 + 1.0D), IEntitySelector.selectAnything);
		return var7.size() > 0 ? (EntityItem) var7.get(0) : null;
	}
	
	public static IInventory getInventoryAboveHopper(Hopper par0Hopper)
	{
		return getInventoryAtLocation(par0Hopper.getWorldObj(), par0Hopper.getXPos(), par0Hopper.getYPos() + 1.0D, par0Hopper.getZPos());
	}
	
	public static IInventory getInventoryAtLocation(World par0World, double par1, double par3, double par5)
	{
		IInventory var7 = null;
		int var8 = MathHelper.floor_double(par1);
		int var9 = MathHelper.floor_double(par3);
		int var10 = MathHelper.floor_double(par5);
		TileEntity var11 = par0World.getBlockTileEntity(var8, var9, var10);
		if(var11 != null && var11 instanceof IInventory)
		{
			var7 = (IInventory) var11;
			if(var7 instanceof TileEntityChest)
			{
				int var12 = par0World.getBlockId(var8, var9, var10);
				Block var13 = Block.blocksList[var12];
				if(var13 instanceof BlockChest)
				{
					var7 = ((BlockChest) var13).getInventory(par0World, var8, var9, var10);
				}
			}
		}
		if(var7 == null)
		{
			List var14 = par0World.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5, par1 + 1.0D, par3 + 1.0D, par5 + 1.0D), IEntitySelector.selectInventories);
			if(var14 != null && var14.size() > 0)
			{
				var7 = (IInventory) var14.get(par0World.rand.nextInt(var14.size()));
			}
		}
		return var7;
	}
	
	public static ItemStack insertStack(IInventory par0IInventory, ItemStack par1ItemStack, int par2)
	{
		if(par0IInventory instanceof ISidedInventory && par2 > -1)
		{
			ISidedInventory var6 = (ISidedInventory) par0IInventory;
			int[] var7 = var6.getAccessibleSlotsFromSide(par2);
			for(int var5 = 0; var5 < var7.length && par1ItemStack != null && par1ItemStack.stackSize > 0; ++var5)
			{
				par1ItemStack = func_102014_c(par0IInventory, par1ItemStack, var7[var5], par2);
			}
		} else
		{
			int var3 = par0IInventory.getSizeInventory();
			for(int var4 = 0; var4 < var3 && par1ItemStack != null && par1ItemStack.stackSize > 0; ++var4)
			{
				par1ItemStack = func_102014_c(par0IInventory, par1ItemStack, var4, par2);
			}
		}
		if(par1ItemStack != null && par1ItemStack.stackSize == 0)
		{
			par1ItemStack = null;
		}
		return par1ItemStack;
	}
	
	public static boolean suckItemsIntoHopper(Hopper par0Hopper)
	{
		IInventory var1 = getInventoryAboveHopper(par0Hopper);
		if(var1 != null)
		{
			byte var2 = 0;
			if(var1 instanceof ISidedInventory && var2 > -1)
			{
				ISidedInventory var7 = (ISidedInventory) var1;
				int[] var8 = var7.getAccessibleSlotsFromSide(var2);
				for(int element : var8)
				{
					if(func_102012_a(par0Hopper, var1, element, var2)) return true;
				}
			} else
			{
				int var3 = var1.getSizeInventory();
				for(int var4 = 0; var4 < var3; ++var4)
				{
					if(func_102012_a(par0Hopper, var1, var4, var2)) return true;
				}
			}
		} else
		{
			EntityItem var6 = func_96119_a(par0Hopper.getWorldObj(), par0Hopper.getXPos(), par0Hopper.getYPos() + 1.0D, par0Hopper.getZPos());
			if(var6 != null) return func_96114_a(par0Hopper, var6);
		}
		return false;
	}
}
