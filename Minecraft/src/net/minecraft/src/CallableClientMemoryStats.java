package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableClientMemoryStats implements Callable
{
	final Minecraft theMinecraft;
	
	CallableClientMemoryStats(Minecraft par1Minecraft)
	{
		theMinecraft = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return callClientMemoryStats();
	}
	
	public String callClientMemoryStats()
	{
		return theMinecraft.mcProfiler.profilingEnabled ? theMinecraft.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
	}
}
