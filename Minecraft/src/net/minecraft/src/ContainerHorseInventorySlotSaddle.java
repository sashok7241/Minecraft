package net.minecraft.src;

class ContainerHorseInventorySlotSaddle extends Slot
{
	final ContainerHorseInventory field_111239_a;
	
	ContainerHorseInventorySlotSaddle(ContainerHorseInventory par1ContainerHorseInventory, IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
		field_111239_a = par1ContainerHorseInventory;
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return super.isItemValid(par1ItemStack) && par1ItemStack.itemID == Item.saddle.itemID && !getHasStack();
	}
}
