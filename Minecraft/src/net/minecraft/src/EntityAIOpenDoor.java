package net.minecraft.src;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
	boolean field_75361_i;
	int field_75360_j;
	
	public EntityAIOpenDoor(EntityLiving p_i3484_1_, boolean p_i3484_2_)
	{
		super(p_i3484_1_);
		theEntity = p_i3484_1_;
		field_75361_i = p_i3484_2_;
	}
	
	@Override public boolean continueExecuting()
	{
		return field_75361_i && field_75360_j > 0 && super.continueExecuting();
	}
	
	@Override public void resetTask()
	{
		if(field_75361_i)
		{
			targetDoor.onPoweredBlockChange(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, false);
		}
	}
	
	@Override public void startExecuting()
	{
		field_75360_j = 20;
		targetDoor.onPoweredBlockChange(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, true);
	}
	
	@Override public void updateTask()
	{
		--field_75360_j;
		super.updateTask();
	}
}
