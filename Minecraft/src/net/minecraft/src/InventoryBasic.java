package net.minecraft.src;

import java.util.List;

public class InventoryBasic implements IInventory
{
	private String inventoryTitle;
	private int slotsCount;
	private ItemStack[] inventoryContents;
	private List field_70480_d;
	private boolean field_94051_e;
	
	public InventoryBasic(String p_i9018_1_, boolean p_i9018_2_, int p_i9018_3_)
	{
		inventoryTitle = p_i9018_1_;
		field_94051_e = p_i9018_2_;
		slotsCount = p_i9018_3_;
		inventoryContents = new ItemStack[p_i9018_3_];
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(inventoryContents[p_70298_1_] != null)
		{
			ItemStack var3;
			if(inventoryContents[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = inventoryContents[p_70298_1_];
				inventoryContents[p_70298_1_] = null;
				onInventoryChanged();
				return var3;
			} else
			{
				var3 = inventoryContents[p_70298_1_].splitStack(p_70298_2_);
				if(inventoryContents[p_70298_1_].stackSize == 0)
				{
					inventoryContents[p_70298_1_] = null;
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
		return inventoryTitle;
	}
	
	@Override public int getSizeInventory()
	{
		return slotsCount;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return inventoryContents[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(inventoryContents[p_70304_1_] != null)
		{
			ItemStack var2 = inventoryContents[p_70304_1_];
			inventoryContents[p_70304_1_] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return field_94051_e;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
		if(field_70480_d != null)
		{
			for(int var1 = 0; var1 < field_70480_d.size(); ++var1)
			{
				((IInvBasic) field_70480_d.get(var1)).onInventoryChanged(this);
			}
		}
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		inventoryContents[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
		{
			p_70299_2_.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
}
