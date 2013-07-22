package net.minecraft.src;

public abstract class EntityAIBase
{
	private int mutexBits;
	
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
	
	public void setMutexBits(int par1)
	{
		mutexBits = par1;
	}
	
	public abstract boolean shouldExecute();
	
	public void startExecuting()
	{
	}
	
	public void updateTask()
	{
	}
}
