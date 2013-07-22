package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
	EntityTameable theEntityTameable;
	EntityLiving theTarget;
	
	public EntityAIOwnerHurtTarget(EntityTameable p_i3504_1_)
	{
		super(p_i3504_1_, 32.0F, false);
		theEntityTameable = p_i3504_1_;
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
