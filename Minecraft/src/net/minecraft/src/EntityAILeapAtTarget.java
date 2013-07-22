package net.minecraft.src;

public class EntityAILeapAtTarget extends EntityAIBase
{
	EntityLiving leaper;
	EntityLiving leapTarget;
	float leapMotionY;
	
	public EntityAILeapAtTarget(EntityLiving p_i3471_1_, float p_i3471_2_)
	{
		leaper = p_i3471_1_;
		leapMotionY = p_i3471_2_;
		setMutexBits(5);
	}
	
	@Override public boolean continueExecuting()
	{
		return !leaper.onGround;
	}
	
	@Override public boolean shouldExecute()
	{
		leapTarget = leaper.getAttackTarget();
		if(leapTarget == null) return false;
		else
		{
			double var1 = leaper.getDistanceSqToEntity(leapTarget);
			return var1 >= 4.0D && var1 <= 16.0D ? !leaper.onGround ? false : leaper.getRNG().nextInt(5) == 0 : false;
		}
	}
	
	@Override public void startExecuting()
	{
		double var1 = leapTarget.posX - leaper.posX;
		double var3 = leapTarget.posZ - leaper.posZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		leaper.motionX += var1 / var5 * 0.5D * 0.800000011920929D + leaper.motionX * 0.20000000298023224D;
		leaper.motionZ += var3 / var5 * 0.5D * 0.800000011920929D + leaper.motionZ * 0.20000000298023224D;
		leaper.motionY = leapMotionY;
	}
}
