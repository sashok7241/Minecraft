package net.minecraft.src;

final class DispenserBehaviorEgg extends BehaviorProjectileDispense
{
	@Override protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
	{
		return new EntityEgg(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
	}
}
