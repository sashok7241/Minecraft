package net.minecraft.src;

public class EntityAILookIdle extends EntityAIBase
{
	private EntityLiving idleEntity;
	private double lookX;
	private double lookZ;
	private int idleTime = 0;
	
	public EntityAILookIdle(EntityLiving p_i3488_1_)
	{
		idleEntity = p_i3488_1_;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		return idleTime >= 0;
	}
	
	@Override public boolean shouldExecute()
	{
		return idleEntity.getRNG().nextFloat() < 0.02F;
	}
	
	@Override public void startExecuting()
	{
		double var1 = Math.PI * 2D * idleEntity.getRNG().nextDouble();
		lookX = Math.cos(var1);
		lookZ = Math.sin(var1);
		idleTime = 20 + idleEntity.getRNG().nextInt(20);
	}
	
	@Override public void updateTask()
	{
		--idleTime;
		idleEntity.getLookHelper().setLookPosition(idleEntity.posX + lookX, idleEntity.posY + idleEntity.getEyeHeight(), idleEntity.posZ + lookZ, 10.0F, idleEntity.getVerticalFaceSpeed());
	}
}
