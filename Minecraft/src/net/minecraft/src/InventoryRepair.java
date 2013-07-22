package net.minecraft.src;

class InventoryRepair extends InventoryBasic
{
	final ContainerRepair theContainer;
	
	InventoryRepair(ContainerRepair p_i9033_1_, String p_i9033_2_, boolean p_i9033_3_, int p_i9033_4_)
	{
		super(p_i9033_2_, p_i9033_3_, p_i9033_4_);
		theContainer = p_i9033_1_;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
		super.onInventoryChanged();
		theContainer.onCraftMatrixChanged(this);
	}
}
