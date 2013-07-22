package net.minecraft.src;

class SlotArmor extends Slot
{
	final int armorType;
	final ContainerPlayer parent;
	
	SlotArmor(ContainerPlayer p_i3609_1_, IInventory p_i3609_2_, int p_i3609_3_, int p_i3609_4_, int p_i3609_5_, int p_i3609_6_)
	{
		super(p_i3609_2_, p_i3609_3_, p_i3609_4_, p_i3609_5_);
		parent = p_i3609_1_;
		armorType = p_i3609_6_;
	}
	
	@Override public Icon getBackgroundIconIndex()
	{
		return ItemArmor.func_94602_b(armorType);
	}
	
	@Override public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return p_75214_1_ == null ? false : p_75214_1_.getItem() instanceof ItemArmor ? ((ItemArmor) p_75214_1_.getItem()).armorType == armorType : p_75214_1_.getItem().itemID != Block.pumpkin.blockID && p_75214_1_.getItem().itemID != Item.skull.itemID ? false : armorType == 0;
	}
}
