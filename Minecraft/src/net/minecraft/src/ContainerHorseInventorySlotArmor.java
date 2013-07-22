package net.minecraft.src;

class ContainerHorseInventorySlotArmor extends Slot
{
	final EntityHorse field_111241_a;
	final ContainerHorseInventory field_111240_b;
	
	ContainerHorseInventorySlotArmor(ContainerHorseInventory par1ContainerHorseInventory, IInventory par2IInventory, int par3, int par4, int par5, EntityHorse par6EntityHorse)
	{
		super(par2IInventory, par3, par4, par5);
		field_111240_b = par1ContainerHorseInventory;
		field_111241_a = par6EntityHorse;
	}
	
	@Override public boolean func_111238_b()
	{
		return field_111241_a.func_110259_cr();
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return super.isItemValid(par1ItemStack) && field_111241_a.func_110259_cr() && EntityHorse.func_110211_v(par1ItemStack.itemID);
	}
}
