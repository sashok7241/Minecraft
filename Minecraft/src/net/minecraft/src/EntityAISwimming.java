package net.minecraft.src;

public class EntityAISwimming extends EntityAIBase
{
	private EntityLiving theEntity;
	
	public EntityAISwimming(EntityLiving p_i3465_1_)
	{
		theEntity = p_i3465_1_;
		setMutexBits(4);
		p_i3465_1_.getNavigator().setCanSwim(true);
	}
	
	@Override public boolean shouldExecute()
	{
		return theEntity.isInWater() || theEntity.handleLavaMovement();
	}
	
	@Override public void updateTask()
	{
		if(theEntity.getRNG().nextFloat() < 0.8F)
		{
			theEntity.getJumpHelper().setJumping();
		}
	}
}
