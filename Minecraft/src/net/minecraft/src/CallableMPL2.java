package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMPL2 implements Callable
{
	final WorldClient theWorldClient;
	
	CallableMPL2(WorldClient p_i3097_1_)
	{
		theWorldClient = p_i3097_1_;
	}
	
	@Override public Object call()
	{
		return getEntitySpawnQueueCountAndList();
	}
	
	public String getEntitySpawnQueueCountAndList()
	{
		return WorldClient.getEntitySpawnQueue(theWorldClient).size() + " total; " + WorldClient.getEntitySpawnQueue(theWorldClient).toString();
	}
}
