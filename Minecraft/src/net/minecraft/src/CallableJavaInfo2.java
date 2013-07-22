package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJavaInfo2 implements Callable
{
	final CrashReport theCrashReport;
	
	CallableJavaInfo2(CrashReport par1CrashReport)
	{
		theCrashReport = par1CrashReport;
	}
	
	@Override public Object call()
	{
		return getJavaVMInfoAsString();
	}
	
	public String getJavaVMInfoAsString()
	{
		return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
	}
}
