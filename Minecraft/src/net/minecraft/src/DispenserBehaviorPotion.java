package net.minecraft.src;

final class DispenserBehaviorPotion implements IBehaviorDispenseItem
{
	private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
	
	@Override public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
	{
		return ItemPotion.isSplash(par2ItemStack.getItemDamage()) ? new DispenserBehaviorPotionProjectile(this, par2ItemStack).dispense(par1IBlockSource, par2ItemStack) : defaultDispenserItemBehavior.dispense(par1IBlockSource, par2ItemStack);
	}
}
