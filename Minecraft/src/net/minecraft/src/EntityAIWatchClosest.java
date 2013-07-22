package net.minecraft.src;

public class EntityAIWatchClosest extends EntityAIBase
{
	private EntityLiving theWatcher;
	protected Entity closestEntity;
	private float field_75333_c;
	private int lookTime;
	private float field_75331_e;
	private Class watchedClass;
	
	public EntityAIWatchClosest(EntityLiving p_i3472_1_, Class p_i3472_2_, float p_i3472_3_)
	{
		theWatcher = p_i3472_1_;
		watchedClass = p_i3472_2_;
		field_75333_c = p_i3472_3_;
		field_75331_e = 0.02F;
		setMutexBits(2);
	}
	
	public EntityAIWatchClosest(EntityLiving p_i3473_1_, Class p_i3473_2_, float p_i3473_3_, float p_i3473_4_)
	{
		theWatcher = p_i3473_1_;
		watchedClass = p_i3473_2_;
		field_75333_c = p_i3473_3_;
		field_75331_e = p_i3473_4_;
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
