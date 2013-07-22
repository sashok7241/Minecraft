package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableClientProfiler implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableClientProfiler(Minecraft p_i3017_1_)
	{
		theMinecraft = p_i3017_1_;
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
