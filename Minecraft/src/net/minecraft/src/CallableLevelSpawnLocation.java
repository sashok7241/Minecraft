package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelSpawnLocation implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelSpawnLocation(WorldInfo p_i6823_1_)
	{
		worldInfoInstance = p_i6823_1_;
	}
	
	@Override public Object call()
	{
		return callLevelSpawnLocation();
	}
	
	public String callLevelSpawnLocation()
	{
		return CrashReportCategory.getLocationInfo(WorldInfo.getSpawnXCoordinate(worldInfoInstance), WorldInfo.getSpawnYCoordinate(worldInfoInstance), WorldInfo.getSpawnZCoordinate(worldInfoInstance));
	}
}
