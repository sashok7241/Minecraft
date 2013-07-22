package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelWeather implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelWeather(WorldInfo par1WorldInfo)
	{
		worldInfoInstance = par1WorldInfo;
	}
	
	@Override public Object call()
	{
		return callLevelWeatherInfo();
	}
	
	public String callLevelWeatherInfo()
	{
		return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(WorldInfo.getRainTime(worldInfoInstance)), Boolean.valueOf(WorldInfo.getRaining(worldInfoInstance)), Integer.valueOf(WorldInfo.getThunderTime(worldInfoInstance)), Boolean.valueOf(WorldInfo.getThundering(worldInfoInstance)) });
	}
}
