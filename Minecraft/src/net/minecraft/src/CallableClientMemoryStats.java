package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableClientMemoryStats implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableClientMemoryStats(Minecraft par1)
	{
		theMinecraft = par1;
	}
	
	@Override public Object call()
	{
		return callClientMemoryStats();
	}
	
	public String callClientMemoryStats()
	{
		int var1 = theMinecraft.theWorld.getWorldVec3Pool().getPoolSize();
		int var2 = 56 * var1;
		int var3 = var2 / 1024 / 1024;
		int var4 = theMinecraft.theWorld.getWorldVec3Pool().func_82590_d();
		int var5 = 56 * var4;
		int var6 = var5 / 1024 / 1024;
		return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
	}
}
