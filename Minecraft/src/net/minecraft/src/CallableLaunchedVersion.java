package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableLaunchedVersion implements Callable
{
	final Minecraft mc;
	
	CallableLaunchedVersion(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return getLWJGLVersion();
	}
	
	public String getLWJGLVersion()
	{
		return Minecraft.func_110431_a(mc);
	}
}
