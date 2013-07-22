package net.minecraft.src;

final class DispenserBehaviorArrow extends BehaviorProjectileDispense
{
	@Override protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
	{
		EntityArrow var3 = new EntityArrow(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
		var3.canBePickedUp = 1;
		return var3;
	}
}
