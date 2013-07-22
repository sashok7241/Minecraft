package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
	EntityTameable theDefendingTameable;
	EntityLivingBase theOwnerAttacker;
	private int field_142051_e;
	
	public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable)
	{
		super(par1EntityTameable, false);
		theDefendingTameable = par1EntityTameable;
		setMutexBits(1);
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theDefendingTameable.isTamed()) return false;
		else
		{
			EntityLivingBase var1 = theDefendingTameable.func_130012_q();
			if(var1 == null) return false;
			else
			{
				theOwnerAttacker = var1.getAITarget();
				int var2 = var1.func_142015_aE();
				return var2 != field_142051_e && isSuitableTarget(theOwnerAttacker, false) && theDefendingTameable.func_142018_a(theOwnerAttacker, var1);
			}
		}
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(theOwnerAttacker);
		EntityLivingBase var1 = theDefendingTameable.func_130012_q();
		if(var1 != null)
		{
			field_142051_e = var1.func_142015_aE();
		}
		super.startExecuting();
	}
}
