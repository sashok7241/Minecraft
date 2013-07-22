package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
	final CrashReport theCrashReport;
	
	CallableMinecraftVersion(CrashReport p_i3244_1_)
	{
		theCrashReport = p_i3244_1_;
	}
	
	@Override public Object call()
	{
		return minecraftVersion();
	}
	
	public String minecraftVersion()
	{
		return "1.5.2";
	}
}
