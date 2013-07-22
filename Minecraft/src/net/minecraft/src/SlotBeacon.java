package net.minecraft.src;

class SlotBeacon extends Slot
{
	final ContainerBeacon beacon;
	
	public SlotBeacon(ContainerBeacon p_i5075_1_, IInventory p_i5075_2_, int p_i5075_3_, int p_i5075_4_, int p_i5075_5_)
	{
		super(p_i5075_2_, p_i5075_3_, p_i5075_4_, p_i5075_5_);
		beacon = p_i5075_1_;
	}
	
	@Override public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return p_75214_1_ == null ? false : p_75214_1_.itemID == Item.emerald.itemID || p_75214_1_.itemID == Item.diamond.itemID || p_75214_1_.itemID == Item.ingotGold.itemID || p_75214_1_.itemID == Item.ingotIron.itemID;
	}
}
