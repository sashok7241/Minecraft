package net.minecraft.src;

final class EntitySelectorInventory implements IEntitySelector
{
	@Override public boolean isEntityApplicable(Entity p_82704_1_)
	{
		return p_82704_1_ instanceof IInventory && p_82704_1_.isEntityAlive();
	}
}
