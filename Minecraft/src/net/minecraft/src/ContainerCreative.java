package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class ContainerCreative extends Container
{
	public List itemList = new ArrayList();
	
	public ContainerCreative(EntityPlayer par1EntityPlayer)
	{
		InventoryPlayer var2 = par1EntityPlayer.inventory;
		int var3;
		for(var3 = 0; var3 < 5; ++var3)
		{
			for(int var4 = 0; var4 < 9; ++var4)
			{
				addSlotToContainer(new Slot(GuiContainerCreative.getInventory(), var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
			}
		}
		for(var3 = 0; var3 < 9; ++var3)
		{
			addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
		}
		scrollTo(0.0F);
	}
	
	@Override public boolean canDragIntoSlot(Slot par1Slot)
	{
		return par1Slot.inventory instanceof InventoryPlayer || par1Slot.yDisplayPosition > 90 && par1Slot.xDisplayPosition <= 162;
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return true;
	}
	
	@Override public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
	{
		return par2Slot.yDisplayPosition > 90;
	}
	
	public boolean hasMoreThan1PageOfItemsInList()
	{
		return itemList.size() > 45;
	}
	
	@Override protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
	{
	}
	
	public void scrollTo(float par1)
	{
		int var2 = itemList.size() / 9 - 5 + 1;
		int var3 = (int) (par1 * var2 + 0.5D);
		if(var3 < 0)
		{
			var3 = 0;
		}
		for(int var4 = 0; var4 < 5; ++var4)
		{
			for(int var5 = 0; var5 < 9; ++var5)
			{
				int var6 = var5 + (var4 + var3) * 9;
				if(var6 >= 0 && var6 < itemList.size())
				{
					GuiContainerCreative.getInventory().setInventorySlotContents(var5 + var4 * 9, (ItemStack) itemList.get(var6));
				} else
				{
					GuiContainerCreative.getInventory().setInventorySlotContents(var5 + var4 * 9, (ItemStack) null);
				}
			}
		}
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		if(par2 >= inventorySlots.size() - 9 && par2 < inventorySlots.size())
		{
			Slot var3 = (Slot) inventorySlots.get(par2);
			if(var3 != null && var3.getHasStack())
			{
				var3.putStack((ItemStack) null);
			}
		}
		return null;
	}
}
