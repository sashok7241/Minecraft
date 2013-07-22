package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableLWJGLVersion implements Callable
{
	final Minecraft mc;
	
	public CallableLWJGLVersion(Minecraft p_i3002_1_)
	{
		mc = p_i3002_1_;
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
