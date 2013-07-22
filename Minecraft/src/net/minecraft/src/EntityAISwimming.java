package net.minecraft.src;

public class EntityAISwimming extends EntityAIBase
{
	private EntityLiving theEntity;
	
	public EntityAISwimming(EntityLiving par1EntityLiving)
	{
		theEntity = par1EntityLiving;
		setMutexBits(4);
		par1EntityLiving.getNavigator().setCanSwim(true);
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
