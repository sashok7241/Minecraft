package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
	EntityTameable theEntityTameable;
	EntityLiving theTarget;
	
	public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
	{
		super(par1EntityTameable, 32.0F, false);
		theEntityTameable = par1EntityTameable;
		setMutexBits(1);
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theEntityTameable.isTamed()) return false;
		else
		{
			EntityLiving var1 = theEntityTameable.getOwner();
			if(var1 == null) return false;
			else
			{
				theTarget = var1.getLastAttackingEntity();
				return isSuitableTarget(theTarget, false);
			}
		}
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(theTarget);
		super.startExecuting();
	}
}
