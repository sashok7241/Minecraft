package net.minecraft.src;

final class DispenserBehaviorExperience extends BehaviorProjectileDispense
{
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
		return new EntityExpBottle(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
	}
}
