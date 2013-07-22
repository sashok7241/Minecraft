package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelGamemode implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelGamemode(WorldInfo par1WorldInfo)
	{
		worldInfoInstance = par1WorldInfo;
	}
	
	@Override public Object call()
	{
		return callLevelGameModeInfo();
	}
	
	public String callLevelGameModeInfo()
	{
		return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { WorldInfo.getGameType(worldInfoInstance).getName(), Integer.valueOf(WorldInfo.getGameType(worldInfoInstance).getID()), Boolean.valueOf(WorldInfo.func_85117_p(worldInfoInstance)), Boolean.valueOf(WorldInfo.func_85131_q(worldInfoInstance)) });
	}
}
