package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.server.MinecraftServer;

public class CallableIsServerModded implements Callable
{
	final MinecraftServer mcServer;
	
	public CallableIsServerModded(MinecraftServer par1)
	{
		mcServer = par1;
	}
	
	@Override public Object call()
	{
		return func_96558_a();
	}
	
	public String func_96558_a()
	{
		return mcServer.theProfiler.profilingEnabled ? mcServer.theProfiler.getNameOfLastSection() : "N/A (disabled)";
	}
}
