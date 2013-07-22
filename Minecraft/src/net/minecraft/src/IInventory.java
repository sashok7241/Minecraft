package net.minecraft.src;

public interface IInventory
{
	void closeChest();
	
	ItemStack decrStackSize(int var1, int var2);
	
	int getInventoryStackLimit();
	
	String getInvName();
	
	int getSizeInventory();
	
	ItemStack getStackInSlot(int var1);
	
	ItemStack getStackInSlotOnClosing(int var1);
	
	boolean isInvNameLocalized();
	
	boolean isItemValidForSlot(int var1, ItemStack var2);
	
	boolean isUseableByPlayer(EntityPlayer var1);
	
	void onInventoryChanged();
	
	void openChest();
	
	void setInventorySlotContents(int var1, ItemStack var2);
}
