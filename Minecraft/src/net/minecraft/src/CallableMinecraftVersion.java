package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
	final CrashReport theCrashReport;
	
	CallableMinecraftVersion(CrashReport par1CrashReport)
	{
		theCrashReport = par1CrashReport;
	}
	
	@Override public Object call()
	{
		return minecraftVersion();
	}
	
	public String minecraftVersion()
	{
		return "1.6.2";
	}
}
