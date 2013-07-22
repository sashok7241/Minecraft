package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMPL2 implements Callable
{
	final WorldClient theWorldClient;
	
	CallableMPL2(WorldClient par1WorldClient)
	{
		theWorldClient = par1WorldClient;
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
