package net.minecraft.src;

class SlotEnchantment extends Slot
{
	final ContainerEnchantment container;
	
	SlotEnchantment(ContainerEnchantment p_i3605_1_, IInventory p_i3605_2_, int p_i3605_3_, int p_i3605_4_, int p_i3605_5_)
	{
		super(p_i3605_2_, p_i3605_3_, p_i3605_4_, p_i3605_5_);
		container = p_i3605_1_;
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return true;
	}
}
