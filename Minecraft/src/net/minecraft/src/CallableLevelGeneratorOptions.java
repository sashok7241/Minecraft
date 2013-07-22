package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelGeneratorOptions implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelGeneratorOptions(WorldInfo p_i6822_1_)
	{
		worldInfoInstance = p_i6822_1_;
	}
	
	@Override public Object call()
	{
		return callLevelGeneratorOptions();
	}
	
	public String callLevelGeneratorOptions()
	{
		return WorldInfo.getWorldGeneratorOptions(worldInfoInstance);
	}
}
