package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityAITasks
{
	private List taskEntries = new ArrayList();
	private List executingTaskEntries = new ArrayList();
	private final Profiler theProfiler;
	private int tickCount = 0;
	private int tickRate = 3;
	
	public EntityAITasks(Profiler p_i3469_1_)
	{
		theProfiler = p_i3469_1_;
	}
	
	public void addTask(int p_75776_1_, EntityAIBase p_75776_2_)
	{
		taskEntries.add(new EntityAITaskEntry(this, p_75776_1_, p_75776_2_));
	}
	
	private boolean areTasksCompatible(EntityAITaskEntry p_75777_1_, EntityAITaskEntry p_75777_2_)
	{
		return (p_75777_1_.action.getMutexBits() & p_75777_2_.action.getMutexBits()) == 0;
	}
	
	private boolean canContinue(EntityAITaskEntry p_75773_1_)
	{
		theProfiler.startSection("canContinue");
		boolean var2 = p_75773_1_.action.continueExecuting();
		theProfiler.endSection();
		return var2;
	}
	
	private boolean canUse(EntityAITaskEntry p_75775_1_)
	{
		theProfiler.startSection("canUse");
		Iterator var2 = taskEntries.iterator();
		while(var2.hasNext())
		{
			EntityAITaskEntry var3 = (EntityAITaskEntry) var2.next();
			if(var3 != p_75775_1_)
			{
				if(p_75775_1_.priority >= var3.priority)
				{
					if(executingTaskEntries.contains(var3) && !areTasksCompatible(p_75775_1_, var3))
					{
						theProfiler.endSection();
						return false;
					}
				} else if(executingTaskEntries.contains(var3) && !var3.action.isInterruptible())
				{
					theProfiler.endSection();
					return false;
				}
			}
		}
		theProfiler.endSection();
		return true;
	}
	
	public void onUpdateTasks()
	{
		ArrayList var1 = new ArrayList();
		Iterator var2;
		EntityAITaskEntry var3;
		if(tickCount++ % tickRate == 0)
		{
			var2 = taskEntries.iterator();
			while(var2.hasNext())
			{
				var3 = (EntityAITaskEntry) var2.next();
				boolean var4 = executingTaskEntries.contains(var3);
				if(var4)
				{
					if(canUse(var3) && canContinue(var3))
					{
						continue;
					}
					var3.action.resetTask();
					executingTaskEntries.remove(var3);
				}
				if(canUse(var3) && var3.action.shouldExecute())
				{
					var1.add(var3);
					executingTaskEntries.add(var3);
				}
			}
		} else
		{
			var2 = executingTaskEntries.iterator();
			while(var2.hasNext())
			{
				var3 = (EntityAITaskEntry) var2.next();
				if(!var3.action.continueExecuting())
				{
					var3.action.resetTask();
					var2.remove();
				}
			}
		}
		theProfiler.startSection("goalStart");
		var2 = var1.iterator();
		while(var2.hasNext())
		{
			var3 = (EntityAITaskEntry) var2.next();
			theProfiler.startSection(var3.action.getClass().getSimpleName());
			var3.action.startExecuting();
			theProfiler.endSection();
		}
		theProfiler.endSection();
		theProfiler.startSection("goalTick");
		var2 = executingTaskEntries.iterator();
		while(var2.hasNext())
		{
			var3 = (EntityAITaskEntry) var2.next();
			var3.action.updateTask();
		}
		theProfiler.endSection();
	}
	
	public void removeTask(EntityAIBase p_85156_1_)
	{
		Iterator var2 = taskEntries.iterator();
		while(var2.hasNext())
		{
			EntityAITaskEntry var3 = (EntityAITaskEntry) var2.next();
			EntityAIBase var4 = var3.action;
			if(var4 == p_85156_1_)
			{
				if(executingTaskEntries.contains(var3))
				{
					var4.resetTask();
					executingTaskEntries.remove(var3);
				}
				var2.remove();
			}
		}
	}
}
