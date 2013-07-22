package net.minecraft.src;

class EntityAIAvoidEntitySelector implements IEntitySelector
{
	final EntityAIAvoidEntity entityAvoiderAI;
	
	EntityAIAvoidEntitySelector(EntityAIAvoidEntity p_i11037_1_)
	{
		entityAvoiderAI = p_i11037_1_;
	}
	
	@Override public boolean isEntityApplicable(Entity p_82704_1_)
	{
		return p_82704_1_.isEntityAlive() && EntityAIAvoidEntity.func_98217_a(entityAvoiderAI).getEntitySenses().canSee(p_82704_1_);
	}
}
