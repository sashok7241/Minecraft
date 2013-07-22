package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableTickingScreenName implements Callable
{
	final Minecraft mc;
	
	public CallableTickingScreenName(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return getLWJGLVersion();
	}
	
	public String getLWJGLVersion()
	{
		return mc.currentScreen.getClass().getCanonicalName();
	}
}
