package net.minecraft.src;

public class ContainerChest extends Container
{
	private IInventory lowerChestInventory;
	private int numRows;
	
	public ContainerChest(IInventory p_i3601_1_, IInventory p_i3601_2_)
	{
		lowerChestInventory = p_i3601_2_;
		numRows = p_i3601_2_.getSizeInventory() / 9;
		p_i3601_2_.openChest();
		int var3 = (numRows - 4) * 18;
		int var4;
		int var5;
		for(var4 = 0; var4 < numRows; ++var4)
		{
			for(var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(p_i3601_2_, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
			}
		}
		for(var4 = 0; var4 < 3; ++var4)
		{
			for(var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(p_i3601_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
			}
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			addSlotToContainer(new Slot(p_i3601_1_, var4, 8 + var4 * 18, 161 + var3));
		}
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return lowerChestInventory.isUseableByPlayer(p_75145_1_);
	}
	
	public IInventory getLowerChestInventory()
	{
		return lowerChestInventory;
	}
	
	@Override public void onContainerClosed(EntityPlayer p_75134_1_)
	{
		super.onContainerClosed(p_75134_1_);
		lowerChestInventory.closeChest();
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(p_82846_2_);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(p_82846_2_ < numRows * 9)
			{
				if(!mergeItemStack(var5, numRows * 9, inventorySlots.size(), true)) return null;
			} else if(!mergeItemStack(var5, 0, numRows * 9, false)) return null;
			if(var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			} else
			{
				var4.onSlotChanged();
			}
		}
		return var3;
	}
}
