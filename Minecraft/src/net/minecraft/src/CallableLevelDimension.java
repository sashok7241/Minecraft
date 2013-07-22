package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelDimension implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelDimension(WorldInfo par1WorldInfo)
	{
		worldInfoInstance = par1WorldInfo;
	}
	
	@Override public Object call()
	{
		return callLevelDimension();
	}
	
	public String callLevelDimension()
	{
		return String.valueOf(WorldInfo.func_85122_i(worldInfoInstance));
	}
}
