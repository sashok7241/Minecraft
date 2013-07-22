package net.minecraft.src;

class SlotBeacon extends Slot
{
	final ContainerBeacon beacon;
	
	public SlotBeacon(ContainerBeacon par1ContainerBeacon, IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
		beacon = par1ContainerBeacon;
	}
	
	@Override public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? false : par1ItemStack.itemID == Item.emerald.itemID || par1ItemStack.itemID == Item.diamond.itemID || par1ItemStack.itemID == Item.ingotGold.itemID || par1ItemStack.itemID == Item.ingotIron.itemID;
	}
}
