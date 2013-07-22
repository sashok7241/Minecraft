package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableItemName implements Callable
{
	final ItemStack theItemStack;
	final InventoryPlayer playerInventory;
	
	CallableItemName(InventoryPlayer p_i10055_1_, ItemStack p_i10055_2_)
	{
		playerInventory = p_i10055_1_;
		theItemStack = p_i10055_2_;
	}
	
	@Override public Object call()
	{
		return callItemDisplayName();
	}
	
	public String callItemDisplayName()
	{
		return theItemStack.getDisplayName();
	}
}
