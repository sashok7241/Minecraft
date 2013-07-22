package net.minecraft.src;

import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory
{
	private ItemStack[] dispenserContents = new ItemStack[9];
	private Random dispenserRandom = new Random();
	protected String customName;
	
	public int addItem(ItemStack p_70360_1_)
	{
		for(int var2 = 0; var2 < dispenserContents.length; ++var2)
		{
			if(dispenserContents[var2] == null || dispenserContents[var2].itemID == 0)
			{
				setInventorySlotContents(var2, p_70360_1_);
				return var2;
			}
		}
		return -1;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(dispenserContents[p_70298_1_] != null)
		{
			ItemStack var3;
			if(dispenserContents[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = dispenserContents[p_70298_1_];
				dispenserContents[p_70298_1_] = null;
				onInventoryChanged();
				return var3;
			} else
			{
				var3 = dispenserContents[p_70298_1_].splitStack(p_70298_2_);
				if(dispenserContents[p_70298_1_].stackSize == 0)
				{
					dispenserContents[p_70298_1_] = null;
				}
				onInventoryChanged();
				return var3;
			}
		} else return null;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? customName : "container.dispenser";
	}
	
	public int getRandomStackFromInventory()
	{
		int var1 = -1;
		int var2 = 1;
		for(int var3 = 0; var3 < dispenserContents.length; ++var3)
		{
			if(dispenserContents[var3] != null && dispenserRandom.nextInt(var2++) == 0)
			{
				var1 = var3;
			}
		}
		return var1;
	}
	
	@Override public int getSizeInventory()
	{
		return 9;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return dispenserContents[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(dispenserContents[p_70304_1_] != null)
		{
			ItemStack var2 = dispenserContents[p_70304_1_];
			dispenserContents[p_70304_1_] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return customName != null;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : p_70300_1_.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		NBTTagList var2 = p_70307_1_.getTagList("Items");
		dispenserContents = new ItemStack[getSizeInventory()];
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			if(var5 >= 0 && var5 < dispenserContents.length)
			{
				dispenserContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		if(p_70307_1_.hasKey("CustomName"))
		{
			customName = p_70307_1_.getString("CustomName");
		}
	}
	
	public void setCustomName(String p_94049_1_)
	{
		customName = p_94049_1_;
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		dispenserContents[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
		{
			p_70299_2_.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < dispenserContents.length; ++var3)
		{
			if(dispenserContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				dispenserContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		p_70310_1_.setTag("Items", var2);
		if(isInvNameLocalized())
		{
			p_70310_1_.setString("CustomName", customName);
		}
	}
}
