package net.minecraft.src;

class SlotArmor extends Slot
{
	final int armorType;
	final ContainerPlayer parent;
	
	SlotArmor(ContainerPlayer par1ContainerPlayer, IInventory par2IInventory, int par3, int par4, int par5, int par6)
	{
		super(par2IInventory, par3, par4, par5);
		parent = par1ContainerPlayer;
		armorType = par6;
	}
	
	@Override public Icon getBackgroundIconIndex()
	{
		return ItemArmor.func_94602_b(armorType);
	}
	
	@Override public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? false : par1ItemStack.getItem() instanceof ItemArmor ? ((ItemArmor) par1ItemStack.getItem()).armorType == armorType : par1ItemStack.getItem().itemID != Block.pumpkin.blockID && par1ItemStack.getItem().itemID != Item.skull.itemID ? false : armorType == 0;
	}
}
