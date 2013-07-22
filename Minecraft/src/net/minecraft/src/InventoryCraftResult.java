package net.minecraft.src;

public class InventoryCraftResult implements IInventory
{
	private ItemStack[] stackResult = new ItemStack[1];
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(stackResult[0] != null)
		{
			ItemStack var3 = stackResult[0];
			stackResult[0] = null;
			return var3;
		} else return null;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return "Result";
	}
	
	@Override public int getSizeInventory()
	{
		return 1;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return stackResult[0];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(stackResult[0] != null)
		{
			ItemStack var2 = stackResult[0];
			stackResult[0] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return false;
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
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		stackResult[0] = p_70299_2_;
	}
}
