package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLevelStorageVersion implements Callable
{
	final WorldInfo worldInfoInstance;
	
	CallableLevelStorageVersion(WorldInfo p_i6826_1_)
	{
		worldInfoInstance = p_i6826_1_;
	}
	
	@Override public Object call()
	{
		return callLevelStorageFormat();
	}
	
	public String callLevelStorageFormat()
	{
		String var1 = "Unknown?";
		try
		{
			switch(WorldInfo.getSaveVersion(worldInfoInstance))
			{
				case 19132:
					var1 = "McRegion";
					break;
				case 19133:
					var1 = "Anvil";
			}
		} catch(Throwable var3)
		{
			;
		}
		return String.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldInfo.getSaveVersion(worldInfoInstance)), var1 });
	}
}
