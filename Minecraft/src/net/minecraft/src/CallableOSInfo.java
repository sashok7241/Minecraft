package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableOSInfo implements Callable
{
	final CrashReport theCrashReport;
	
	CallableOSInfo(CrashReport par1CrashReport)
	{
		theCrashReport = par1CrashReport;
	}
	
	@Override public Object call()
	{
		return getOsAsString();
	}
	
	public String getOsAsString()
	{
		return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
	}
}
