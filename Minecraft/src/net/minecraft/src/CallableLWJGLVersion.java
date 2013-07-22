package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableLWJGLVersion implements Callable
{
	final Minecraft mc;
	
	public CallableLWJGLVersion(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return getType();
	}
	
	public String getType()
	{
		return Sys.getVersion();
	}
}
