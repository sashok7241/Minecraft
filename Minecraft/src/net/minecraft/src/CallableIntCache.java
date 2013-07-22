package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableIntCache implements Callable
{
	final CrashReport theCrashReport;
	
	CallableIntCache(CrashReport par1CrashReport)
	{
		theCrashReport = par1CrashReport;
	}
	
	@Override public Object call()
	{
		return func_85083_a();
	}
	
	public String func_85083_a()
	{
		return IntCache.func_85144_b();
	}
}
