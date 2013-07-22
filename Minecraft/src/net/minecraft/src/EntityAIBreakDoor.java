package net.minecraft.src;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
	private int breakingTime;
	private int field_75358_j = -1;
	
	public EntityAIBreakDoor(EntityLiving p_i3460_1_)
	{
		super(p_i3460_1_);
	}
	
	@Override public boolean continueExecuting()
	{
		double var1 = theEntity.getDistanceSq(entityPosX, entityPosY, entityPosZ);
		return breakingTime <= 240 && !targetDoor.isDoorOpen(theEntity.worldObj, entityPosX, entityPosY, entityPosZ) && var1 < 4.0D;
	}
	
	@Override public void resetTask()
	{
		super.resetTask();
		theEntity.worldObj.destroyBlockInWorldPartially(theEntity.entityId, entityPosX, entityPosY, entityPosZ, -1);
	}
	
	@Override public boolean shouldExecute()
	{
		return !super.shouldExecute() ? false : !theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") ? false : !targetDoor.isDoorOpen(theEntity.worldObj, entityPosX, entityPosY, entityPosZ);
	}
	
	@Override public void startExecuting()
	{
		super.startExecuting();
		breakingTime = 0;
	}
	
	@Override public void updateTask()
	{
		super.updateTask();
		if(theEntity.getRNG().nextInt(20) == 0)
		{
			theEntity.worldObj.playAuxSFX(1010, entityPosX, entityPosY, entityPosZ, 0);
		}
		++breakingTime;
		int var1 = (int) (breakingTime / 240.0F * 10.0F);
		if(var1 != field_75358_j)
		{
			theEntity.worldObj.destroyBlockInWorldPartially(theEntity.entityId, entityPosX, entityPosY, entityPosZ, var1);
			field_75358_j = var1;
		}
		if(breakingTime == 240 && theEntity.worldObj.difficultySetting == 3)
		{
			theEntity.worldObj.setBlockToAir(entityPosX, entityPosY, entityPosZ);
			theEntity.worldObj.playAuxSFX(1012, entityPosX, entityPosY, entityPosZ, 0);
			theEntity.worldObj.playAuxSFX(2001, entityPosX, entityPosY, entityPosZ, targetDoor.blockID);
		}
	}
}
