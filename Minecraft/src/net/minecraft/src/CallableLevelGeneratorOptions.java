package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelGeneratorOptions implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelGeneratorOptions(WorldInfo par1WorldInfo)
	{
		worldInfoInstance = par1WorldInfo;
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
