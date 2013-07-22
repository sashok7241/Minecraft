package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableLWJGLVersion implements Callable
{
	final Minecraft mc;
	
	CallableLWJGLVersion(Minecraft par1Minecraft)
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
