package net.minecraft.src;

class SlotBrewingStandIngredient extends Slot
{
	final ContainerBrewingStand brewingStand;
	
	public SlotBrewingStandIngredient(ContainerBrewingStand p_i3598_1_, IInventory p_i3598_2_, int p_i3598_3_, int p_i3598_4_, int p_i3598_5_)
	{
		super(p_i3598_2_, p_i3598_3_, p_i3598_4_, p_i3598_5_);
		brewingStand = p_i3598_1_;
	}
	
	@Override public int getSlotStackLimit()
	{
		return 64;
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return p_75214_1_ != null ? Item.itemsList[p_75214_1_.itemID].isPotionIngredient() : false;
	}
}
