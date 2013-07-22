package net.minecraft.src;

public class EntityAIRestrictSun extends EntityAIBase
{
	private EntityCreature theEntity;
	
	public EntityAIRestrictSun(EntityCreature p_i3491_1_)
	{
		theEntity = p_i3491_1_;
	}
	
	@Override public void resetTask()
	{
		theEntity.getNavigator().setAvoidSun(false);
	}
	
	@Override public boolean shouldExecute()
	{
		return theEntity.worldObj.isDaytime();
	}
	
	@Override public void startExecuting()
	{
		theEntity.getNavigator().setAvoidSun(true);
	}
}
