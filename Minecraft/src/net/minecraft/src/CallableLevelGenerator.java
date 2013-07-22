package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelGenerator implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelGenerator(WorldInfo par1WorldInfo)
	{
		worldInfoInstance = par1WorldInfo;
	}
	
	@Override public Object call()
	{
		return callLevelGeneratorInfo();
	}
	
	public String callLevelGeneratorInfo()
	{
		return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(WorldInfo.getTerrainTypeOfWorld(worldInfoInstance).getWorldTypeID()), WorldInfo.getTerrainTypeOfWorld(worldInfoInstance).getWorldTypeName(), Integer.valueOf(WorldInfo.getTerrainTypeOfWorld(worldInfoInstance).getGeneratorVersion()), Boolean.valueOf(WorldInfo.getMapFeaturesEnabled(worldInfoInstance)) });
	}
}
