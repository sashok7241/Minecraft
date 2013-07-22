package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableUpdatingScreenName implements Callable
{
	final Minecraft theMinecraft;
	
	CallableUpdatingScreenName(Minecraft par1Minecraft)
	{
		theMinecraft = par1Minecraft;
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
