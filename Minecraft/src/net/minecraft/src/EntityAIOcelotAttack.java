package net.minecraft.src;

public class EntityAIOcelotAttack extends EntityAIBase
{
	World theWorld;
	EntityLiving theEntity;
	EntityLivingBase theVictim;
	int attackCountdown;
	
	public EntityAIOcelotAttack(EntityLiving par1EntityLiving)
	{
		theEntity = par1EntityLiving;
		theWorld = par1EntityLiving.worldObj;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		return !theVictim.isEntityAlive() ? false : theEntity.getDistanceSqToEntity(theVictim) > 225.0D ? false : !theEntity.getNavigator().noPath() || shouldExecute();
	}
	
	@Override public void resetTask()
	{
		theVictim = null;
		theEntity.getNavigator().clearPathEntity();
	}
	
	@Override public boolean shouldExecute()
	{
		EntityLivingBase var1 = theEntity.getAttackTarget();
		if(var1 == null) return false;
		else
		{
			theVictim = var1;
			return true;
		}
	}
	
	@Override public void updateTask()
	{
		theEntity.getLookHelper().setLookPositionWithEntity(theVictim, 30.0F, 30.0F);
		double var1 = theEntity.width * 2.0F * theEntity.width * 2.0F;
		double var3 = theEntity.getDistanceSq(theVictim.posX, theVictim.boundingBox.minY, theVictim.posZ);
		double var5 = 0.8D;
		if(var3 > var1 && var3 < 16.0D)
		{
			var5 = 1.33D;
		} else if(var3 < 225.0D)
		{
			var5 = 0.6D;
		}
		theEntity.getNavigator().tryMoveToEntityLiving(theVictim, var5);
		attackCountdown = Math.max(attackCountdown - 1, 0);
		if(var3 <= var1)
		{
			if(attackCountdown <= 0)
			{
				attackCountdown = 20;
				theEntity.attackEntityAsMob(theVictim);
			}
		}
	}
}
