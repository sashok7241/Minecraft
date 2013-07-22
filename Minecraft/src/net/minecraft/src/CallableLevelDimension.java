package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelDimension implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelDimension(WorldInfo p_i6825_1_)
	{
		worldInfoInstance = p_i6825_1_;
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
