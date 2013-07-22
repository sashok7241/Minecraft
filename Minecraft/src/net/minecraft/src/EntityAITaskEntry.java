package net.minecraft.src;

class EntityAITaskEntry
{
	public EntityAIBase action;
	public int priority;
	final EntityAITasks tasks;
	
	public EntityAITaskEntry(EntityAITasks p_i3468_1_, int p_i3468_2_, EntityAIBase p_i3468_3_)
	{
		tasks = p_i3468_1_;
		priority = p_i3468_2_;
		action = p_i3468_3_;
	}
}
