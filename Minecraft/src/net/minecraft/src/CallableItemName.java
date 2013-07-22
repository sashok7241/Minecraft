package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableItemName implements Callable
{
	final ItemStack theItemStack;
	final InventoryPlayer playerInventory;
	
	CallableItemName(InventoryPlayer par1InventoryPlayer, ItemStack par2ItemStack)
	{
		playerInventory = par1InventoryPlayer;
		theItemStack = par2ItemStack;
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
