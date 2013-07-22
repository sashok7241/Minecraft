package net.minecraft.src;

public class ContainerHopper extends Container
{
	private final IInventory field_94538_a;
	
	public ContainerHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory)
	{
		field_94538_a = par2IInventory;
		par2IInventory.openChest();
		byte var3 = 51;
		int var4;
		for(var4 = 0; var4 < par2IInventory.getSizeInventory(); ++var4)
		{
			addSlotToContainer(new Slot(par2IInventory, var4, 44 + var4 * 18, 20));
		}
		for(var4 = 0; var4 < 3; ++var4)
		{
			for(int var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + var3));
			}
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 58 + var3));
		}
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return field_94538_a.isUseableByPlayer(par1EntityPlayer);
	}
	
	@Override public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		field_94538_a.closeChest();
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(par2 < field_94538_a.getSizeInventory())
			{
				if(!mergeItemStack(var5, field_94538_a.getSizeInventory(), inventorySlots.size(), true)) return null;
			} else if(!mergeItemStack(var5, 0, field_94538_a.getSizeInventory(), false)) return null;
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
