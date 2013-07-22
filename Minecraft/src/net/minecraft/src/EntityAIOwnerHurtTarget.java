package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
	EntityTameable theEntityTameable;
	EntityLivingBase theTarget;
	private int field_142050_e;
	
	public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
	{
		super(par1EntityTameable, false);
		theEntityTameable = par1EntityTameable;
		setMutexBits(1);
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theEntityTameable.isTamed()) return false;
		else
		{
			EntityLivingBase var1 = theEntityTameable.func_130012_q();
			if(var1 == null) return false;
			else
			{
				theTarget = var1.func_110144_aD();
				int var2 = var1.func_142013_aG();
				return var2 != field_142050_e && isSuitableTarget(theTarget, false) && theEntityTameable.func_142018_a(theTarget, var1);
			}
		}
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(theTarget);
		EntityLivingBase var1 = theEntityTameable.func_130012_q();
		if(var1 != null)
		{
			field_142050_e = var1.func_142013_aG();
		}
		super.startExecuting();
	}
}
