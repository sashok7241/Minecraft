package net.minecraft.src;

class ContainerRepairINNER1 extends InventoryBasic
{
	final ContainerRepair field_135010_a;
	
	ContainerRepairINNER1(ContainerRepair par1ContainerRepair, String par2Str, boolean par3, int par4)
	{
		super(par2Str, par3, par4);
		field_135010_a = par1ContainerRepair;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
		super.onInventoryChanged();
		field_135010_a.onCraftMatrixChanged(this);
	}
}
