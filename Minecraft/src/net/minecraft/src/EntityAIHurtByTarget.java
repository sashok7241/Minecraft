package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIHurtByTarget extends EntityAITarget
{
	boolean entityCallsForHelp;
	EntityLiving entityPathNavigate;
	
	public EntityAIHurtByTarget(EntityLiving p_i3498_1_, boolean p_i3498_2_)
	{
		super(p_i3498_1_, 16.0F, false);
		entityCallsForHelp = p_i3498_2_;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return taskOwner.getAITarget() != null && taskOwner.getAITarget() != entityPathNavigate;
	}
	
	@Override public void resetTask()
	{
		if(taskOwner.getAttackTarget() != null && taskOwner.getAttackTarget() instanceof EntityPlayer && ((EntityPlayer) taskOwner.getAttackTarget()).capabilities.disableDamage)
		{
			super.resetTask();
		}
	}
	
	@Override public boolean shouldExecute()
	{
		return isSuitableTarget(taskOwner.getAITarget(), true);
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(taskOwner.getAITarget());
		entityPathNavigate = taskOwner.getAITarget();
		if(entityCallsForHelp)
		{
			List var1 = taskOwner.worldObj.getEntitiesWithinAABB(taskOwner.getClass(), AxisAlignedBB.getAABBPool().getAABB(taskOwner.posX, taskOwner.posY, taskOwner.posZ, taskOwner.posX + 1.0D, taskOwner.posY + 1.0D, taskOwner.posZ + 1.0D).expand(targetDistance, 10.0D, targetDistance));
			Iterator var2 = var1.iterator();
			while(var2.hasNext())
			{
				EntityLiving var3 = (EntityLiving) var2.next();
				if(taskOwner != var3 && var3.getAttackTarget() == null)
				{
					var3.setAttackTarget(taskOwner.getAITarget());
				}
			}
		}
		super.startExecuting();
	}
}
