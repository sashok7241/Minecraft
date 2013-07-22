package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableClientProfiler implements Callable
{
	final Minecraft theMinecraft;
	
	CallableClientProfiler(Minecraft par1Minecraft)
	{
		theMinecraft = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return callClientProfilerInfo();
	}
	
	public String callClientProfilerInfo()
	{
		return Minecraft.func_142024_b(theMinecraft).func_135041_c().toString();
	}
}
