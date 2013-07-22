package net.minecraft.src;

class SlotBrewingStandPotion extends Slot
{
	private EntityPlayer player;
	
	public SlotBrewingStandPotion(EntityPlayer p_i3599_1_, IInventory p_i3599_2_, int p_i3599_3_, int p_i3599_4_, int p_i3599_5_)
	{
		super(p_i3599_2_, p_i3599_3_, p_i3599_4_, p_i3599_5_);
		player = p_i3599_1_;
	}
	
	@Override public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return canHoldPotion(p_75214_1_);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		if(p_82870_2_.itemID == Item.potion.itemID && p_82870_2_.getItemDamage() > 0)
		{
			player.addStat(AchievementList.potion, 1);
		}
		super.onPickupFromSlot(p_82870_1_, p_82870_2_);
	}
	
	public static boolean canHoldPotion(ItemStack p_75243_0_)
	{
		return p_75243_0_ != null && (p_75243_0_.itemID == Item.potion.itemID || p_75243_0_.itemID == Item.glassBottle.itemID);
	}
}
