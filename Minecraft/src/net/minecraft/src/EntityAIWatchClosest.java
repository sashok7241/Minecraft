package net.minecraft.src;

public class EntityAIWatchClosest extends EntityAIBase
{
	private EntityLiving theWatcher;
	protected Entity closestEntity;
	private float field_75333_c;
	private int lookTime;
	private float field_75331_e;
	private Class watchedClass;
	
	public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3)
	{
		theWatcher = par1EntityLiving;
		watchedClass = par2Class;
		field_75333_c = par3;
		field_75331_e = 0.02F;
		setMutexBits(2);
	}
	
	public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3, float par4)
	{
		theWatcher = par1EntityLiving;
		watchedClass = par2Class;
		field_75333_c = par3;
		field_75331_e = par4;
		setMutexBits(2);
	}
	
	@Override public boolean continueExecuting()
	{
		return !closestEntity.isEntityAlive() ? false : theWatcher.getDistanceSqToEntity(closestEntity) > field_75333_c * field_75333_c ? false : lookTime > 0;
	}
	
	@Override public void resetTask()
	{
		closestEntity = null;
	}
	
	@Override public boolean shouldExecute()
	{
		if(theWatcher.getRNG().nextFloat() >= field_75331_e) return false;
		else
		{
			if(theWatcher.getAttackTarget() != null)
			{
				closestEntity = theWatcher.getAttackTarget();
			}
			if(watchedClass == EntityPlayer.class)
			{
				closestEntity = theWatcher.worldObj.getClosestPlayerToEntity(theWatcher, field_75333_c);
			} else
			{
				closestEntity = theWatcher.worldObj.findNearestEntityWithinAABB(watchedClass, theWatcher.boundingBox.expand(field_75333_c, 3.0D, field_75333_c), theWatcher);
			}
			return closestEntity != null;
		}
	}
	
	@Override public void startExecuting()
	{
		lookTime = 40 + theWatcher.getRNG().nextInt(40);
	}
	
	@Override public void updateTask()
	{
		theWatcher.getLookHelper().setLookPosition(closestEntity.posX, closestEntity.posY + closestEntity.getEyeHeight(), closestEntity.posZ, 10.0F, theWatcher.getVerticalFaceSpeed());
		--lookTime;
	}
}
