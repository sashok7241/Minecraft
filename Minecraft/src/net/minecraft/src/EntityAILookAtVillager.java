package net.minecraft.src;

public class EntityAILookAtVillager extends EntityAIBase
{
	private EntityIronGolem theGolem;
	private EntityVillager theVillager;
	private int lookTime;
	
	public EntityAILookAtVillager(EntityIronGolem p_i3483_1_)
	{
		theGolem = p_i3483_1_;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		return lookTime > 0;
	}
	
	@Override public void resetTask()
	{
		theGolem.setHoldingRose(false);
		theVillager = null;
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theGolem.worldObj.isDaytime()) return false;
		else if(theGolem.getRNG().nextInt(8000) != 0) return false;
		else
		{
			theVillager = (EntityVillager) theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, theGolem.boundingBox.expand(6.0D, 2.0D, 6.0D), theGolem);
			return theVillager != null;
		}
	}
	
	@Override public void startExecuting()
	{
		lookTime = 400;
		theGolem.setHoldingRose(true);
	}
	
	@Override public void updateTask()
	{
		theGolem.getLookHelper().setLookPositionWithEntity(theVillager, 30.0F, 30.0F);
		--lookTime;
	}
}
