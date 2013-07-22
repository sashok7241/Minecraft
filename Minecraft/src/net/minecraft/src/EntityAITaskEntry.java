package net.minecraft.src;

class EntityAITaskEntry
{
	public EntityAIBase action;
	public int priority;
	final EntityAITasks tasks;
	
	public EntityAITaskEntry(EntityAITasks par1EntityAITasks, int par2, EntityAIBase par3EntityAIBase)
	{
		tasks = par1EntityAITasks;
		priority = par2;
		action = par3EntityAIBase;
	}
}
