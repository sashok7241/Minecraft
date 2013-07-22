package net.minecraft.src;

public abstract class EntityAIBase
{
	private int mutexBits = 0;
	
	public boolean continueExecuting()
	{
		return shouldExecute();
	}
	
	public int getMutexBits()
	{
		return mutexBits;
	}
	
	public boolean isInterruptible()
	{
		return true;
	}
	
	public void resetTask()
	{
	}
	
	public void setMutexBits(int p_75248_1_)
	{
		mutexBits = p_75248_1_;
	}
	
	public abstract boolean shouldExecute();
	
	public void startExecuting()
	{
	}
	
	public void updateTask()
	{
	}
}
