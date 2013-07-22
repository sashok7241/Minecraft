package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
	EntityTameable theDefendingTameable;
	EntityLiving theOwnerAttacker;
	
	public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable)
	{
		super(par1EntityTameable, 32.0F, false);
		theDefendingTameable = par1EntityTameable;
		setMutexBits(1);
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theDefendingTameable.isTamed()) return false;
		else
		{
			EntityLiving var1 = theDefendingTameable.getOwner();
			if(var1 == null) return false;
			else
			{
				theOwnerAttacker = var1.getAITarget();
				return isSuitableTarget(theOwnerAttacker, false);
			}
		}
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(theOwnerAttacker);
		super.startExecuting();
	}
}
