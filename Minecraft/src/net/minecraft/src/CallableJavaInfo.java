package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJavaInfo implements Callable
{
	final CrashReport theCrashReport;
	
	CallableJavaInfo(CrashReport p_i3246_1_)
	{
		theCrashReport = p_i3246_1_;
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
