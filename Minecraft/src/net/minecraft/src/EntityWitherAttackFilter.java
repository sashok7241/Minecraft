package net.minecraft.src;

final class EntityWitherAttackFilter implements IEntitySelector
{
	@Override public boolean isEntityApplicable(Entity p_82704_1_)
	{
		return p_82704_1_ instanceof EntityLiving && ((EntityLiving) p_82704_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
	}
}
