package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelSeed implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelSeed(WorldInfo par1WorldInfo)
	{
		worldInfoInstance = par1WorldInfo;
	}
	
	@Override public Object call()
	{
		return callLevelSeed();
	}
	
	public String callLevelSeed()
	{
		return String.valueOf(worldInfoInstance.getSeed());
	}
}
