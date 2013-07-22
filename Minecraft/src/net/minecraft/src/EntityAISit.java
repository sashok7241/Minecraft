package net.minecraft.src;

public class EntityAISit extends EntityAIBase
{
	private EntityTameable theEntity;
	private boolean isSitting = false;
	
	public EntityAISit(EntityTameable par1EntityTameable)
	{
		theEntity = par1EntityTameable;
		setMutexBits(5);
	}
	
	@Override public void resetTask()
	{
		theEntity.setSitting(false);
	}
	
	public void setSitting(boolean par1)
	{
		isSitting = par1;
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theEntity.isTamed()) return false;
		else if(theEntity.isInWater()) return false;
		else if(!theEntity.onGround) return false;
		else
		{
			EntityLiving var1 = theEntity.getOwner();
			return var1 == null ? true : theEntity.getDistanceSqToEntity(var1) < 144.0D && var1.getAITarget() != null ? false : isSitting;
		}
	}
	
	@Override public void startExecuting()
	{
		theEntity.getNavigator().clearPathEntity();
		theEntity.setSitting(true);
	}
}
