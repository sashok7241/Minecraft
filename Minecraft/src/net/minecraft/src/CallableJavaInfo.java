package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJavaInfo implements Callable
{
	final CrashReport theCrashReport;
	
	CallableJavaInfo(CrashReport par1CrashReport)
	{
		theCrashReport = par1CrashReport;
	}
	
	@Override public Object call()
	{
		return getJavaInfoAsString();
	}
	
	public String getJavaInfoAsString()
	{
		return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
	}
}
