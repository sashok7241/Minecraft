package net.minecraft.src;

final class DispenserBehaviorPotion implements IBehaviorDispenseItem
{
	private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
	
	@Override public ItemStack dispense(IBlockSource p_82482_1_, ItemStack p_82482_2_)
	{
		return ItemPotion.isSplash(p_82482_2_.getItemDamage()) ? new DispenserBehaviorPotionProjectile(this, p_82482_2_).dispense(p_82482_1_, p_82482_2_) : defaultDispenserItemBehavior.dispense(p_82482_1_, p_82482_2_);
	}
}
