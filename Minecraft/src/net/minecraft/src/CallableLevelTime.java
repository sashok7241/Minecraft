package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelTime implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelTime(WorldInfo p_i6824_1_)
	{
		worldInfoInstance = p_i6824_1_;
	}
	
	@Override public Object call()
	{
		return callLevelTime();
	}
	
	public String callLevelTime()
	{
		return String.format("%d game time, %d day time", new Object[] { Long.valueOf(WorldInfo.func_85126_g(worldInfoInstance)), Long.valueOf(WorldInfo.getWorldTime(worldInfoInstance)) });
	}
}
