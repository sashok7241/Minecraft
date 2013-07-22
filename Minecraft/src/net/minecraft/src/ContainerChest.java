package net.minecraft.src;

public class ContainerChest extends Container
{
	private IInventory lowerChestInventory;
	private int numRows;
	
	public ContainerChest(IInventory par1IInventory, IInventory par2IInventory)
	{
		lowerChestInventory = par2IInventory;
		numRows = par2IInventory.getSizeInventory() / 9;
		par2IInventory.openChest();
		int var3 = (numRows - 4) * 18;
		int var4;
		int var5;
		for(var4 = 0; var4 < numRows; ++var4)
		{
			for(var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(par2IInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
			}
		}
		for(var4 = 0; var4 < 3; ++var4)
		{
			for(var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(par1IInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
			}
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			addSlotToContainer(new Slot(par1IInventory, var4, 8 + var4 * 18, 161 + var3));
		}
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
	}
	
	public IInventory getLowerChestInventory()
	{
		return lowerChestInventory;
	}
	
	@Override public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		lowerChestInventory.closeChest();
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(par2 < numRows * 9)
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
