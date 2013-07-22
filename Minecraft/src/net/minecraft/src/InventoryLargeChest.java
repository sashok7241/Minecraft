package net.minecraft.src;

public class InventoryLargeChest implements IInventory
{
	private String name;
	private IInventory upperChest;
	private IInventory lowerChest;
	
	public InventoryLargeChest(String par1Str, IInventory par2IInventory, IInventory par3IInventory)
	{
		name = par1Str;
		if(par2IInventory == null)
		{
			par2IInventory = par3IInventory;
		}
		if(par3IInventory == null)
		{
			par3IInventory = par2IInventory;
		}
		upperChest = par2IInventory;
		lowerChest = par3IInventory;
	}
	
	@Override public void closeChest()
	{
		upperChest.closeChest();
		lowerChest.closeChest();
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		return par1 >= upperChest.getSizeInventory() ? lowerChest.decrStackSize(par1 - upperChest.getSizeInventory(), par2) : upperChest.decrStackSize(par1, par2);
	}
	
	@Override public int getInventoryStackLimit()
	{
		return upperChest.getInventoryStackLimit();
	}
	
	@Override public String getInvName()
	{
		return upperChest.isInvNameLocalized() ? upperChest.getInvName() : lowerChest.isInvNameLocalized() ? lowerChest.getInvName() : name;
	}
	
	@Override public int getSizeInventory()
	{
		return upperChest.getSizeInventory() + lowerChest.getSizeInventory();
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return par1 >= upperChest.getSizeInventory() ? lowerChest.getStackInSlot(par1 - upperChest.getSizeInventory()) : upperChest.getStackInSlot(par1);
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		return par1 >= upperChest.getSizeInventory() ? lowerChest.getStackInSlotOnClosing(par1 - upperChest.getSizeInventory()) : upperChest.getStackInSlotOnClosing(par1);
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return upperChest.isInvNameLocalized() || lowerChest.isInvNameLocalized();
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	public boolean isPartOfLargeChest(IInventory par1IInventory)
	{
		return upperChest == par1IInventory || lowerChest == par1IInventory;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return upperChest.isUseableByPlayer(par1EntityPlayer) && lowerChest.isUseableByPlayer(par1EntityPlayer);
	}
	
	@Override public void onInventoryChanged()
	{
		upperChest.onInventoryChanged();
		lowerChest.onInventoryChanged();
	}
	
	@Override public void openChest()
	{
		upperChest.openChest();
		lowerChest.openChest();
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		if(par1 >= upperChest.getSizeInventory())
		{
			lowerChest.setInventorySlotContents(par1 - upperChest.getSizeInventory(), par2ItemStack);
		} else
		{
			upperChest.setInventorySlotContents(par1, par2ItemStack);
		}
	}
}
