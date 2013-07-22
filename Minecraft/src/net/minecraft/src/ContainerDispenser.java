package net.minecraft.src;

public class ContainerDispenser extends Container
{
	private TileEntityDispenser tileEntityDispenser;
	
	public ContainerDispenser(IInventory p_i3617_1_, TileEntityDispenser p_i3617_2_)
	{
		tileEntityDispenser = p_i3617_2_;
		int var3;
		int var4;
		for(var3 = 0; var3 < 3; ++var3)
		{
			for(var4 = 0; var4 < 3; ++var4)
			{
				addSlotToContainer(new Slot(p_i3617_2_, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
			}
		}
		for(var3 = 0; var3 < 3; ++var3)
		{
			for(var4 = 0; var4 < 9; ++var4)
			{
				addSlotToContainer(new Slot(p_i3617_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		for(var3 = 0; var3 < 9; ++var3)
		{
			addSlotToContainer(new Slot(p_i3617_1_, var3, 8 + var3 * 18, 142));
		}
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return tileEntityDispenser.isUseableByPlayer(p_75145_1_);
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(p_82846_2_);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(p_82846_2_ < 9)
			{
				if(!mergeItemStack(var5, 9, 45, true)) return null;
			} else if(!mergeItemStack(var5, 0, 9, false)) return null;
			if(var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			} else
			{
				var4.onSlotChanged();
			}
			if(var5.stackSize == var3.stackSize) return null;
			var4.onPickupFromSlot(p_82846_1_, var5);
		}
		return var3;
	}
}
