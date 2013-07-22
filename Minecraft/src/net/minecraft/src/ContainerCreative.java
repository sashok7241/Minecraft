package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class ContainerCreative extends Container
{
	public List itemList = new ArrayList();
	
	public ContainerCreative(EntityPlayer p_i3082_1_)
	{
		InventoryPlayer var2 = p_i3082_1_.inventory;
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
	
	@Override public boolean canDragIntoSlot(Slot p_94531_1_)
	{
		return p_94531_1_.inventory instanceof InventoryPlayer || p_94531_1_.yDisplayPosition > 90 && p_94531_1_.xDisplayPosition <= 162;
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return true;
	}
	
	@Override public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
	{
		return p_94530_2_.yDisplayPosition > 90;
	}
	
	public boolean hasMoreThan1PageOfItemsInList()
	{
		return itemList.size() > 45;
	}
	
	@Override protected void retrySlotClick(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_)
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
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		if(p_82846_2_ >= inventorySlots.size() - 9 && p_82846_2_ < inventorySlots.size())
		{
			Slot var3 = (Slot) inventorySlots.get(p_82846_2_);
			if(var3 != null && var3.getHasStack())
			{
				var3.putStack((ItemStack) null);
			}
		}
		return null;
	}
}
