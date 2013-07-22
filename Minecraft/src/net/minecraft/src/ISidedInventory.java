package net.minecraft.src;

public interface ISidedInventory extends IInventory
{
	boolean canExtractItem(int var1, ItemStack var2, int var3);
	
	boolean canInsertItem(int var1, ItemStack var2, int var3);
	
	int[] getAccessibleSlotsFromSide(int var1);
}
