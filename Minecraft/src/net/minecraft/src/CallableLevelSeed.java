package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelSeed implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelSeed(WorldInfo p_i6820_1_)
	{
		worldInfoInstance = p_i6820_1_;
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
