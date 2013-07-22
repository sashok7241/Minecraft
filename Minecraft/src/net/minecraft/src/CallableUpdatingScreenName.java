package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableUpdatingScreenName implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableUpdatingScreenName(Minecraft p_i7002_1_)
	{
		theMinecraft = p_i7002_1_;
	}
	
	@Override public Object call()
	{
		return callUpdatingScreenName();
	}
	
	public String callUpdatingScreenName()
	{
		return theMinecraft.currentScreen.getClass().getCanonicalName();
	}
}
