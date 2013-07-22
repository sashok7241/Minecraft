package net.minecraft.src;

public class InventoryCraftResult implements IInventory
{
	private ItemStack[] stackResult = new ItemStack[1];
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
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
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return stackResult[0];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
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
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		stackResult[0] = par2ItemStack;
	}
}
