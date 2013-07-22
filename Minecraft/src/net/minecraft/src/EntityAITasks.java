package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityAITasks
{
	private List taskEntries = new ArrayList();
	private List executingTaskEntries = new ArrayList();
	private final Profiler theProfiler;
	private int tickCount;
	private int tickRate = 3;
	
	public EntityAITasks(Profiler par1Profiler)
	{
		theProfiler = par1Profiler;
	}
	
	public void addTask(int par1, EntityAIBase par2EntityAIBase)
	{
		taskEntries.add(new EntityAITaskEntry(this, par1, par2EntityAIBase));
	}
	
	private boolean areTasksCompatible(EntityAITaskEntry par1EntityAITaskEntry, EntityAITaskEntry par2EntityAITaskEntry)
	{
		return (par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0;
	}
	
	private boolean canContinue(EntityAITaskEntry par1EntityAITaskEntry)
	{
		theProfiler.startSection("canContinue");
		boolean var2 = par1EntityAITaskEntry.action.continueExecuting();
		theProfiler.endSection();
		return var2;
	}
	
	private boolean canUse(EntityAITaskEntry par1EntityAITaskEntry)
	{
		theProfiler.startSection("canUse");
		Iterator var2 = taskEntries.iterator();
		while(var2.hasNext())
		{
			EntityAITaskEntry var3 = (EntityAITaskEntry) var2.next();
			if(var3 != par1EntityAITaskEntry)
			{
				if(par1EntityAITaskEntry.priority >= var3.priority)
				{
					if(executingTaskEntries.contains(var3) && !areTasksCompatible(par1EntityAITaskEntry, var3))
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
	
	public void removeTask(EntityAIBase par1EntityAIBase)
	{
		Iterator var2 = taskEntries.iterator();
		while(var2.hasNext())
		{
			EntityAITaskEntry var3 = (EntityAITaskEntry) var2.next();
			EntityAIBase var4 = var3.action;
			if(var4 == par1EntityAIBase)
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
