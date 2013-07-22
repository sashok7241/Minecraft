package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableClientProfiler implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableClientProfiler(Minecraft par1Minecraft)
	{
		theMinecraft = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return callClientProfilerInfo();
	}
	
	public String callClientProfilerInfo()
	{
		return theMinecraft.mcProfiler.profilingEnabled ? theMinecraft.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
	}
}
