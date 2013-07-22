package net.minecraft.src;

public class InventoryCrafting implements IInventory
{
	private ItemStack[] stackList;
	private int inventoryWidth;
	private Container eventHandler;
	
	public InventoryCrafting(Container p_i3602_1_, int p_i3602_2_, int p_i3602_3_)
	{
		int var4 = p_i3602_2_ * p_i3602_3_;
		stackList = new ItemStack[var4];
		eventHandler = p_i3602_1_;
		inventoryWidth = p_i3602_2_;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(stackList[p_70298_1_] != null)
		{
			ItemStack var3;
			if(stackList[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = stackList[p_70298_1_];
				stackList[p_70298_1_] = null;
				eventHandler.onCraftMatrixChanged(this);
				return var3;
			} else
			{
				var3 = stackList[p_70298_1_].splitStack(p_70298_2_);
				if(stackList[p_70298_1_].stackSize == 0)
				{
					stackList[p_70298_1_] = null;
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
	
	public ItemStack getStackInRowAndColumn(int p_70463_1_, int p_70463_2_)
	{
		if(p_70463_1_ >= 0 && p_70463_1_ < inventoryWidth)
		{
			int var3 = p_70463_1_ + p_70463_2_ * inventoryWidth;
			return getStackInSlot(var3);
		} else return null;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return p_70301_1_ >= getSizeInventory() ? null : stackList[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(stackList[p_70304_1_] != null)
		{
			ItemStack var2 = stackList[p_70304_1_];
			stackList[p_70304_1_] = null;
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
		stackList[p_70299_1_] = p_70299_2_;
		eventHandler.onCraftMatrixChanged(this);
	}
}
