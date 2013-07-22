package net.minecraft.src;

class SlotEnchantmentTable extends InventoryBasic
{
	final ContainerEnchantment container;
	
	SlotEnchantmentTable(ContainerEnchantment par1ContainerEnchantment, String par2Str, boolean par3, int par4)
	{
		super(par2Str, par3, par4);
		container = par1ContainerEnchantment;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
		super.onInventoryChanged();
		container.onCraftMatrixChanged(this);
	}
}
