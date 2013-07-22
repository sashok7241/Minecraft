package net.minecraft.src;

public class InventoryCrafting implements IInventory
{
	private ItemStack[] stackList;
	private int inventoryWidth;
	private Container eventHandler;
	
	public InventoryCrafting(Container par1Container, int par2, int par3)
	{
		int var4 = par2 * par3;
		stackList = new ItemStack[var4];
		eventHandler = par1Container;
		inventoryWidth = par2;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(stackList[par1] != null)
		{
			ItemStack var3;
			if(stackList[par1].stackSize <= par2)
			{
				var3 = stackList[par1];
				stackList[par1] = null;
				eventHandler.onCraftMatrixChanged(this);
				return var3;
			} else
			{
				var3 = stackList[par1].splitStack(par2);
				if(stackList[par1].stackSize == 0)
				{
					stackList[par1] = null;
				}
				eventHandler.onCraftMatrixChanged(this);
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
		return "container.crafting";
	}
	
	@Override public int getSizeInventory()
	{
		return stackList.length;
	}
	
	public ItemStack getStackInRowAndColumn(int par1, int par2)
	{
		if(par1 >= 0 && par1 < inventoryWidth)
		{
			int var3 = par1 + par2 * inventoryWidth;
			return getStackInSlot(var3);
		} else return null;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return par1 >= getSizeInventory() ? null : stackList[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(stackList[par1] != null)
		{
			ItemStack var2 = stackList[par1];
			stackList[par1] = null;
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
		stackList[par1] = par2ItemStack;
		eventHandler.onCraftMatrixChanged(this);
	}
}
