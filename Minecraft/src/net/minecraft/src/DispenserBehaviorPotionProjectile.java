package net.minecraft.src;

class DispenserBehaviorPotionProjectile extends BehaviorProjectileDispense
{
	final ItemStack potionItemStack;
	final DispenserBehaviorPotion dispenserPotionBehavior;
	
	DispenserBehaviorPotionProjectile(DispenserBehaviorPotion p_i10042_1_, ItemStack p_i10042_2_)
	{
		dispenserPotionBehavior = p_i10042_1_;
		potionItemStack = p_i10042_2_;
	}
	
	@Override protected float func_82498_a()
	{
		return super.func_82498_a() * 0.5F;
	}
	
	@Override protected float func_82500_b()
	{
		return super.func_82500_b() * 1.25F;
	}
	
	@Override protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
	{
		return new EntityPotion(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ(), potionItemStack.copy());
	}
}
