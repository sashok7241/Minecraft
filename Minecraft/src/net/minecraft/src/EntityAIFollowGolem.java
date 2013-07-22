package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowGolem extends EntityAIBase
{
	private EntityVillager theVillager;
	private EntityIronGolem theGolem;
	private int takeGolemRoseTick;
	private boolean tookGolemRose = false;
	
	public EntityAIFollowGolem(EntityVillager par1EntityVillager)
	{
		theVillager = par1EntityVillager;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		return theGolem.getHoldRoseTick() > 0;
	}
	
	@Override public void resetTask()
	{
		theGolem = null;
		theVillager.getNavigator().clearPathEntity();
	}
	
	@Override public boolean shouldExecute()
	{
		if(theVillager.getGrowingAge() >= 0) return false;
		else if(!theVillager.worldObj.isDaytime()) return false;
		else
		{
			List var1 = theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, theVillager.boundingBox.expand(6.0D, 2.0D, 6.0D));
			if(var1.isEmpty()) return false;
			else
			{
				Iterator var2 = var1.iterator();
				while(var2.hasNext())
				{
					EntityIronGolem var3 = (EntityIronGolem) var2.next();
					if(var3.getHoldRoseTick() > 0)
					{
						theGolem = var3;
						break;
					}
				}
				return theGolem != null;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		takeGolemRoseTick = theVillager.getRNG().nextInt(320);
		tookGolemRose = false;
		theGolem.getNavigator().clearPathEntity();
	}
	
	@Override public void updateTask()
	{
		theVillager.getLookHelper().setLookPositionWithEntity(theGolem, 30.0F, 30.0F);
		if(theGolem.getHoldRoseTick() == takeGolemRoseTick)
		{
			theVillager.getNavigator().tryMoveToEntityLiving(theGolem, 0.15F);
			tookGolemRose = true;
		}
		if(tookGolemRose && theVillager.getDistanceSqToEntity(theGolem) < 4.0D)
		{
			theGolem.setHoldingRose(false);
			theVillager.getNavigator().clearPathEntity();
		}
	}
}
