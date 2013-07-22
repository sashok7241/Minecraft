package net.minecraft.src;

class SlotEnchantmentTable extends InventoryBasic
{
	final ContainerEnchantment container;
	
	SlotEnchantmentTable(ContainerEnchantment p_i9031_1_, String p_i9031_2_, boolean p_i9031_3_, int p_i9031_4_)
	{
		super(p_i9031_2_, p_i9031_3_, p_i9031_4_);
		container = p_i9031_1_;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
		super.onInventoryChanged();
		container.onCraftMatrixChanged(this);
	}
}
